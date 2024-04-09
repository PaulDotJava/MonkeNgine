package org.monkeg.api.entity;

import java.util.HashSet;

import org.monkeg.api.util.logging.Log;

public class Entity {
    private static final HashSet<Entity> entities = new HashSet<>();

    public Entity() {
        Log.debug("Creating entity (" + this.getClass().getName() + ")");
        entities.add(this);
    }

    public void delete() {
        Log.debug("Deleting entity (" + this.getClass().getName() + ")");
        entities.remove(this);
    }

    public void freeResources() {
        Log.debug("Freeing resources from entity (" + this.getClass().getName() + ")");
    }

    public void update(double dt) {}

    public static HashSet<Entity> getEntities() {
        return entities;
    }
}
