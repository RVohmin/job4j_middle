package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownload {
    private long getSizeFile(String http) throws IOException {
        URL url = new URL(http);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("HEAD");
        return httpConnection.getContentLengthLong();
    }

    private void progressPrint(long size, long currentSize) {
//        Thread thread = new Thread(
//                () -> {
//                    for (int i = 0; i <= (currentSize / size) * 100; i++) {
//                        System.out.print("\rLoading progress: " + i + "%");
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            Thread.currentThread().interrupt();
//                        }
//                    }
//                    System.out.println("\nLoading success! Congratulation!");
//                }
//        );
//        thread.start();
        int progress = (int) ((currentSize * 100 / size));
        System.out.print("\rLoading progress: " + progress + "%");
    }

    public void fileDownload(String url, int speed) throws IOException {
        int kb = 1024;
        long size = getSizeFile(url);
        speed = speed * kb / 1000; // скорость байт / мсек
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("car.jpg")) {
            byte[] dataBuffer = new byte[kb];
            int bytesRead;
            int sumBytes = 0;
            long downloadTime = 0;
            int currentSpeed;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, kb)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                sumBytes += bytesRead;
                downloadTime = System.currentTimeMillis() - start;
                if (downloadTime == 0) {
                    downloadTime = 100;
                    Thread.sleep(100);
                }
                currentSpeed = (int) (sumBytes / (downloadTime));
                if (currentSpeed > speed) {
                    int delay = (currentSpeed / speed) * 1000;
                    Thread.sleep(delay);
                }
                progressPrint(size, sumBytes);
            }
            System.out.println();
            System.out.println("Total downloaded: " + (sumBytes / kb) + " Kb");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        FileDownload fileDownload = new FileDownload();
        fileDownload.fileDownload("https://s1.1zoom.ru/b5050/458/294916-svetik_1920x1200.jpg", 150);
    }

}
