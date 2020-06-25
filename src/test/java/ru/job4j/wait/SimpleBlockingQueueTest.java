package ru.job4j.wait;

import org.junit.Test;

public class SimpleBlockingQueueTest {
    @Test
    public void blockingQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();

        Thread produser = new Thread(
                () -> {
                    var i = 1;
                    while (i <= 11) {
                        queue.offer(i);
                        i++;
                    }
                    queue.off();
                    System.out.println("Producer final");
                }, "Производитель (offer)"
        );

        Thread consumer = new Thread(
                () -> {
                    var i = 1;
                    while (i <= 11) {
                        System.out.println("Получено " + queue.poll());
                        i++;
                    }
                }, "Потребитель (poll)"
        );
        produser.start();
        consumer.start();
        produser.join();
        consumer.join();
    }
}