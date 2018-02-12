package com.sad.function.rogue.systems;

/**
 * Renders things on the screen.
 *
 * In order to draw entities they have to have a sprite/animation and a position.
 *
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.components.PhysicsComponent;
import com.sad.function.rogue.components.SpriteComponent;

import java.util.UUID;

public class RenderingSystem {

    public RenderingSystem() {
        init();
    }

    public void init() {

    }

    public void run(Batch batch, EntityManager em) {

        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] { SpriteComponent.class, PhysicsComponent.class })) {
            batch.draw(em.getComponent(entity, SpriteComponent.class).sprite,
                    em.getComponent(entity, PhysicsComponent.class).body.getPosition().x * 16,
                    em.getComponent(entity, PhysicsComponent.class).body.getPosition().y * 16);

        }


    }

}