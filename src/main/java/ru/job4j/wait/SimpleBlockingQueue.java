package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    @GuardedBy("this")
    private final Object monitor = this;
    private final int maxSizeQueue;
    private int count = 0;

    public SimpleBlockingQueue(int maxSizeQueue) {
        this.maxSizeQueue = maxSizeQueue;
    }

    public synchronized boolean isEmptyQueue() {
        return queue.size() == 0;
    }

    public synchronized boolean isFullQueue() {
        return queue.size() == maxSizeQueue;
    }

    public synchronized void wakeUpThreads() {
        monitor.notifyAll();
    }

    public synchronized void ifFullQueueThenWait() {
        while (isFullQueue()) {
            try {
                System.out.println(Thread.currentThread().getName() + " sleep");
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void ifEmptyQueueThenWait() {
        while (isEmptyQueue()) {
            try {
                System.out.println(Thread.currentThread().getName() + " sleep");
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void offer(T value) {
        if (isFullQueue()) {
            wakeUpThreads();
        }
        ifFullQueueThenWait();
        queue.offer(value);
        count++;
        System.out.println("Добавлено " + value + " count = " + count + " isEmpty? " + isEmptyQueue());
    }

    public synchronized T poll() {
        if (isEmptyQueue()) {
            wakeUpThreads();
        }
        ifEmptyQueueThenWait();
        T value = queue.poll();
        count--;
        return value;
    }
}
