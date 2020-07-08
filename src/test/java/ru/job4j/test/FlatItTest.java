package ru.job4j.test;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FlatItTest {
    @Test
    public void whenIt() {
        Iterator<Iterator<Integer>> it = List.of(
                List.of(1).iterator(), List.of(2, 3).iterator()
        ).iterator();
        assertEquals(List.of(1, 2, 3), FlatIt.flatten(it));
    }

}