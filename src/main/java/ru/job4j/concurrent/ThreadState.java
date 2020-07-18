package ru.job4j.concurrent;

/**
 * Cоздано две нити. Каждая нить должна вывести свое имя на консоль.
 * Нить main должна дождаться завершения этих нитей и вывести на консоль сообщение, что работа завершена.
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }, "Thread-1");

        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }, "Thread-2");

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED && second.getState() != Thread.State.TERMINATED) {
            System.out.println("Thread 1 or 2 are worked");
        }
    }
}
