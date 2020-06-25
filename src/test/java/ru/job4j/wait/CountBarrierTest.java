package ru.job4j.wait;

import org.junit.Test;

public class CountBarrierTest {
    @Test
    public void countTest() throws InterruptedException {

        CountBarrier countBarrier = new CountBarrier(2);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                    System.out.println("Await from master finished");
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                    try {
                        System.out.println(Thread.currentThread().getName() + " sleep 3000");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countBarrier.count();
                    System.out.println("Slave from slave finished");
                },
                "Slave"
        );
        master.start();
        slave.start();
        master.join();
        slave.join();
    }
}