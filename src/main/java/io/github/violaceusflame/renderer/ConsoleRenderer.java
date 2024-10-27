package io.github.violaceusflame.renderer;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.coordinates.Coordinates;
import io.github.violaceusflame.entity.*;
import io.github.violaceusflame.entity.creature.Herbivore;
import io.github.violaceusflame.entity.creature.Predator;

import java.util.Optional;

public class ConsoleRenderer implements Renderer {
    private enum EntityView {
        GRASS("🌿"),
        ROCK("🪨"),
        TREE("🌳"),
        HERBIVORE("🐇"),
        PREDATOR("🐆");

        public final String view;

        EntityView(String view) {
            this.view = view;
        }
    }

    private static final String EMPTY_CELL = "🟫";

    private final WorldMap worldMap;

    public ConsoleRenderer(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void render() {
        for (int y = 0; y < worldMap.getHeight(); y++) {
            for (int x = 0; x < worldMap.getWidth(); x++) {
                StringBuilder line = new StringBuilder();
                Coordinates coordinates = Coordinates.of(x, y);
                Optional<Entity> entityOptional = worldMap.getEntity(coordinates);
                if (entityOptional.isEmpty()) {
                    line.append(EMPTY_CELL);
                } else {
                    Entity entity = entityOptional.get();
                    line.append(getEntityView(entity));
                }
                System.out.print(line);
            }
            System.out.println();
        }
        System.out.println();
    }

    private String getEntityView(Entity entity) {
        if (entity instanceof Grass) {
            return EntityView.GRASS.view;
        } else if (entity instanceof Rock) {
            return EntityView.ROCK.view;
        } else if (entity instanceof Tree) {
            return EntityView.TREE.view;
        } else if (entity instanceof Herbivore) {
            return EntityView.HERBIVORE.view;
        } else if (entity instanceof Predator) {
            return EntityView.PREDATOR.view;
        }

        throw new IllegalArgumentException("Not found view for entity class: " + entity.getClass().getName());
    }
}
