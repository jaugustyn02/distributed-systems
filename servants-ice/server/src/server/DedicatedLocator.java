package server;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;

public class DedicatedLocator implements ServantLocator {

    @Override
    public LocateResult locate(Current current) throws UserException {
        String category = current.id.category;
        String name = current.id.name;
        System.out.println("Creating dedicated servant for: " + category + "/" + name);
        System.out.flush();
        Object servant;
        if (name.equals("stack")){
            servant = new StackI();
        } else if (name.equals("counter")) {
            servant = new CounterI();
        } else {
            System.err.println("Error: Unknown servant: " + category + "/" + name);
            System.err.flush();
            throw new ObjectNotExistException();
        }
        current.adapter.add(servant, current.id);
        return new LocateResult(servant, null);
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {}

    @Override
    public void deactivate(String s) {}
}
