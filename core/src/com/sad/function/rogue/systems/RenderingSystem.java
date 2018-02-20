package com.sad.function.rogue.systems;

/**
 * Renders things on the screen.
 *
 * In order to draw entities they have to have a sprite/animation and a position.
 *
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;

import java.util.UUID;

public class RenderingSystem {
    private static final float WORLD_TO_BOX = 1/16f;

    public static void run(Batch batch, EntityManager em) {

        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] { SpriteComponent.class, TransformComponent.class })) {
            //get the PhysicsComponent once. Maybe it saves a bunch of lookups, who knows? I probably should.
            TransformComponent tmp = em.getComponent(entity, TransformComponent.class);
            SpriteComponent spriteTemp = em.getComponent(entity, SpriteComponent.class);

            //Convert all units from world, to the Box2D units.
            batch.draw(spriteTemp.sprite,
                    tmp.x,
                    tmp.y,
                    spriteTemp.sprite.getWidth(),
                    spriteTemp.sprite.getHeight());

        }


    }
}