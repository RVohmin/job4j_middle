package ru.job4j.test;

import java.util.*;

/**
 * ru.job4j.test
 *
 * @author romanvohmin
 * @since 17.07.2020
 */
public class Test {
    static <T> List<T> merge(Iterator<T> i1, Iterator<T> i2, Comparator<T> comparator) {
        List<T> list = new ArrayList<>();
        T a = i1.next();
        T b = i2.next();

        while (true) {
            if (comparator.compare(a, b) <= 0) {
                list.add(a);
                a = i1.next();
            } else {
                list.add(b);
                b = i2.next();
            }
            if (!i1.hasNext()) {
                list.add(b);
                list.add(a);
                i2.forEachRemaining(list::add);
                break;
            }
            if (!i2.hasNext()) {
                list.add(b);
                list.add(a);
                i1.forEachRemaining(list::add);
                break;
            }
        }
        i2.forEachRemaining(list::add);
        return list;
    }

    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 5, 12, 66, 66, 67);
        List<Integer> l2 = Arrays.asList(2, 3, 6, 7, 8, 12, 33, 66, 92, 134);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 6, 7, 8, 12, 12, 33, 66, 66, 66, 67, 92, 134);
        List<Integer> merged1 = merge(l1.iterator(), l2.iterator(), Integer::compareTo);
        System.out.println(expected.equals(merged1) ? "Test PASSED" : "Test FAILED");
        System.out.println(merged1);
        List<Integer> merged2 = merge(l2.iterator(), l1.iterator(), Integer::compareTo);
        System.out.println(expected.equals(merged2) ? "Test PASSED" : "Test FAILED");
        System.out.println(merged2);
    }
}
