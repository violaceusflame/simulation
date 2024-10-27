package io.github.violaceusflame.worldmap;

import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.entity.Entity;
import io.github.violaceusflame.entity.creature.Creature;

import java.util.List;
import java.util.Optional;

public interface WorldMap {
    Optional<Entity> getEntity(Coordinates coordinates);
    void setEntity(Coordinates coordinates, Entity entity);
    void removeEntity(Entity entity);
    List<Creature> getCreatures();
    Optional<Coordinates> getEntityCoordinates(Entity entity);
    int getHeight();
    int getWidth();
    int getGrassCount();
    List<Coordinates> getEmptyCellsCoordinates();
    boolean isValidCoordinates(Coordinates coordinates);
    boolean isEmpty(Coordinates coordinates);
}
