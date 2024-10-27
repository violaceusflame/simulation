package io.github.violaceusflame.pathfinder;

import io.github.violaceusflame.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Path {
    public static Path of(List<Coordinates> coordinates) {
        return new Path(coordinates);
    }

    public static Path empty() {
        return new Path(new ArrayList<>());
    }

    private final List<Coordinates> coordinates;

    private Path(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isEmpty() {
        return coordinates.isEmpty();
    }

    public List<Coordinates> getCoordinates() {
        return Collections.unmodifiableList(coordinates);
    }

    public Optional<Coordinates> getTargetCoordinates() {
        if (isEmpty()) {
            return Optional.empty();
        }

        int lastIndex = coordinates.size() - 1;
        return Optional.of(coordinates.get(lastIndex));
    }

    public int size() {
        return coordinates.size();
    }
}
