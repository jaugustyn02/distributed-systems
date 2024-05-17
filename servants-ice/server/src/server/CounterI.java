package server;

import Objects.Counter;
import com.zeroc.Ice.Current;

public class CounterI implements Counter{
    private long value = 0;

    @Override
    public void increment(Current current) {
        System.out.println("<counter> Incrementing counter");
        value++;
    }

    @Override
    public long getValue(Current current) {
        System.out.println("<counter> Returning counter value");
        return value;
    }
}
