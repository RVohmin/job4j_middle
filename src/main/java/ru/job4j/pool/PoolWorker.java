package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

/**
 * Запасной класс (для ThreadPool.java), представляет собой Thread c единственной задачей
 * queue.poll().run();
 */
public class PoolWorker extends Thread {
    private final SimpleBlockingQueue<Runnable> queue;

    public PoolWorker(SimpleBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            queue.poll().run();
        }
    }
}
