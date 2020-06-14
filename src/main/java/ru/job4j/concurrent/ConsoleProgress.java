package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        Thread progress = new Thread(
                () -> {

                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.print("\rload: " + "-");
                        System.out.print("\rload: " + "\\");
                        System.out.print("\rload: " + "|");
                        System.out.print("\rload: " + "/");
                    }
                }
        );
        progress.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    public static void main(String[] args) {
        ConsoleProgress consoleProgress = new ConsoleProgress();
        consoleProgress.run();
    }
}
