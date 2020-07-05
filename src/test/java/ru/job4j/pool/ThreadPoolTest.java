package ru.job4j.pool;

import org.junit.Test;

public class ThreadPoolTest {
    @Test
    public void threadPoolTest() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 10000; i++) {
            pool.work(() -> System.out.println(Thread.currentThread().getName() + " Worked"));
        }
        pool.shutdown();
    }

}