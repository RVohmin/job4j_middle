package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(size);
    private final List<Thread> list = new LinkedList<>();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            queue.poll().run();
                        }
                    });
            list.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) {
        queue.offer(job);
    }

    public void shutdown() {
        for (Thread thread : list) {
            thread.interrupt();
        }
    }
}
