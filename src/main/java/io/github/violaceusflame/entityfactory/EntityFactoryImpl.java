package io.github.violaceusflame.entityfactory;

import io.github.violaceusflame.entity.Entity;
import io.github.violaceusflame.entity.Grass;
import io.github.violaceusflame.entity.Rock;
import io.github.violaceusflame.entity.Tree;
import io.github.violaceusflame.entity.creature.Herbivore;
import io.github.violaceusflame.entity.creature.Predator;

public class EntityFactoryImpl implements EntityFactory {
    public Entity createGrass() {
        return new Grass();
    }

    public Entity createRock() {
        return new Rock();
    }

    public Entity createTree() {
        return new Tree();
    }

    public Entity createHerbivore() {
        Herbivore herbivore = new Herbivore(5, 1);
        herbivore.setTarget(Grass.class);
        return herbivore;
    }

    public Entity createPredator() {
        Predator predator = new Predator(10, 1, 1);
        predator.setTarget(Herbivore.class);
        return predator;
    }
}
