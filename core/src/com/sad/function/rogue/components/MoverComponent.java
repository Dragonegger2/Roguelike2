package com.sad.function.rogue.components;

import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

/**
 * Gives an entity the ability move via it's box2d physics body.
 */
public class MoverComponent extends Component {
    private EntityManager manager;
    private UUID target;

    public MoverComponent(EntityManager manager, UUID target) {
        this.manager = manager;
        this.target = target;
    }

    public void moveOrAttack(float dx, float dy) {

        manager.getComponent(target, PhysicsComponent.class).body.setLinearVelocity(dx, dy);

    }
}
