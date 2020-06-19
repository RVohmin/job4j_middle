package ru.job4j.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> storage = new ArrayList<>();

    public synchronized void add(T value) {
        storage.add(value);
    }

    public synchronized T get(int index) {
        return storage.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.storage).iterator();
    }

    private synchronized List<T> copy(List<T> storage) {
        return new ArrayList<>(storage);
    }
}
