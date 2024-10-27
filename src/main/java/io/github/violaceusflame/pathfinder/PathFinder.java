package io.github.violaceusflame.pathfinder;

import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.entity.Entity;

public interface PathFinder {
    Path find(Coordinates startCoordinates, Class<? extends Entity> targetClass);
}
