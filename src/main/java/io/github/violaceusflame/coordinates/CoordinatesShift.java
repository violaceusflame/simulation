package io.github.violaceusflame.coordinates;

public class CoordinatesShift {
    public final int x;
    public final int y;

    public static CoordinatesShift of(int x, int y) {
        return new CoordinatesShift(x, y);
    }

    private CoordinatesShift(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
