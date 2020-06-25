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
                    while (i <= 11) {
                        queue.offer(i);
                        i++;
                    }
                    queue.wakeUpThreads();
                    System.out.println("Producer is dead");
                }, "Производитель (offer)"
        );

        Thread consumer = new Thread(
                () -> {
                    var i = 1;
                    while (i <= 11) {
                        System.out.println("Получено " + queue.poll());
                        i++;
                    }
                    System.out.println("Consumer is dead");
                }, "Потребитель (poll)"
        );
        produser.start();
        consumer.start();
        produser.join();
        consumer.join();
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(1, 6).forEach(queue::offer);
                }, "Thread Producer"
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmptyQueue() || !Thread.currentThread().isInterrupted()) {
                        buffer.add(queue.poll());
                    }
                }, "Thread Consumer"
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertEquals(buffer, Arrays.asList(1, 2, 3, 4, 5));
    }
}