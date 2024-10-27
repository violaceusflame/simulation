package io.github.violaceusflame.entity.creature;

public class Herbivore extends Creature {
    public Herbivore(int healthPoints, int speed) {
        super(healthPoints, speed);
    }

    public void damage(int damagePower) {
        healthPoints -= damagePower;
    }
}
