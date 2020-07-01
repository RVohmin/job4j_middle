package ru.job4j.nonblock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheModel {
    private final Map<Integer, Base> map = new ConcurrentHashMap<>();

    public void add(Base model) {
        if (map.putIfAbsent(model.getId(), model) != null) {
            try {
                throw new OptimisticException("Such Base model existing, use update()");
            } catch (OptimisticException ex) {
                ex.getMessage();
            }
        }
    }

    public void update(Base model) {
        AtomicInteger newVersion = new AtomicInteger(model.getVersion());
        int oldVersion = map.get(model.getId()).getVersion();
        if (newVersion.get() != oldVersion) {
            throw new OptimisticException("Error on update, model versions is differencing");
        }
        map.computeIfPresent(model.getId(), (key, value) -> {
            model.setVersion(newVersion.incrementAndGet());
            return model;
        });
    }

    public void delete(Base model) {
        map.remove(model.getId());
    }

    public Base getByID(int id) {
        return map.get(id);
    }

    public void print() {
        map.forEach((x, data) -> System.out.println(data));
    }

    public static void main(String[] args) {
        CacheModel cacheModel = new CacheModel();
        Base model1 = new Base(1, 0, "Alex");
        Base model2 = new Base(1, 0, "Alex2");
        cacheModel.add(model1);
        cacheModel.print();
        cacheModel.update(model2);
        cacheModel.print();
    }
}
