package com.sad.function.rogue.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.sad.function.rogue.objects.Tile;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MoverComponent extends Component {
    private EntityManager manager;
    private UUID target;

    private float WORLD_TO_BOX = 1/16f;
    public MoverComponent(EntityManager manager, UUID target) {
        this.manager = manager;
        this.target = target;
    }

    public void moveOrAttack(int dx, int dy, Tile[][] map) {
        //If you aren't blocked by a blocking thing.
        try {
            Body boxBody = manager.getComponent(target, PhysicsComponent.class).body;

//            if (!map[manager.getComponent(target, TransformComponent.class).x + dx][manager.getComponent(target, TransformComponent.class).y + dy].blocked) {
//                manager.getComponent(target, TransformComponent.class).x += dx;
//                manager.getComponent(target, TransformComponent.class).y += dy;
//
                //The Transform class is still being used to check for collisions. If I correctly modify values to world/box coordinates I shouldn't have to.
//            boxBody.setTransform(1, 1, 0f);
//            }

            boxBody.applyForceToCenter(dx/60, dx/60, true);
        }
        catch(Exception e) {
            System.out.println("Matching component was not found");
        }

    }
}
