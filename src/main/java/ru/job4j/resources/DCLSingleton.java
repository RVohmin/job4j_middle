package ru.job4j.resources;

public class DCLSingleton {
    /**
     * field must be volatile
     */
    private static volatile DCLSingleton inst;

    /**
     * empty private constructor
     */
    private DCLSingleton() {
    }

    /**
     * Method for get instance, essentially replaces constructor,
     * thread-safe and double-check
     *
     * @return single instance of Class
     */
    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }
}
