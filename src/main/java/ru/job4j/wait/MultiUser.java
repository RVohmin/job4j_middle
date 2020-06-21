package ru.job4j.wait;

public class MultiUser {
    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(2);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.await();
                    System.out.println("Await from master finished");
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.await();
                    System.out.println("Await from slave finished");
                },
                "Slave"
        );
        master.start();
        barrier.count();
        slave.start();
        barrier.count();

    }
}
