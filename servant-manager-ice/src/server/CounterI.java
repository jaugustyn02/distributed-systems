package server;

import Objects.Counter;
import com.zeroc.Ice.Current;

public class CounterI implements Counter{
    private long value = 0;

    @Override
    public void increment(Current current) {
        value++;
    }

    @Override
    public long getValue(Current current) {
        return value;
    }
}
