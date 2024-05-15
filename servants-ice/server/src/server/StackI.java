package server;

import Objects.Stack;
import com.zeroc.Ice.Current;

public class StackI implements Stack {
    static final int EMPTY_STACK_VALUE = Integer.MIN_VALUE;
    private final int[] stack = new int[100];
    private int top = -1;

    @Override
    public void push(int value, Current current) {
        if (top == stack.length - 1) {
            return;
        }
        stack[++top] = value;
    }

    @Override
    public int pop(Current current) {
        if (top == -1) {
            return EMPTY_STACK_VALUE;
        }
        return stack[top--];
    }

    @Override
    public int top(Current current) {
        if (top == -1) {
            return EMPTY_STACK_VALUE;
        }
        return stack[top];
    }

    @Override
    public int[] topN(int n, Current current) {
        n = Math.min(n, top + 1);
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = stack[top - i];
        }
        return result;
    }

    @Override
    public boolean isEmpty(Current current) {
        return top == -1;
    }
}
