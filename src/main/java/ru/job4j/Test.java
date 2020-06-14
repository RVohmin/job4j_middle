package ru.job4j;

/**
 * Test class for experiments from lessons
 */
public class Test extends Thread {
    public void run() {
        System.out.println("I’m " + this.getName());
    }

    String name1 = "Оля";
    String name2 = "Лена";

    /**
     * replaced reference value
     */
    public void swap() {
        synchronized (this) {
            String s = name1;
            name1 = name2;
            name2 = s;
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.swap();
        System.out.println(test.name1);
        System.out.println(test.name2);

    }
}
