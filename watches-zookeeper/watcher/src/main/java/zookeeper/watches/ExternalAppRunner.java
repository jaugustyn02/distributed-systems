package main.java.zookeeper.watches;

import java.io.IOException;

public class ExternalAppRunner {
    private Process externalAppProcess = null;

    public void openExternalApp() {
        try {
            externalAppProcess = Runtime.getRuntime().exec(new String[]{"DRAWEXE"});
            writeToExternalAppProcess("axo");
            updateExternalApp(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateExternalApp(int numOfChildren) {
        writeToExternalAppProcess("erase");
        writeToExternalAppProcess("dtext -180 100 0 \"Number of children: " + numOfChildren + "\"");
    }

    public void closeExternalApp() {
        if (!isAppRunning()) {
            System.err.println("Cannot close the external app because it is not running");
            return;
        }
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

    public boolean isAppRunning() {
        return externalAppProcess != null;
    }

    private void writeToExternalAppProcess(String message) {
        if (externalAppProcess != null) {
            try {
                externalAppProcess.getOutputStream().write((message + "\n").getBytes());
                externalAppProcess.getOutputStream().flush();
            } catch (IOException e) {
                System.err.println("Failed to write to the external process" + e);
            }
        }
    }
}
