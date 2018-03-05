package com.sad.function.rogue.components;

import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MoverComponent2 extends Component {
    private EntityManager manager;
    private UUID target;

    public MoverComponent2(EntityManager manager, UUID target) {
        this.manager = manager;
        this.target = target;
    }

    public void move(float dx, float dy) {
        //TODO Write the Physics move method.
        manager.getComponent(target, PhysicsComponent.class).body.applyForceToCenter(dx, dy, false);
    }
}
