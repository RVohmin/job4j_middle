package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int n = 250;
        Thread progress = new Thread(
                () -> {
                    try {

                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.print("\rload: " + "-");
                            Thread.sleep(n);
                            System.out.print("\rload: " + "\\");
                            Thread.sleep(n);
                            System.out.print("\rload: " + "|");
                            Thread.sleep(n);
                            System.out.print("\rload: " + "/");
                            Thread.sleep(n);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
