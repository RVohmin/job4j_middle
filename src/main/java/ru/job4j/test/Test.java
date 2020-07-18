package ru.job4j.test;

import ru.job4j.synchronize.Count;

/**
 * ru.job4j.test
 *
 * @author romanvohmin
 * @since 17.07.2020
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }
}
