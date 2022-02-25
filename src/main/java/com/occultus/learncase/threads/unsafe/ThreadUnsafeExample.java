package com.occultus.learncase.threads.unsafe;

public class ThreadUnsafeExample {
    private int count = 0;

    public void add() {
        count++;
    }

    public int get() {
        return count;
    }
}
