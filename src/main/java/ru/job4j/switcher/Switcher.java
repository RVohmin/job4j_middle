package ru.job4j.switcher;

/**
 * ru.job4j.switcher
 *
 * @author romanvohmin
 * @since 16.07.2020
 */
public class Switcher {
    public static void main(String[] args) {
        MasterSlaveBarrier barrier = new MasterSlaveBarrier();

        Thread first = new Thread(
                () -> {
                    while (true) {
                        barrier.tryMaster();
                        barrier.doneMaster();
                    }
                }, "Thread A"
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        barrier.trySlave();
                        barrier.doneSlave();
                    }
                }, "Thread B"
        );
        first.start();
        second.start();
    }
}
