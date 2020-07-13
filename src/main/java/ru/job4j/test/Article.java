package ru.job4j.test;

/**
 * Задаy большой текст. Из него вырезают слова и предложения. Из них составляют новый текст.
 * Нужно проверить, что новый текст был получен из оригинально.
 */
public class Article {
    public static boolean generateBy(String origin, String line) {
        String[] lineArray = line.split(" ");
        for (String word : lineArray) {
            if (!origin.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
