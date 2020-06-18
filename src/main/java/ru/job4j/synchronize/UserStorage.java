package ru.job4j.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> store = new ArrayList<>();

    public synchronized List<User> getStore() {
        return store;
    }

    public synchronized boolean add(User user) {
        final var i = store.indexOf(user);
        if (i == -1) {
            store.add(user);
        } else {
            System.out.println("User with such id is exist in store, use update()");
            return false;
        }
        return true;
    }

    public synchronized boolean update(User user) {
        final var i = store.indexOf(user);
        if (i != -1) {
            store.set(i, user);
            return true;
        }
        System.out.println("No user with such id");
        return false;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = null;
        User to = null;
        for (User item : store) {
            if (item.getId() == fromId) {
                from = item;
            }
            if (item.getId() == toId) {
                to = item;
            }
        }
        if ((from != null && to != null) && from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
        } else {
            System.out.println("transfer can not be made");
        }
    }
}
