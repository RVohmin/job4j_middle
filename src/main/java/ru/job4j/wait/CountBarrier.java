package ru.job4j.wait;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void check() {
        synchronized (monitor) {
            while (count != total) {
                try {
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
    }

    public void await() {
        check();
        System.out.println("Await run");
    }
}
