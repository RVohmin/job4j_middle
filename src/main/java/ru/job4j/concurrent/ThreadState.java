package ru.job4j.concurrent;

/**
 * Cоздано две нити. Каждая нить должна вывести свое имя на консоль.
 * Нить main должна дождаться завершения этих нитей и вывести на консоль сообщение, что работа завершена.
 */
public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    System.out.println("Thread first worked");
                });
        System.out.println("Thread first after created: " + first.getName() + " status: " + first.getState());
        Thread second = new Thread(
                () -> {
                    System.out.println("Thread second worked");
                });
        System.out.println("Thread second after created: " + first.getName() + " status: " + second.getState());
        first.start();
        System.out.println("Thread first after start: " + first.getName() + " status: " + first.getState());
        Thread.sleep(1000);
        second.start();
        first.join();
        System.out.println("Thread second: after start: " + first.getName() + " status: " + second.getState());
        second.join();
        Thread.sleep(1000);
        first.join();
        second.join();
        System.out.println(first.getState());
        System.out.println(second.getState());

        System.out.println("main: " + Thread.currentThread().getState() + ", first & second threads are finished");
    }
}
