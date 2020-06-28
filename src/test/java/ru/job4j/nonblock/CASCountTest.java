package ru.job4j.nonblock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CASCountTest {
    @Test
    public void increment() {
        CASCount<Integer> casCount = new CASCount<>();
        List<Integer> exp = List.of(1, 2, 3, 4, 5);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            casCount.increment();
            list.add(casCount.getValue());
        }
        assertEquals(exp, list);
    }

}