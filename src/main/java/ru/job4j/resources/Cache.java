package ru.job4j.resources;

/**
 * Код содержит ошибку атомарности. Поправить код.
 * public class Cache {
 * private static Cache cache;
 * private static Cache instOf() {
 * if (cache == null) {
 * cache = new Cache();
 * }
 * return cache;
 * }
 * }
 */

public class Cache {
    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
