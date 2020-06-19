package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * job4j_design ru.job4j.list.SimpleArray
 *
 * @author romanvohmin
 * @since 15.04.2020 19:37
 */
public class MyLinkedList<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int modCount = 0;
    private int size = 0;

    public Node<T> getFirst() {
        return first;
    }

    public Node<T> getLast() {
        return last;
    }

    public static class Node<T> {
        T value;
        Node<T> next;
        Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            newNode.next = null;
            newNode.previous = null;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
        modCount++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            clear();
            return;
        }
        if (index == size - 1) {
            deleteLast();
            return;
        }
        if (index == 0) {
            deleteFirst();
            return;
        }
        Node<T> node = first;
        int count = 0;
        while (node.next != null) {
            if (count == index) {
                node.previous.next = node.next;
                node.next.previous = node.previous;
                size--;
                modCount++;
                return;
            }
            node = node.next;
            count++;
        }
    }

    public void deleteLast() {
        if (size == 1) {
            clear();
            return;
        }
        last = last.previous;
        last.next = null;
        size--;
        modCount++;
    }

    public void deleteFirst() {
        if (size == 1) {
            clear();
            return;
        }
        first = first.next;
        first.previous = null;
        size--;
        modCount++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.value;
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
        modCount++;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        final int expectedModCount = modCount;
        return new Iterator<>() {
            Node<T> node = first;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        Node<T> node = first;
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = node.value;
            node = node.next;
        }
        return Arrays.toString(arr);
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

    }
}
