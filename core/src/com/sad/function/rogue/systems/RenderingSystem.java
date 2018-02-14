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
    private static final float WORLD_TO_BOX = 1/16f;

    public static void run(Batch batch, EntityManager em) {

        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] { SpriteComponent.class, PhysicsComponent.class })) {
            //get the PhysicsComponent once. Maybe it saves a bunch of lookups, who knows? I probably should.
            PhysicsComponent tmp = em.getComponent(entity, PhysicsComponent.class);
            SpriteComponent spriteTemp = em.getComponent(entity, SpriteComponent.class);

            //Convert all units from world, to the Box2D units.
            batch.draw(spriteTemp.sprite,
                    tmp.body.getPosition().x - (spriteTemp.sprite.getWidth() * WORLD_TO_BOX / 2),  //DON'T FORGET TO FIX OFFSET, We're using lower-left as the origin and box2d uses the center as an origin.
                    tmp.body.getPosition().y - (spriteTemp.sprite.getHeight() * WORLD_TO_BOX / 2), //DON'T FORGET TO FIX OFFSET, We're using lower-left as the origin and box2d uses the center as an origin.
                    spriteTemp.sprite.getWidth() * WORLD_TO_BOX,
                    spriteTemp.sprite.getHeight() * WORLD_TO_BOX);

        }


    }
}