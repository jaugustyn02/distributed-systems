package server;

import Objects.StringReverser;
import com.zeroc.Ice.Current;

public class StringReverserI implements StringReverser{
    @Override
    public String reverse(String text, Current current) {
        return new StringBuilder(text).reverse().toString();
    }
}
