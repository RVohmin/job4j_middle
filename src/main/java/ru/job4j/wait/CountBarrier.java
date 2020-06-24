package ru.job4j.wait;

/**
 * Class described thread with counter. If counter == total, method awayte can be execute
 */
public class CountBarrier {
    private final Object monitor = this;

    private final int total;
    private boolean flag = false;
    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
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

    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            monitor.notifyAll();
        }
    }

    public synchronized void count() {
        count++;
        if (count == total) {
            on();
        }
    }

    public void await() {
        check();
        System.out.println("Await run");
    }
}
