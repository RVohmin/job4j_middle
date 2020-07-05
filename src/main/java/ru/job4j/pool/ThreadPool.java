package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> blockingQueue = new SimpleBlockingQueue<>(size);
    List<MyThread> threadList = new LinkedList<>();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threadList.add(new MyThread(blockingQueue));
        }
        for (MyThread thread : threadList) {
            thread.start();
        }
    }

    public void work(Runnable job) {
        blockingQueue.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threadList) {
            thread.interrupt();
        }
    }
}
