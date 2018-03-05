package com.sad.function.rogue.event;

import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.MoverComponent;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

/**
 * Contains all of the logic to fire a move or attack event.
 */
public class MoveEvent implements IEvent {
    private int dx;
    private int dy;
    private UUID map;
    private UUID target;
    private EntityManager entityManager;
    /**
     *
     * @param dx Delta of X
     * @param dy Delta of Y
     * @param map Tile map, used to check for occupied spaces.
     * @param target Registering object.
     */
    public MoveEvent(int dx, int dy, EntityManager entityManager, UUID target, UUID map) {
        this.dx = dx;
        this.dy = dy;
        this.map = map;
        this.target = target;
        this.entityManager = entityManager;
    }

    @Override
    public void Execute() {
        entityManager.getComponent(target, MoverComponent.class).moveOrAttack(dx, dy, entityManager.getComponent(map, MapComponent.class).dungeon.map);
    }

}
