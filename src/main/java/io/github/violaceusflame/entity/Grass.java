package io.github.violaceusflame.entity;

public class Grass extends Entity implements CreatureResource {
    @Override
    public int getRestoringHealthPoints() {
        return 1;
    }
}
