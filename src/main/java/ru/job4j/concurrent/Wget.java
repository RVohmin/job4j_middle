package ru.job4j.concurrent;

/**
 * Simulation of loading process
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
                            e.printStackTrace();
                        }
                    }
                    System.out.println("\nLoading successed! Congratulation!");
                }
        );
        thread.start();
    }
}
