package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final Object monitor = this;
    private int count = 0;
    boolean flag = true;

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        synchronized (Thread.currentThread()) {
            while (!flag) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void offers(T value) {
        if (count >= 3) {
            off();
        }
        check();
        queue.offer(value);
        count++;
        System.out.println("Добавлено " + value);
    }

    public synchronized T poll() {
        T value = queue.poll();
        count--;
        if (queue.peek() == null) {
            on();
        }
        return value;
    }
}
