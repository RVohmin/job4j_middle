package ru.job4j.resources;

import net.jcip.annotations.NotThreadSafe;

/**
 * 1. Ниже приведет код не потокобезопасного класса описывающего узел односвязанного списка.
 * 2. Сделай этот класс @Immutable.
 *
 * @param <T> - Type of Node value
 */
@NotThreadSafe
public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
