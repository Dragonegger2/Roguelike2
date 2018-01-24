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

    public void move(int dx, int dy, Tile[][] map) {
//        manager.getAllComponentsOfType(MapComponent.class).toArray()[0]; Get the tile map.
        //If you aren't blocked by a blocking thing.
        try {
            if (!map[manager.getComponent(target, TransformComponent.class).x + dx][manager.getComponent(target, TransformComponent.class).y + dy].blocked) {
                manager.getComponent(target, TransformComponent.class).x += dx;
                manager.getComponent(target, TransformComponent.class).y += dy;
            }
        }
        catch(Exception e) {
            System.out.println("Matching component was not found");
        }

    }
}
