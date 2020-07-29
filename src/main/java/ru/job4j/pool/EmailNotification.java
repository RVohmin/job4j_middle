package ru.job4j.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    void emailTo(User user) throws ExecutionException, InterruptedException {
        String subject = "Notification ".concat(user.getUsername().concat(" to email ").concat(user.getEmail()));
        String body = "Add a new event to ".concat(user.getUsername());
        Future<String> future = pool.submit(() -> send(subject, body, user.getEmail()));
        System.out.println(future.get());
    }

    public String send(String subject, String body, String email) {
        return "string";
    }
}
