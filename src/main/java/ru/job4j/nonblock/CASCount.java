package ru.job4j.nonblock;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount<T> {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int val;
        do {
            val = getValue();
        } while (!count.compareAndSet(val, val + 1));
    }

    public int getValue() {
        return count.get();
    }
}
