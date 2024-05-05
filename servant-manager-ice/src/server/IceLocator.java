package server;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.UserException;

import Objects.PalindromeChecker;
import Objects.StringReverser;

public class IceLocator implements ServantLocator {

    @Override
    public LocateResult locate(Current current) throws UserException {
        String category = current.id.category;
        String name = current.id.name;
        if (category.equals("dedicated")) {
            System.out.println("Creating dedicated servant for: " + category + "/" + name);
            Object servant;
            if (name.equals("palindrome")){
                servant = new PalindromeCheckerI();
            } else if (name.equals("reverser")) {
                servant = new StringReverserI();
            } else {
                System.out.println("Unknown servant name: " + name);
                return null;
            }
            current.adapter.add(servant, current.id);
            return new LocateResult(servant, null);
        }
        return null;
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }

    @Override
    public void deactivate(String s) {

    }
}
