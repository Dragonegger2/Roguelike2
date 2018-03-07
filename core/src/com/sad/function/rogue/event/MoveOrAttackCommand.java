package com.sad.function.rogue.event;

import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MoveOrAttackCommand implements ICommand {
    private int dx;
    private int dy;

    /**
     *
     * @param dx Delta of X
     * @param dy Delta of Y
     */
    public MoveOrAttackCommand(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }


    @Override
    public void Execute(EntityManager entityManager, UUID target) {
        UUID map = entityManager.getAllEntitiesPossessingComponent(MapComponent.class).iterator().next();
        if (!entityManager.getComponent(map, MapComponent.class).dungeon.map[entityManager.getComponent(target, TransformComponent.class).x + dx][entityManager.getComponent(target, TransformComponent.class).y + dy].blocked) {
            entityManager.getComponent(target, TransformComponent.class).x += dx;
            entityManager.getComponent(target, TransformComponent.class).y += dy;
        }
    }
}
