package ru.job4j.synchronize;

import java.io.*;

/**
 * Поправьте код с ошибками в коде.
 * - Ошибки в многопоточности - добавлено synchronized ко всем методам для исключения ситуации
 * когда один поток читает файл, другой пишет в него. Добавлено volatile в поле класса для исключения
 * работы с кешированным значением.
 * <p>
 * - Ошибки в IO - добавлена буферизация, try with resources для автоматического закрывания
 * IO потоков с обработкой исключений в catch, убрано из сигнатуры throws. Добавлена проверка на
 * существование файла для сохранения. Убран не нужный цикл.
 * <p>
 * - Ошибки в производительности - String заменен на StringBuilder - так как методы
 * синхронизированы, нет необходимости использовать StringBuffer.
 */
public class ParseFile {
    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        StringBuilder builder = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                builder.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public synchronized String getContentWithoutUnicode() {
        StringBuilder builder = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                if (data < 0x80) {
                    builder.append(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public synchronized void saveContent(String content) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File for writing not exist");
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
