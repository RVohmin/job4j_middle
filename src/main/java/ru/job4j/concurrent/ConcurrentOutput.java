package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> {
                    System.out.println("another: " + Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println("second " + Thread.currentThread().getName());
                }
        );
        another.start();
        second.start();
        System.out.println("main: " + Thread.currentThread().getName());
    }
}
