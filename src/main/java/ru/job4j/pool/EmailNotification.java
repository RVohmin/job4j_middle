package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void runPool() {
        System.out.println("Execute " + Thread.currentThread().getName());
    }

    void emailTo(User user) {
        String subject = "Notification ".concat(user.getUsername().concat(" to email ").concat(user.getEmail()));
        String body = "Add a new event to ".concat(user.getUsername());
        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    public void send(String subject, String body, String email) {
    }
}
