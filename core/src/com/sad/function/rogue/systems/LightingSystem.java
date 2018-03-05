package com.sad.function.rogue.systems;

/**
 * Renders things on the screen.
 *
 * In order to draw entities they have to have a sprite/animation and a position.
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.sad.function.rogue.Globals;
import com.sad.function.rogue.components.Light;
import com.sad.function.rogue.components.TransformComponent;

import java.util.UUID;

/**
 * Rendering system like that mentioned here:
 * https://techblog.orangepixel.net/2015/07/shine-a-light-on-it/
 */
public class LightingSystem {

    private static FrameBuffer frameBuffer;

    private static LightingSystem _instance;
    private static Color shadeColor = new Color(0.3f,0.3f,0.3f,.9f);


    private LightingSystem(int width, int height) {
        resize(width, height);
    }

    /**
     * Call in the ApplicationListener resize method to ensure that we properly resize the "fog of war"
     *
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        if(frameBuffer !=null && (frameBuffer.getWidth()!=width || frameBuffer.getHeight()!=height )) {
            frameBuffer.dispose();
            frameBuffer=null;
        }

        if(frameBuffer==null){
            try {
                frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
            }catch (GdxRuntimeException e){
                frameBuffer=new FrameBuffer(Pixmap.Format.RGB565,(int) Math.pow(width, 2), (int) Math.pow(height, 2),false);
            }
        }

    }

    public static LightingSystem getInstance() {
        if(_instance == null) {
            _instance = new LightingSystem(Globals.screenWidth, Globals.screenHeight);
        }
        return _instance;
    }

    public void renderLighting(Batch batch, EntityManager em, Camera camera) {

        frameBuffer.begin();
            Gdx.gl.glClearColor(shadeColor.r, shadeColor.g, shadeColor.b, shadeColor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // setup the right blending

            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.begin();
                for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] {Light.class, TransformComponent.class})) {
                    // set the color of your light (red,green,blue,alpha values)

                    batch.setColor(em.getComponent(entity, Light.class).color);

                    //TODO: Current lighting bugs exist here:
                    batch.draw(em.getComponent(entity, Light.class).source,
                            em.getComponent(entity, TransformComponent.class).x * 16 - em.getComponent(entity, Light.class).source.getWidth() / 2,
                            em.getComponent(entity,TransformComponent.class).y * 16 - em.getComponent(entity, Light.class).source.getHeight() / 2);
                    batch.setColor(Color.WHITE); //White is the default color. If you don't, we'll get bleeding issues.
                }

            batch.end();
        frameBuffer.end();


        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);

        /**
         * The important thing to remember here about the frameBuffer is that the lights are drawn at their respective
         * camera coordinates and are visible with the fb between them and the sprites below.
         */
        batch.begin();

            batch.draw(frameBuffer.getColorBufferTexture(),
                    camera.position.x - frameBuffer.getColorBufferTexture().getWidth()/2,
                    camera.position.y - frameBuffer.getColorBufferTexture().getHeight()/2
            );

        batch.end();
    }
}