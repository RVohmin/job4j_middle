package ru.job4j.synchronize;

import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class SingleLockListTest {

    @Test
    public void whenAdd() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new Thread(
                () -> list.add(1)
        );
        Thread second = new Thread(
                () -> list.add(2)
        );
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertEquals(Set.of(1, 2), rsl);
    }
}