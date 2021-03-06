package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Simulation of loading process
 * Note! In block catch must be interrupt thread
 */
public class Wget {
    /**
     * Get size file at URL
     *
     * @param http - String url
     * @return Long size of file in bytes
     * @throws IOException
     */
    private long getSizeFile(String http) throws IOException {
        URL url = new URL(http);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("HEAD");
        return httpConnection.getContentLengthLong();
    }

    /**
     * Method prints in console download progress
     *
     * @param size        size of downloading file
     * @param currentSize - current size downloaded file
     * @param speed       - speed of downloading in bytes per msec
     * @throws InterruptedException
     */
    private void progressPrint(long size, long currentSize, int speed) throws InterruptedException {
        int progress = (int) ((currentSize * 100 / size));
        System.out.print("\rLoading progress: "
                .concat(String.valueOf(progress))
                .concat("%")
                .concat(" Download speed: ")
                .concat(String.valueOf(speed)));
    }

    /**
     * Method downloading file, after download must be renamed^ default name - file.txt
     *
     * @param url   - String with path to file for download
     * @param speed - limit speed for download
     * @throws IOException
     */
    public void fileDownload(String url, int speed, String destFileName) throws IOException {
        int kb = 1024;
        long size = getSizeFile(url);
        speed = speed * kb / 1000; // скорость байт / мсек
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destFileName)) {
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
                progressPrint(size, sumBytes, currentSpeed);
            }
            System.out.println();
            System.out.println("Total downloaded: " + (sumBytes / kb) + " Kb");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalStateException("Number of arguments must be 3: url speed destFileName");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String destFileName = args[2];

        Wget wget = new Wget();
        wget.fileDownload(url, speed, destFileName);

    }
}
