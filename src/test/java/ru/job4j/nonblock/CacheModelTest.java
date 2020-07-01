package ru.job4j.nonblock;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class CacheModelTest {
    @Test
    public void whenUpdateExistThenThrowException() throws InterruptedException {
        AtomicReference<RuntimeException> optiExc = new AtomicReference<>();
        CacheModel cacheModel = new CacheModel();
        Base model1 = new Base(1, 0, "Alex");
        Base model2 = new Base(1, 0, "Bob");
        Base model3 = new Base(1, 0, "Roy");
        cacheModel.add(model1);
        Thread thread1 = new Thread(
                () -> {
                    try {
                        cacheModel.update(model2);
                    } catch (OptimisticException e) {
                        optiExc.set(e);
                    }
                }, "Thread1"
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        cacheModel.update(model3);
                    } catch (OptimisticException e) {
                        optiExc.set(e);
                    }
                }, "Thread2"
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertNotNull(optiExc.get());
        assertEquals("Error on update, model versions is differencing", optiExc.get().getMessage());
    }

    @Test
    public void whenUpdateNotExistThenTrue() throws InterruptedException {
        AtomicReference<RuntimeException> optiExc = new AtomicReference<>();
        CacheModel cacheModel = new CacheModel();
        Base model1 = new Base(1, 0, "Alex");
        Base model2 = new Base(1, 0, "Bob");
        Base model3 = new Base(2, 0, "Roy");
        Base model4 = new Base(2, 0, "Royal");
        cacheModel.add(model1);
        cacheModel.add(model3);

        Thread thread1 = new Thread(
                () -> {
                    try {
                        cacheModel.update(model2);
                    } catch (OptimisticException e) {
                        optiExc.set(e);
                    }
                }, "Thread1"
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        cacheModel.update(model4);
                    } catch (OptimisticException e) {
                        optiExc.set(e);
                    }
                }, "Thread2"
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertNull(optiExc.get());
        assertEquals(model2, cacheModel.getByID(1));
        assertEquals(model4, cacheModel.getByID(2));
    }

    @Test
    public void whenDeleteAndGetThenNull() throws InterruptedException {
        AtomicReference<RuntimeException> optiExc = new AtomicReference<>();
        CacheModel cacheModel = new CacheModel();
        Base model1 = new Base(1, 0, "Alex");
        Base model3 = new Base(2, 0, "Roy");

        Thread thread1 = new Thread(
                () -> {
                    try {
                        cacheModel.add(model1);
                        cacheModel.add(new Base(1, 0, "Petr"));
                        cacheModel.add(model3);
                    } catch (OptimisticException e) {
                        optiExc.set(e);
                    }
                }, "Thread1"
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        cacheModel.delete(model1);
                    } catch (OptimisticException e) {
                        optiExc.set(e);
                    }
                }, "Thread2"
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertNull(optiExc.get());
        assertNull(cacheModel.getByID(1));
    }
}