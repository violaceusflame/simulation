package io.github.violaceusflame;

import io.github.violaceusflame.action.RandomGrassRefillAction;
import io.github.violaceusflame.action.RandomInitAction;
import io.github.violaceusflame.action.TurnAction;
import io.github.violaceusflame.entity.creature.Creature;
import io.github.violaceusflame.entityfactory.EntityFactory;
import io.github.violaceusflame.entityfactory.EntityFactoryImpl;
import io.github.violaceusflame.pathfinder.PathFinder;
import io.github.violaceusflame.pathfinder.PathFinderImpl;
import io.github.violaceusflame.renderer.ConsoleRenderer;
import io.github.violaceusflame.renderer.Renderer;
import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.worldmap.WorldMapImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMapImpl(10, 10);
        Simulation simulation = createSimulation(worldMap);
        Thread togglePauseThread = new Thread(() -> togglePauseOnInput(simulation));
        togglePauseThread.start();
        simulation.start();
    }

    private static Simulation createSimulation(WorldMap worldMap) {
        Renderer renderer = new ConsoleRenderer(worldMap);
        PathFinder pathFinder = new PathFinderImpl(worldMap);
        EntityFactory entityFactory = new EntityFactoryImpl();

        Simulation simulation = new Simulation(worldMap, renderer);
        simulation.setInitAction(new RandomInitAction(worldMap, entityFactory));
        simulation.setTurnAction(new TurnAction(worldMap, pathFinder));
        simulation.setGrassRefillAction(new RandomGrassRefillAction(worldMap));
        simulation.setEndCondition(map -> map.getCreatures().stream().noneMatch(Creature::isHaveWay));

        return simulation;
    }

    private static void togglePauseOnInput(Simulation simulation) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean isNeedsPause = true;
            while (true) {
                reader.readLine();
                if (isNeedsPause) {
                    simulation.pause();
                } else {
                    simulation.resume();
                }
                isNeedsPause = !isNeedsPause;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
