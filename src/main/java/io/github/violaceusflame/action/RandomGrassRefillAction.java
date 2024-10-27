package io.github.violaceusflame.action;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.entity.Entity;
import io.github.violaceusflame.entity.Grass;

public class RandomGrassRefillAction extends RandomPlaceAction {
    public RandomGrassRefillAction(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public void execute() {
        if (worldMap.getGrassCount() == 0) {
            super.execute();
        }
    }

    @Override
    protected double getMinEntitiesFactor() {
        return 0.05;
    }

    @Override
    protected double getMaxEntitiesFactor() {
        return 0.1;
    }

    @Override
    protected Entity createEntity() {
        return new Grass();
    }
}
