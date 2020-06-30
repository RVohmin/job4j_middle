package ru.job4j.nonblock;

import java.util.Objects;

public class Base {
    private final int id;
    private int version;
    String name;

    public Base(int id, int version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return getId() == base.getId()
                && getVersion() == base.getVersion()
                && Objects.equals(getName(), base.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "{id=" + id
                + ", version=" + version
                + ", name='" + name
                + '\'' + '}';
    }
}
