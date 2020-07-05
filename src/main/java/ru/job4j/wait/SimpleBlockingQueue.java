package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int maxSizeQueue;

    public SimpleBlockingQueue(int maxSizeQueue) {
        this.maxSizeQueue = maxSizeQueue;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void offer(T value) {
        while (queue.size() >= maxSizeQueue) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        System.out.println("Добавлено " + value + " size = " + queue.size() + " isEmpty? " + isEmpty());
        notify();
    }

    public synchronized T poll() {
        T value;
        while (queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        synchronized (queue) {
            value = queue.poll();
        }
        notify();
        System.out.println("извлечено " + value);
        return value;
    }
}
