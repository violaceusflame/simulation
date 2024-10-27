package io.github.violaceusflame.coordinates;

import java.util.Objects;

public class Coordinates {
    public final int x;
    public final int y;

    public static Coordinates of(int x, int y) {
        return new Coordinates(x, y);
    }

    public static Coordinates unspecified() {
        return new Coordinates(-1, -1);
    }

    public static Coordinates zero() {
        return new Coordinates(0, 0);
    }

    private Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates add(CoordinatesShift other) {
        return new Coordinates(x + other.x, y + other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
