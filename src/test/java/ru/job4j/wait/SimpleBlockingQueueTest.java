package ru.job4j.wait;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void blockingQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);

        Thread produser = new Thread(
                () -> {
                    var i = 1;
                    while (i <= 11 && !Thread.currentThread().isInterrupted()) {
                        queue.offer(i);
                        i++;
                    }
                    System.out.println("Producer is dead");
                }, "Производитель (offer)"
        );

        Thread consumer = new Thread(
                () -> {
                    var i = 1;
                    while (i <= 11) {
                        queue.poll();
//                        System.out.println("Получено " + queue.poll());
                        i++;
                    }
                    System.out.println("Consumer is dead");
                }, "Потребитель (poll)"
        );
        produser.start();
        consumer.start();
        produser.join();
        consumer.interrupt();
        consumer.join();
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
//                    for (int i = 1; i < 6; i++) {
//                        queue.offer(i);
//                    }
                    IntStream.range(1, 6).forEach(queue::offer);
                    System.out.println("Producer is dead");
                }, "Thread Producer"
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (Exception e) {
                            System.out.println("Исключение типа InterruptedException перехвачено");
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Consumer is dead");
                }, "Thread Consumer"
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), buffer);
    }
}