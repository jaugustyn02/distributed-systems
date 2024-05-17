package server;

import Objects.StringReverser;
import com.zeroc.Ice.Current;

public class StringReverserI implements StringReverser{
    @Override
    public String reverse(String text, Current current) {
        System.out.println("<reverser> Reversing the string: " + text);
        return new StringBuilder(text).reverse().toString();
    }
}
