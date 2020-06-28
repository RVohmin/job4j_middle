package ru.job4j.nonblock;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount<T> {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        while (true) {
            int existingValue = getValue();
            int newValue = existingValue + 1;
            if (count.compareAndSet(existingValue, newValue)) {
                return;
            }
        }
    }

    public int getValue() {
        return count.get();
    }
}
