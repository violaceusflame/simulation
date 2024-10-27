package io.github.violaceusflame.entity.creature;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.pathfinder.Path;

import java.util.Optional;

public class Predator extends Creature {
    private static final int NEAREST_DISTANCE_TO_TARGET = 1;

    private final int damagePower;

    public Predator(int healthPoints, int speed, int damagePower) {
        super(healthPoints, speed);
        this.damagePower = damagePower;
    }

    @Override
    public void makeMove(WorldMap worldMap, Path path) {
        setPath(path);

        if (isNearHerbivore(path)) {
            Optional<Herbivore> targetHerbivoreOptional = getHerbivore(worldMap, path);
            Herbivore targetHerbivore = targetHerbivoreOptional.orElseThrow();
            if (targetHerbivore.isAlive()) {
                targetHerbivore.damage(damagePower);
                return;
            }
        }

        super.makeMove(worldMap, path);
    }

    private Optional<Herbivore> getHerbivore(WorldMap worldMap, Path path) {
        if (path.isEmpty()) {
            return Optional.empty();
        }
        Coordinates herbivoreCoordinates = path.getTargetCoordinates().orElseThrow();
        Herbivore herbivore = (Herbivore) worldMap.getEntity(herbivoreCoordinates).orElseThrow();
        return Optional.of(herbivore);
    }

    private boolean isNearHerbivore(Path path) {
        return path.size() - 1 == NEAREST_DISTANCE_TO_TARGET;
    }
}
