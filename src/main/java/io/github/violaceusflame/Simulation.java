package io.github.violaceusflame;

import io.github.violaceusflame.action.Action;
import io.github.violaceusflame.renderer.Renderer;
import io.github.violaceusflame.worldmap.WorldMap;

import java.util.function.Predicate;

public class Simulation {
    private static final Predicate<WorldMap> EMPTY_END_CONDITION = map -> false;
    private static final Action EMPTY_GRASS_REFILL_ACTION = () -> {};

    private int movesCount;
    private final WorldMap worldMap;
    private final Renderer renderer;
    private Action initAction;
    private Action turnAction;
    private Action grassRefillAction;
    private Predicate<WorldMap> endCondition;
    private boolean isPaused;

    public Simulation(WorldMap worldMap, Renderer renderer) {
        this.worldMap = worldMap;
        this.renderer = renderer;
    }

    public void setInitAction(Action initAction) {
        this.initAction = initAction;
    }

    public void setTurnAction(Action turnAction) {
        this.turnAction = turnAction;
    }

    public void setEndCondition(Predicate<WorldMap> endCondition) {
        this.endCondition = endCondition;
    }

    public void setGrassRefillAction(Action grassRefillAction) {
        this.grassRefillAction = grassRefillAction;
    }

    public void nextTurn() {
        grassRefillAction.execute();
        renderer.render();
        turnAction.execute();
        movesCount++;
    }

    public void start() {
        validateStart();
        initAction.execute();
        mainLoop();
    }

    private void validateStart() {
        if (initAction == null) {
            throw new IllegalStateException("Can't start simulation: initAction is null");
        }
        if (turnAction == null) {
            throw new IllegalStateException("Can't start simulation: turnAction is null");
        }
        if (endCondition == null) {
            endCondition = EMPTY_END_CONDITION;
        }
        if (grassRefillAction == null) {
            grassRefillAction = EMPTY_GRASS_REFILL_ACTION;
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    private void mainLoop() {
        do {
            while (isPaused) {
                Utils.sleep(200);
            }
            nextTurn();
            Utils.sleep(1000);
        } while (!endCondition.test(worldMap));
        System.exit(0);
    }
}
