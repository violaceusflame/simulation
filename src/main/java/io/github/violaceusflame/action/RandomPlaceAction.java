package io.github.violaceusflame.action;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class RandomPlaceAction implements Action {
    protected static final Random randomizer = new Random();
    protected static final double DEFAULT_MIN_ENTITIES_FACTOR = 0.2;
    protected static final double DEFAULT_MAX_ENTITIES_FACTOR = 0.4;
    private static final double MIN_RANGE_ENTITIES_FACTOR = 0.0;
    private static final double MAX_RANGE_ENTITIES_FACTOR = 1.0;

    protected final WorldMap worldMap;
    protected final int minEntities;
    protected final int maxEntities;

    public RandomPlaceAction(WorldMap worldMap) {
        this.worldMap = worldMap;
        minEntities = calculateMinEntities();
        maxEntities = calculateMaxEntities();
    }

    private int calculateMinEntities() {
        double minEntitiesFactor = getMinEntitiesFactor();
        validateFactor(minEntitiesFactor);
        int countOfMapCells = getCountOfMapCells();
        return (int) Math.floor(countOfMapCells * minEntitiesFactor);
    }

    private int calculateMaxEntities() {
        double maxEntitiesFactor = getMaxEntitiesFactor();
        validateFactor(maxEntitiesFactor);
        if (getMinEntitiesFactor() >= maxEntitiesFactor) {
            throw new IllegalArgumentException("Max entities factor must be greater than min factor");
        }
        int countOfMapCells = getCountOfMapCells();
        return (int) Math.ceil(countOfMapCells * maxEntitiesFactor);
    }

    private int getCountOfMapCells() {
        return worldMap.getHeight() * worldMap.getWidth();
    }

    private void validateFactor(double factor) {
        if (factor < MIN_RANGE_ENTITIES_FACTOR) {
            throw new IllegalArgumentException("Factor must be non-negative");
        } else if (factor > MAX_RANGE_ENTITIES_FACTOR) {
            throw new IllegalArgumentException("Factor must be less than 1");
        }
    }

    protected double getMinEntitiesFactor() {
        return DEFAULT_MIN_ENTITIES_FACTOR;
    }

    protected double getMaxEntitiesFactor() {
        return DEFAULT_MAX_ENTITIES_FACTOR;
    }

    @Override
    public void execute() {
        for (int i = 0; i < getPlaceCount(); i++) {
            Optional<Coordinates> randomEmptyCoordinates = getRandomEmptyCoordinates();
            if (randomEmptyCoordinates.isEmpty()) {
                break;
            }
            worldMap.setEntity(randomEmptyCoordinates.get(), createEntity());
        }
    }

    private int getPlaceCount() {
        int randomPlaceCount = randomizer.nextInt(minEntities, maxEntities + 1);
        return randomPlaceCount == 0 ? 1 : randomPlaceCount;
    }

    private Optional<Coordinates> getRandomEmptyCoordinates() {
        List<Coordinates> emptyCellsCoordinates = worldMap.getEmptyCellsCoordinates();
        if (emptyCellsCoordinates.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = randomizer.nextInt(emptyCellsCoordinates.size());
        return Optional.of(emptyCellsCoordinates.get(randomIndex));
    }

    protected abstract Entity createEntity();
}
