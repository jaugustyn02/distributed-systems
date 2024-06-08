package main.java.zookeeper.watches;

import org.apache.zookeeper.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    }

    public static void main(String[] args){
        ZooKeeperWatcher watcher = new ZooKeeperWatcher("127.0.0.1", "2181", "/a");
        watcher.start();
    }

    public void start() {
        try {
            zooKeeper.addWatch(zNodeRootPath, AddWatchMode.PERSISTENT_RECURSIVE);
            // Check if zNodeRootPath already exists
            if (zooKeeper.exists(zNodeRootPath, true) != null){
                handleZnodeCreation(zNodeRootPath);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
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
        System.out.println("Available commands: print, exit.");
        while (true) {
            System.out.print("<console> ");
            try {
                String input = br.readLine();
                if (input.equals("exit")) {
                    stop();
                    break;
                } else if (input.equals("print")) {
                    printTree();
                } else if (!input.isEmpty()){
                    System.out.println("Unknown command. Available commands: print, exit.");
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
            if (eventType == Event.EventType.NodeChildrenChanged && path.startsWith(zNodeRootPath)) {
                handleChildren(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleZnodeCreation(String path) throws KeeperException, InterruptedException {
        logger.info("Znode created: " + path);
        if (path.equals(zNodeRootPath)) {
            openExternalApp();
        }
        zooKeeper.getChildren(path, true);
    }

    private void handleZnodeDeletion(String path){
        logger.info("Znode deleted: " + path);
        if (path.startsWith(zNodeRootPath) && externalAppProcess != null) {
            closeExternalApp();
        }
    }

    private void handleChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(path, true);
        logger.info("Number of children: " + children.size());

        writeToExternalAppProcess("erase");
        writeToExternalAppProcess("dtext -180 100 0 \"Number of children: " + children.size() + "\"");
    }

    private void openExternalApp() {
        try {
            externalAppProcess = Runtime.getRuntime().exec(new String[]{"DRAWEXE"});
            writeToExternalAppProcess("axo");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeExternalApp() {
        writeToExternalAppProcess("exit");
        externalAppProcess.destroy();
    }

    private void printTree() {
        System.out.println("Siema");
    }

    private void writeToExternalAppProcess(String message) {
        if (externalAppProcess != null) {
            try {
                OutputStream os = externalAppProcess.getOutputStream();
                os.flush();
                os.write(message.getBytes());
                os.flush();
            } catch (IOException e) {
                logger.error("Failed to write to the external process", e);
            }
        }
    }

//    private static void printTree(String path, String indent) throws KeeperException, InterruptedException {
//        Stat stat = zooKeeper.exists(path, false);
//        if (stat == null) {
//            return;
//        }
//        System.out.println(indent + path);
//        List<String> children = zooKeeper.getChildren(path, false);
//        for (String child : children) {
//            printTree(path + "/" + child, indent + "  ");
//        }
//    }

}
