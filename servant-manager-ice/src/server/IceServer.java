package server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.Util;

public class IceServer {
    public void start(String [] args){
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args, "config.server");
            ObjectAdapter adapter = communicator.createObjectAdapter("Adapter");

            StackI stack = new StackI();
            adapter.add(stack, new Identity("stack", "shared"));

            CounterI counter = new CounterI();
            adapter.add(counter, new Identity("counter", "shared"));

            ServantLocator servantLocator = new IceLocator();
            adapter.addServantLocator(servantLocator, "dedicated");

            System.out.println("Entering event processing loop...");
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            status = 1;
        }

        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                status = 1;
            }
        }
        System.exit(status);
    }

    public static void main(String[] args) {
        IceServer server = new IceServer();
        server.start(args);
    }
}