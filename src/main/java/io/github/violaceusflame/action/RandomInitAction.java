package io.github.violaceusflame.action;

import io.github.violaceusflame.worldmap.WorldMap;
import io.github.violaceusflame.entityfactory.EntityFactory;
import io.github.violaceusflame.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RandomInitAction extends RandomPlaceAction {
    private final EntityFactory entityFactory;
    private boolean isHerbivoreSpawned;
    private boolean isPredatorSpawned;

    public RandomInitAction(WorldMap worldMap, EntityFactory entityFactory) {
        super(worldMap);
        this.entityFactory = entityFactory;
    }

    @Override
    protected Entity createEntity() {
        // guarantee spawn herbivore or predator
        if (!isHerbivoreSpawned) {
            isHerbivoreSpawned = true;
            return entityFactory.createHerbivore();
        }
        if (!isPredatorSpawned) {
            isPredatorSpawned = true;
            return entityFactory.createPredator();
        }

        List<Supplier<Entity>> supplierList = createSuppliersList();
        int randomIndex = randomizer.nextInt(supplierList.size());
        return supplierList.get(randomIndex).get();
    }

    private List<Supplier<Entity>> createSuppliersList() {
        return new ArrayList<>(List.of(
                entityFactory::createHerbivore,
                entityFactory::createGrass,
                entityFactory::createRock,
                entityFactory::createTree,
                entityFactory::createPredator
        ));
    }
}
