package ru.job4j.synchronize;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddNotExistUserThenTrue() {
        final UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        assertTrue(storage.add(new User(2, 200)));
        assertEquals(storage.getStore().size(), 2);
    }

    @Test
    public void whenAddExistUserThenFalse() {
        final UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        assertFalse(storage.add(new User(1, 300)));
    }

    @Test
    public void whenUpdateExistUserThenTrue() {
        final UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        User updUser = new User(2, 250);
        assertTrue(storage.update(updUser));
        int indexOfUpdUser = storage.getStore().indexOf(updUser);
        assertEquals(storage.getStore().get(indexOfUpdUser), updUser);
    }

    @Test
    public void whenUpdateNotExistUserThenFalse() {
        final UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        User updUser = new User(3, 300);
        assertFalse(storage.update(updUser));
    }

    @Test
    public void whenDeleteExistUser() {
        final UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        assertTrue(storage.getStore().contains(user2));
        storage.delete(user2);
        assertFalse(storage.getStore().contains(user2));
    }

    @Test
    public void whenDeleteNotExistUser() {
        final UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        User user3 = new User(3, 300);
        storage.add(user1);
        storage.add(user2);
        assertFalse(storage.delete(user3));
        assertFalse(storage.getStore().contains(user3));
    }

    @Test
    public void whenTransfer() {
        final UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(2, 1, 200);
        assertEquals(user1.getAmount(), 300);
        assertEquals(user2.getAmount(), 0);
    }

    private class ThreadCount extends Thread {
        private final UserStorage storage;

        private ThreadCount(final UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            storage.transfer(2, 1, 50);
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        Thread first = new ThreadCount(storage);
        Thread second = new ThreadCount(storage);
        first.start();
        second.start();
        System.out.println(storage.getStore());

        first.join();
        second.join();
        var index1 = storage.getStore().indexOf(user1);
        var index2 = storage.getStore().indexOf(user2);

        assertEquals(user1.getAmount(), storage.getStore().get(index1).getAmount());
        assertEquals(user2.getAmount(), storage.getStore().get(index2).getAmount());
        assertEquals(200, storage.getStore().get(index1).getAmount());
        assertEquals(100, storage.getStore().get(index2).getAmount());
        System.out.println(storage.getStore());
    }
}