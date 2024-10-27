package io.github.violaceusflame.entity.creature;

import io.github.violaceusflame.entity.CreatureResource;
import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.entity.Entity;
import io.github.violaceusflame.pathfinder.Path;

import java.util.Optional;

public abstract class Creature extends Entity {
    protected int maxHealthPoints;
    protected int healthPoints;
    protected int speed;
    protected Path path;
    protected Class<? extends Entity> targetClass;

    public Creature(int maxHealthPoints, int speed) {
        this.healthPoints = this.maxHealthPoints = maxHealthPoints;
        this.speed = speed;
    }

    public Class<? extends Entity> getTarget() {
        return targetClass;
    }

    public void setTarget(Class<? extends Entity> targetClass) {
        this.targetClass = targetClass;
    }

    protected void setPath(Path path) {
        this.path = path;
    }

    public boolean isAlive() {
        return healthPoints > 0;
    }

    public void makeMove(WorldMap worldMap, Path path) {
        setPath(path);

        Optional<Coordinates> nextCoordinatesOptional = getNextCoordinates(path);
        if (nextCoordinatesOptional.isEmpty()) {
            return;
        }
        Coordinates nextCoordinates = nextCoordinatesOptional.get();
        Optional<Entity> entityOptional = worldMap.getEntity(nextCoordinates);
        entityOptional.ifPresent(this::restoreHealthPoints);
        move(worldMap, nextCoordinates);
    }

    private Optional<Coordinates> getNextCoordinates(Path path) {
        if (path.isEmpty()) {
            return Optional.empty();
        }

        if (path.size() - 1 < speed) {
            return path.getTargetCoordinates();
        }
        return Optional.of(path.getCoordinates().get(speed));
    }

    private void restoreHealthPoints(Entity entity) {
        Optional<Integer> restoringHealthPointsOptional = getRestoringHealthPoints(entity);
        if (restoringHealthPointsOptional.isEmpty()) {
            return;
        }

        int restoringHealthPoints = restoringHealthPointsOptional.get();
        if (healthPoints + restoringHealthPoints <= maxHealthPoints) {
            healthPoints += restoringHealthPoints;
        }
    }

    private Optional<Integer> getRestoringHealthPoints(Entity entity) {
        if (entity instanceof CreatureResource creatureResource) {
            return Optional.of(creatureResource.getRestoringHealthPoints());
        } else {
            return Optional.empty();
        }
    }

    private void move(WorldMap worldMap, Coordinates nextCoordinates) {
        worldMap.removeEntity(this);
        worldMap.setEntity(nextCoordinates, this);
    }

    public boolean isHaveWay() {
        return path != null && !path.getCoordinates().isEmpty();
    }
}
