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

            PalindromeCheckerI checker = new PalindromeCheckerI();
            adapter.add(checker, new Identity("checker", "shared"));
            System.out.println("Creating shared servant for: shared/checker");

            StringReverserI reverser = new StringReverserI();
            adapter.add(reverser, new Identity("reverser", "shared"));
            System.out.println("Creating shared servant for: shared/reverser");

            ServantLocator servantLocator = new DedicatedLocator();
            adapter.addServantLocator(servantLocator, "dedicated");
            adapter.activate();

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