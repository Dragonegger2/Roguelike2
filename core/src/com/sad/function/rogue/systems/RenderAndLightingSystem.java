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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.sad.function.rogue.components.Light;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;

import java.util.UUID;

/**
 * Rendering system like that mentioned here:
 * https://techblog.orangepixel.net/2015/07/shine-a-light-on-it/
 */
public class RenderAndLightingSystem {

    private static FrameBuffer lightBuffer;
    private static TextureRegion lightBufferRegion;

    private static RenderAndLightingSystem _instance;
    private static Color baseColor = new Color(1.0f, 1.0f, 1.0f, 0.99607843f);
    private static Color shadeColor = new Color(0.3f,0.3f,0.3f,1);
//    private static Color shadeColor = new Color(0.3f,0.38f,0.4f,1);

    private RenderAndLightingSystem() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClearColor(shadeColor.r, shadeColor.g, shadeColor.b,  shadeColor.a); //Default clear color I'm using. Can be overridden by calling before this.
    }

    public static RenderAndLightingSystem getInstance() {
        if(_instance == null) {
            _instance = new RenderAndLightingSystem();
        }
        return _instance;
    }

    public void render(Batch spriteBatch, EntityManager em) {
        renderLightedComponents(spriteBatch, em);

        renderLights(spriteBatch, em);

//        renderUI(spriteBatch, em);
    }

    /**
     * This resizes the frame buffer to reflect the current size of the screen.
     * @param lowDisplayW
     * @param lowDisplayH
     */
    public void resize(int lowDisplayW, int lowDisplayH) {
        // Fakedlight system (alpha blending)

        // if lightBuffer was created before, dispose, we recreate a new one
        if (lightBuffer!=null) lightBuffer.dispose();
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, lowDisplayW, lowDisplayH, false);

        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,lightBuffer.getHeight()-lowDisplayH,lowDisplayW,lowDisplayH);

        lightBufferRegion.flip(false, false);
    }

    /**
     * All components that will be impacted by lighting will be rendered here.
     * @param batch
     * @param em
     */
    private void renderLightedComponents(Batch batch, EntityManager em) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        //            Render all objects to the screen before applying lights.
        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] {SpriteComponent.class, TransformComponent.class})) {

            batch.draw(em.getComponent(entity, SpriteComponent.class).sprite,
                    em.getComponent(entity, TransformComponent.class).x,
                    em.getComponent(entity, TransformComponent.class).y,
                    em.getComponent(entity, SpriteComponent.class).sprite.getWidth(),
                    em.getComponent(entity, SpriteComponent.class).sprite.getHeight());
        }

        batch.end();
    }

    /**
     * Render all lights.
     *
     * Makes use of a new component LightComponent.
     *
     * TODO: Add distance to the lights and make sure that they become centered from it.
     * @param spriteBatch
     * @param em
     */
    private void renderLights(Batch spriteBatch, EntityManager em) {
        lightBuffer.begin();

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // setup the right blending
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            Gdx.gl.glEnable(GL20.GL_BLEND);

            spriteBatch.begin();

            for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] {Light.class, TransformComponent.class})) {
                // set the color of your light (red,green,blue,alpha values)
                // spriteBatch.setColor(0.9f, 0.4f, 0f, 1f);

                spriteBatch.setColor(em.getComponent(entity, Light.class).color);

                // and render the sprite
                //spriteBatch.draw(lightSource, -1, 0);
                spriteBatch.draw(em.getComponent(entity, Light.class).source,
                        em.getComponent(entity, TransformComponent.class).x,
                        em.getComponent(entity,TransformComponent.class).y);

                spriteBatch.setColor(Color.WHITE);
            }


            spriteBatch.end();
        lightBuffer.end();


        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);

        spriteBatch.begin();
            spriteBatch.draw(lightBuffer.getColorBufferTexture(), 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.end();

    }

    private void renderUI(Batch spriteBatch, EntityManager em) {
        // post light-rendering
        // you might want to render your statusbar stuff here
        //TODO: Can render UI here.
    }
}