package ru.job4j.concurrent;

/**
 * Simulation of loading process
 * Note! In block catch must be interrupt thread
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i <= 100; i++) {
                        System.out.print("\rLoading progress: " + i + "%");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("\nLoading success! Congratulation!");
                }
        );
        thread.start();
    }
}
