package ru.job4j.wait;

/**
 * Class described thread with counter. If counter == total, method "Await" can be execute
 */
public class CountBarrier {
    private final Object monitor = this;
    /**
     * outcome value for condition for put to sleep the threads
     */
    private final int total;
    /**
     * counter
     */
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Method increased count, if condition is met, waked all threads
     */
    public synchronized void count() {
        count++;
        if (count == total) {
            monitor.notifyAll();
        }
    }

    /**
     * Method checked for count == total and put to sleep the thread if condition isn't met
     */
    public synchronized void await() {
        while (!(count == total)) {
            try {
                System.out.println(Thread.currentThread().getName() + " sleep");
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Await run");
    }
}
