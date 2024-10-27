package io.github.violaceusflame.worldmap;

import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.entity.Entity;
import io.github.violaceusflame.entity.Grass;
import io.github.violaceusflame.entity.creature.Creature;

import java.util.*;

public class WorldMapImpl implements WorldMap {
    private static final Coordinates START_COORDINATES = Coordinates.zero();

    private final int height;
    private final int width;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMapImpl(int width, int height) {
        validate(width, height);
        this.width = width;
        this.height = height;
    }

    private void validate(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
    }

    @Override
    public void setEntity(Coordinates coordinates, Entity entity) {
        if (!isValidCoordinates(coordinates)) {
            throw new IllegalArgumentException("Invalid coordinates");
        }

        entities.put(coordinates, entity);
    }

    @Override
    public boolean isValidCoordinates(Coordinates coordinates) {
        return coordinates.x >= getStartCoordinates().x &&
                coordinates.x <= getEndCoordinates().x &&
                coordinates.y >= getStartCoordinates().y &&
                coordinates.y <= getEndCoordinates().y;
    }

    @Override
    public void removeEntity(Entity entity) {
        if (!entities.containsValue(entity)) {
            throw new IllegalArgumentException("Entity not found");
        }

        entities.remove(getEntityCoordinates(entity).get());
    }

    @Override
    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entities.get(coordinates));
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    private Coordinates getStartCoordinates() {
        return START_COORDINATES;
    }

    private Coordinates getEndCoordinates() {
        return Coordinates.of(width - 1, height - 1);
    }

    @Override
    public List<Creature> getCreatures() {
        return entities.values().stream()
                .filter(c -> c instanceof Creature)
                .map(c -> (Creature) c)
                .toList();
    }

    @Override
    public Optional<Coordinates> getEntityCoordinates(Entity entity) {
        return entities.entrySet().stream()
                .filter(e -> e.getValue() == entity)
                .findFirst()
                .map(Map.Entry::getKey);
    }

    @Override
    public boolean isEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    @Override
    public int getGrassCount() {
        return (int) entities.values().stream()
                .filter(c -> c instanceof Grass)
                .count();
    }

    @Override
    public List<Coordinates> getEmptyCellsCoordinates() {
        List<Coordinates> result = new ArrayList<>();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Coordinates coordinates = Coordinates.of(j, i);
                if (!entities.containsKey(coordinates)) {
                    result.add(coordinates);
                }
            }
        }

        return result;
    }
}
