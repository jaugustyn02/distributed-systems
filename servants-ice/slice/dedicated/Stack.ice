module Objects {
    sequence<int> IntSequence;

    interface Stack {
        void push(int value);
        int pop();
        idempotent int top();
        idempotent IntSequence topN(int n);
        idempotent bool isEmpty();
    };
};