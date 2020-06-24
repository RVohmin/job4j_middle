package ru.job4j.wait;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        AtomicBoolean flag = new AtomicBoolean(true);

        final Thread consumer = new Thread(
                () -> {
                    while (flag.get() || queue.getPeek()) {
                        Integer i = queue.poll();
                        System.out.println(i);
                    }
                }, "Consumer"
        );
        consumer.start();

        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.off();
                    flag.set(false);
                }, "Producer"
        );
        producer.start();
    }
}
