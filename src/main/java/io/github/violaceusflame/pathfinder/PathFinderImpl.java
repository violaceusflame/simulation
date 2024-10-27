package io.github.violaceusflame.pathfinder;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.coordinates.CoordinatesShift;
import io.github.violaceusflame.entity.Entity;

import java.util.*;

public class PathFinderImpl implements PathFinder {
    private static final CoordinatesShift[] COORDINATE_SHIFTS = new CoordinatesShift[]{
            CoordinatesShift.of(1, 0),
            CoordinatesShift.of(0, 1),
            CoordinatesShift.of(-1, 0),
            CoordinatesShift.of(0, -1),
            CoordinatesShift.of(1, 1),
            CoordinatesShift.of(1, -1),
            CoordinatesShift.of(-1, 1),
            CoordinatesShift.of(-1, -1)
    };

    private final WorldMap worldMap;
    private List<Coordinates> visited;
    private Map<Coordinates, Coordinates> coordinateTransitions;

    public PathFinderImpl(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Path find(Coordinates startCoordinates, Class<? extends Entity> targetClass) {
        visited = new ArrayList<>();
        coordinateTransitions = new HashMap<>();
        Queue<Coordinates> queue = new ArrayDeque<>();

        queue.add(startCoordinates);

        while (!queue.isEmpty()) {
            Coordinates dequeuedCoordinates = queue.remove();

            Optional<Entity> entityOptional = worldMap.getEntity(dequeuedCoordinates);
            if (entityOptional.isPresent() &&
                    startCoordinates != dequeuedCoordinates &&
                    isAchieveTarget(entityOptional.get(), targetClass)) {
                return getResultPath(startCoordinates, dequeuedCoordinates);
            }

            for (CoordinatesShift coordinatesShift : COORDINATE_SHIFTS) {
                Coordinates newCoordinates = dequeuedCoordinates.add(coordinatesShift);
                if (isValidCoordinates(newCoordinates) && isCanMove(newCoordinates, targetClass) && !isVisited(newCoordinates)) {
                    queue.add(newCoordinates);
                    visited.add(newCoordinates);
                    coordinateTransitions.put(newCoordinates, dequeuedCoordinates);
                }
            }
        }

        return Path.empty();
    }

    private Path getResultPath(Coordinates startCoordinates, Coordinates endCoordinates) {
        List<Coordinates> result = new ArrayList<>();

        Coordinates unspecifiedCoordinates = Coordinates.unspecified();
        coordinateTransitions.put(startCoordinates, unspecifiedCoordinates);
        Coordinates currentCoordinates = endCoordinates;
        while (currentCoordinates != unspecifiedCoordinates) {
            result.add(currentCoordinates);
            currentCoordinates = coordinateTransitions.get(currentCoordinates);
        }
        Collections.reverse(result);

        return Path.of(result);
    }

    private boolean isCanMove(Coordinates newCoordinates, Class<? extends Entity> targetClass) {
        Optional<Entity> entity = worldMap.getEntity(newCoordinates);
        return entity.isEmpty() || isAchieveTarget(entity.get(), targetClass);
    }

    private boolean isAchieveTarget(Entity entity, Class<? extends Entity> targetClass) {
        return entity.getClass() == targetClass;
    }

    private boolean isVisited(Coordinates coordinates) {
        return visited.contains(coordinates);
    }

    private boolean isValidCoordinates(Coordinates coordinates) {
        return worldMap.isValidCoordinates(coordinates);
    }
}
