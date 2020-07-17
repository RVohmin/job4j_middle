package ru.job4j.switcher;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ru.job4j.switcher
 *
 * @author romanvohmin
 * @since 16.07.2020
 */
@ThreadSafe
public class MasterSlaveBarrier {
    @GuardedBy("this")
    private final AtomicInteger count = new AtomicInteger(0);

    public synchronized void tryMaster() {
        while (count.get() != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void trySlave() {
        while (count.get() != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void doneMaster() {
        System.out.println(Thread.currentThread().getName());
        count.incrementAndGet();
        notifyAll();
    }

    public synchronized void doneSlave() {
        System.out.println(Thread.currentThread().getName());
        count.decrementAndGet();
        notifyAll();
    }
}
