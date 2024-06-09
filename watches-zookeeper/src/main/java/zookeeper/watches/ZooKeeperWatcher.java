package main.java.zookeeper.watches;

import org.apache.zookeeper.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.data.Stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ZooKeeperWatcher implements Watcher {
    private static final Logger logger = LogManager.getLogger(ZooKeeperWatcher.class);
    private static final int SESSION_TIMEOUT_MS = 3000;
    private static final int CLOSE_TIMEOUT_MS = 5000;
    private static ZooKeeper zooKeeper;
    private static Process externalAppProcess;
    private final String zNodeRootPath;

    public ZooKeeperWatcher(String host, String port, String zNodeRootName) {
        this.zNodeRootPath = zNodeRootName;
        try {
            zooKeeper = new ZooKeeper(host+":"+port, SESSION_TIMEOUT_MS, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread shutdownHook = new Thread(this::stop);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    // --------------------------- Main ---------------------------

    public static void main(String[] args){
        ZooKeeperWatcher watcher = new ZooKeeperWatcher("127.0.0.1", "2181", "/a");
        watcher.start();
    }

    public void start() {
        try {
            zooKeeper.addWatch(zNodeRootPath, AddWatchMode.PERSISTENT_RECURSIVE);
            // Check if zNodeRootPath already exists
            if (zooKeeper.exists(zNodeRootPath, true) != null){
                openExternalApp();
            }
        } catch (KeeperException | InterruptedException e) {
            System.err.println("Failed to start the watcher. Check if the ZooKeeper server is running.");
            System.exit(1);
        }
        logger.info("Started running");
        startInputConsole();
    }

    private void stop(){
        try {
            zooKeeper.close(CLOSE_TIMEOUT_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeExternalApp();
        logger.info("Quiting");
    }

    private void startInputConsole() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Available commands: print, exit, app.");
        while (true) {
            System.out.print("<console> ");
            try {
                String input = br.readLine();
                if (input.equals("exit")) {
                    stop();
                    break;
                } else if (input.equals("print")) {
                    printTree(zNodeRootPath, "");
                } else if (input.equals("app")){
                    System.out.print("<external app> ");
                    writeToExternalAppProcess(br.readLine());
                } else if (!input.isEmpty()){
                    System.out.println("Unknown command. Available commands: print, exit, app.");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();
        Event.EventType eventType = watchedEvent.getType();

        try {
            if (eventType == Event.EventType.NodeCreated && path.startsWith(zNodeRootPath)) {
                handleZnodeCreation(path);
            } else if (eventType == Event.EventType.NodeDeleted && path.startsWith(zNodeRootPath)) {
                handleZnodeDeletion(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------------- Handlers ---------------------------

    private void handleZnodeCreation(String path) throws InterruptedException, KeeperException {
        logger.info("Znode created: " + path);
        if (path.equals(zNodeRootPath)){
            openExternalApp();
            updateExternalApp(0);
        } else{
            handleChildren(path);
        }
    }

    private void handleZnodeDeletion(String path) throws InterruptedException, KeeperException {
        logger.info("Znode deleted: " + path);
        if (path.equals(zNodeRootPath) && externalAppProcess != null) {
            closeExternalApp();
            zooKeeper.addWatch(zNodeRootPath, AddWatchMode.PERSISTENT_RECURSIVE);
        } else {
            handleChildren(path);
        }
    }

    private void handleChildren(String path) throws KeeperException, InterruptedException {
        int numOfChildren = countAllChildren(zNodeRootPath);
        logger.info("Number of children: " + numOfChildren);

        updateExternalApp(numOfChildren);

        zooKeeper.addWatch(path, AddWatchMode.PERSISTENT_RECURSIVE);
    }

    // --------------------------- External App ---------------------------

    private void openExternalApp() {
        try {
            externalAppProcess = Runtime.getRuntime().exec(new String[]{"DRAWEXE"});
            writeToExternalAppProcess("axo");
            updateExternalApp(countAllChildren(zNodeRootPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateExternalApp(int numOfChildren) {
        writeToExternalAppProcess("erase");
        writeToExternalAppProcess("dtext -180 100 0 \"Number of children: " + numOfChildren + "\"");
    }

    private void closeExternalApp() {
        writeToExternalAppProcess("exit");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            externalAppProcess.destroy();
            externalAppProcess = null;
        }
    }

    // --------------------------- Utils ---------------------------

    private void writeToExternalAppProcess(String message) {
        if (externalAppProcess != null) {
            try {
                externalAppProcess.getOutputStream().write((message + "\n").getBytes());
                externalAppProcess.getOutputStream().flush();
            } catch (IOException e) {
                logger.error("Failed to write to the external process", e);
            }
        }
    }

    private static void printTree(String path, String indent){
        Stat stat;
        try {
            stat = zooKeeper.exists(path, false);
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (stat == null) {
            return;
        }
        System.out.println(indent + path);
        List<String> children;
        try {
            children = zooKeeper.getChildren(path, false);
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (String child : children) {
            printTree(path + "/" + child, indent + "  ");
        }
    }

    private static int countAllChildren(String path) {
        int count = 0;
        try {
            List<String> children = zooKeeper.getChildren(path, false);
            count += children.size();
            for (String child : children) {
                count += countAllChildren(path + "/" + child);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
