package ru.job4j.concurrent;

/**
 * task Режим ожидания. [#304630]
 * This class demonstrate work of method sleep() (state BLOCKING - TIMED_WAITING)
 * and needed InterruptedException
 */
public class ThreadSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(1000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
        System.out.println(thread.getState());
    }
}
