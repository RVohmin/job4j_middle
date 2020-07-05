package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

public class MyThread extends Thread {
    private final SimpleBlockingQueue<Runnable> queue;

    public MyThread(SimpleBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            queue.poll().run();
        }
    }
}
