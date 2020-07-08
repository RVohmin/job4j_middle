package ru.job4j.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlatIt {

    public static List<Integer> flatten(Iterator<Iterator<Integer>> it) {
        List<Integer> list = new ArrayList<>();
        while (it.hasNext()) {
            Iterator<Integer> v = it.next();
            while (v.hasNext()) {
                list.add(v.next());
            }
        }
        return list;
    }
}
