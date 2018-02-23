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

/**
 * Rendering system like that mentioned here:
 * https://techblog.orangepixel.net/2015/07/shine-a-light-on-it/
 */
public class RenderAndLightingSystem {

    private static FrameBuffer lightBuffer;
    private static TextureRegion lightBufferRegion;

    private static RenderAndLightingSystem _instance;
    private static Color baseColor = new Color(1.0f, 1.0f, 1.0f, 0.99607843f);

    private RenderAndLightingSystem() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

        renderUI(spriteBatch, em);
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
     * @param spriteBatch
     * @param em
     */
    private void renderLightedComponents(Batch spriteBatch, EntityManager em) {
        // set the ambient color values, this is the "global" light of your scene
        // imagine it being the sun.  Usually the alpha value is just 1, and you change the darkness/brightness with the Red, Green and Blue values for best effect
        Gdx.gl.glClearColor(0.3f,0.38f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setColor(baseColor);
        spriteBatch.begin();
//            spriteBatch.draw(badLogic, 0,0);
//            Render all objects to the screen before applying lights.
        spriteBatch.end();

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

            //If we don't clear we get all kinds of artifacts.
            Gdx.gl.glClearColor(0.3f,0.38f,0.4f,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // setup the right blending
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            Gdx.gl.glEnable(GL20.GL_BLEND);

            // start rendering the lights to our spriteBatch
            spriteBatch.begin();

            // set the color of your light (red,green,blue,alpha values)
            spriteBatch.setColor(0.9f, 0.4f, 0f, 1f);

            // and render the sprite
            //spriteBatch.draw(lightSource, -1, 0);
            //TODO: Render lights sources. Will need a position and other stuff here.
            spriteBatch.end();
        lightBuffer.end();

        // now we render the lightBuffer to the default "frame buffer"
        // with the right blending !

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