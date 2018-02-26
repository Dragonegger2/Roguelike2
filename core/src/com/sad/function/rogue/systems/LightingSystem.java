package com.sad.function.rogue.systems;

/**
 * Renders things on the screen.
 *
 * In order to draw entities they have to have a sprite/animation and a position.
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.sad.function.rogue.components.Light;
import com.sad.function.rogue.components.TransformComponent;

import java.util.UUID;

/**
 * Rendering system like that mentioned here:
 * https://techblog.orangepixel.net/2015/07/shine-a-light-on-it/
 */
public class LightingSystem {

    private static FrameBuffer lightBuffer;

    private static LightingSystem _instance;
    private static Color shadeColor = new Color(0.3f,0.3f,0.3f,.9f);

    private LightingSystem() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClearColor(shadeColor.r, shadeColor.g, shadeColor.b,  shadeColor.a); //Default clear color I'm using. Can be overridden by calling before this.
    }

    public static LightingSystem getInstance() {
        if(_instance == null) {
            _instance = new LightingSystem();
        }
        return _instance;
    }

    public void renderLighting(Batch batch, EntityManager em) {

        lightBuffer.begin();

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // setup the right blending

            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.begin();
                for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] {Light.class, TransformComponent.class})) {
                    // set the color of your light (red,green,blue,alpha values)

                    batch.setColor(em.getComponent(entity, Light.class).color);

//                    TODO: Current lighting bugs exist here:
                    // and renderLighting the sprite
                    batch.draw(em.getComponent(entity, Light.class).source,
                            em.getComponent(entity, TransformComponent.class).x,
                            em.getComponent(entity,TransformComponent.class).y);

                    batch.setColor(Color.WHITE); //White is the default color. If you don't, we'll get bleeding issues.
                }

            batch.end();
        lightBuffer.end();


        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);

        batch.begin();
            batch.draw(lightBuffer.getColorBufferTexture(), 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

    }

    /**
     * This resizes the frame buffer to reflect the current size of the screen.
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        // if lightBuffer was created before, dispose, we recreate a new one
        if (lightBuffer!=null) lightBuffer.dispose();
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }
}