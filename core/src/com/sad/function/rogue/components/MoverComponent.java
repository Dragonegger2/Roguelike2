package com.sad.function.rogue.components;

import com.sad.function.rogue.objects.Tile;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MoverComponent extends Component {
    private EntityManager manager;
    private UUID target;

    public MoverComponent(EntityManager manager, UUID target) {
        this.manager = manager;
        this.target = target;
    }

    public void moveOrAttack(int dx, int dy, Tile[][] map) {
        //If you aren't blocked by a blocking thing.
            if (!map[manager.getComponent(target, TransformComponent.class).x + dx][manager.getComponent(target, TransformComponent.class).y + dy].blocked) {
                manager.getComponent(target, TransformComponent.class).x += dx;
                manager.getComponent(target, TransformComponent.class).y += dy;

            }
    }
}
