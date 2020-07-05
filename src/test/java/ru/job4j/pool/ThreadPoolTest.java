package ru.job4j.pool;

import org.junit.Test;

public class ThreadPoolTest {
    @Test
    public void threadPoolTest() {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.work(() -> {
                System.out.println(Thread.currentThread().getName() + " Worked");
            });
            System.out.println(i);
        }
        pool.shutdown();
    }

}