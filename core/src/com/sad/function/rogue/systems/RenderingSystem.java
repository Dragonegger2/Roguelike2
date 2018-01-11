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
    private ShapeRenderer shape;

    private Camera camera;

    private Texture blueRect;
    private Texture redRect;
    private Texture debug;

    public RenderingSystem() {
        init();

    }

    /**
     * Allows for a camera to be added in the constructor.
     * @param camera
     */
    public RenderingSystem(Camera camera) {
        this.camera = camera;
        init();
    }

    public void init() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);

        blueRect = new Texture("blueCollisionBox.png");
        redRect = new Texture("redCollisionBox.png");
    }

    @Override
    public void run(float delta, EntityManager em) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] { SpriteComponent.class, TransformComponent.class })) {
            batch.draw(em.getComponent(entity, SpriteComponent.class).Sprite.getTexture(),
                    em.getComponent(entity, TransformComponent.class).x,
                    em.getComponent(entity, TransformComponent.class).y);

//            drawRect(batch,
//                    blueRect,
//                    em.getComponent(entity, TransformComponent.class).x,
//                    em.getComponent(entity, TransformComponent.class).y,
//                    em.getComponent(entity, SpriteComponent.class).Sprite.getTexture().getWidth(),
//                    em.getComponent(entity, SpriteComponent.class).Sprite.getTexture().getHeight(),
//                    10
//                            );
        }
        batch.end();

/*
        if(camera != null) {
            camera.update();
            batch.setProjectionMatrix(camera.combined);
        }

        batch.begin();
        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] { SpriteComponent.class, TransformComponent.class })) {

            //I have to offset the image...
            batch.draw(em.getComponent(entity, SpriteComponent.class).Sprite.getTexture(),
                    em.getComponent(entity,TransformComponent.class).x,
                    em.getComponent(entity, TransformComponent.class).y,
                    em.getComponent(entity, TransformComponent.class).originX,
                    em.getComponent(entity, TransformComponent.class).originY,
                    em.getComponent(entity, SpriteComponent.class).width,
                    em.getComponent(entity, SpriteComponent.class).height,
                    em.getComponent(entity, SpriteComponent.class).scaleX,
                    em.getComponent(entity, SpriteComponent.class).scaleY,
                    em.getComponent(entity, SpriteComponent.class).rotation,
                    em.getComponent(entity, SpriteComponent.class).Sprite.getRegionX(),
                    em.getComponent(entity, SpriteComponent.class).Sprite.getRegionY(),
                    em.getComponent(entity, SpriteComponent.class).Sprite.getRegionWidth(),
                    em.getComponent(entity, SpriteComponent.class).Sprite.getRegionHeight(),
                    em.getComponent(entity, TransformComponent.class).left,
                    false
            );

            drawRect(batch,
                    redRect,
                    em.getComponent(entity,TransformComponent.class).x,
                    em.getComponent(entity, TransformComponent.class).y,
                    em.getComponent(entity, SpriteComponent.class).width,
                    em.getComponent(entity, SpriteComponent.class).height,
                    2);

        }
        batch.end();
*/
    }

    /**
     * Method to draw a rectangle without actually invoking the ShapeRenderer.
     * @param batch
     * @param rect
     * @param x
     * @param y
     * @param width
     * @param height
     * @param thickness
     */
    private void drawRect(Batch batch, Texture rect, float x, float y, float width, float height, int thickness) {
        batch.draw(rect, x, y, width, thickness);
        batch.draw(rect, x, y, thickness, height);
        batch.draw(rect, x, y+height-thickness, width, thickness);
        batch.draw(rect, x+width-thickness, y, thickness, height);
    }

/*
void drawLine(Batch batch, Texture rect, int x1, int y1, int x2, int y2, int thickness) {
        int dx = x2-x1;
        int dy = y2-y1;
        float dist = (float)Math.sqrt(dx*dx + dy*dy);
        float rad = (float)Math.atan2(dy, dx);
        batch.draw(rect, x1, y1, dist, thickness, 0, 0, rad);
    }
    */
}