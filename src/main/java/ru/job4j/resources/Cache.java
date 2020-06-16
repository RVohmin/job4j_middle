package ru.job4j.resources;

/**
 * Код содержит ошибку атомарности. Поправить код.
 * public class Cache {
 * private static Cache cache;
 * <p>
 * private static Cache instOf() {
 * if (cache == null) {
 * cache = new Cache();
 * }
 * return cache;
 * }
 * }
 */

public class Cache {
    private static Cache instOf() {
        return new Cache();
    }
}
