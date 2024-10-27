package io.github.violaceusflame.action;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.entity.creature.Creature;
import io.github.violaceusflame.pathfinder.Path;
import io.github.violaceusflame.pathfinder.PathFinder;

public class TurnAction implements Action {
    private final WorldMap worldMap;
    private final PathFinder pathFinder;

    public TurnAction(WorldMap worldMap, PathFinder pathFinder) {
        this.worldMap = worldMap;
        this.pathFinder = pathFinder;
    }

    @Override
    public void execute() {
        for (Creature creature : worldMap.getCreatures()) {
            if (!creature.isAlive()) {
                continue;
            }
            Path path = pathFinder.find(worldMap.getEntityCoordinates(creature).get(), creature.getTarget());
            creature.makeMove(worldMap, path);
        }
    }
}
