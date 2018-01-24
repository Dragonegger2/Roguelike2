package com.sad.function.rogue.systems;

/**
 * Renders things on the screen.
 *
 * In order to draw entities they have to have a sprite/animation and a position.
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;

import java.util.UUID;

public class RenderingSystem extends BaseSystem {
    private SpriteBatch batch;

    private Texture debug;

    public RenderingSystem() {
        init();
    }

    public void init() {
        batch = new SpriteBatch();
    }

    @Override
    public void run(float delta, EntityManager em) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] { SpriteComponent.class, TransformComponent.class })) {
            batch.draw(em.getComponent(entity, SpriteComponent.class).sprite,
                    em.getComponent(entity, TransformComponent.class).x,
                    em.getComponent(entity, TransformComponent.class).y);

        }
        batch.end();


    }

}