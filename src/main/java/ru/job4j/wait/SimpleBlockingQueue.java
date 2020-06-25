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
    private boolean flag = true;

    public synchronized boolean getPeek() {
        return queue.peek() != null;
    }

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

    public void checkProduce() {
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

    public void checkConsume() {
        synchronized (Thread.currentThread()) {
            while (flag) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void offer(T value) {
        if (count >= 3) {
            off();
        }
        checkProduce(); //while false - sleep
        queue.offer(value);
        count++;
        System.out.println("Добавлено " + value + " count = " + count + " flag = " + flag + " peek " + queue.peek());
    }

    public synchronized T poll() {
        if (queue.peek() == null) {
            on();
        }
        checkConsume(); //while true == sleep
        T value = queue.poll();
        count--;
        return value;
    }
}
