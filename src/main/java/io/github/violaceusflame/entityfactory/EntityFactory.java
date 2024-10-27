package io.github.violaceusflame.entityfactory;

import io.github.violaceusflame.entity.Entity;

public interface EntityFactory {
    Entity createGrass();
    Entity createRock();
    Entity createTree();
    Entity createHerbivore();
    Entity createPredator();
}
