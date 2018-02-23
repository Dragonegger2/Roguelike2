package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GdxTest extends ApplicationAdapter implements InputProcessor {

    FrameBuffer lightBuffer;
    TextureRegion lightBufferRegion;

    SpriteBatch spriteBatch;
    OrthographicCamera camera;

    Vector3 vector3;

    Texture lightSource, badLogic;
    Sprite sprite;
    Vector2 position;

    @Override
    public void create() {
        position = new Vector2(0,0);
        vector3=new Vector3();
        camera=new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch=new SpriteBatch();

        lightSource =new Texture("bigOlLight.png");

        badLogic =new Texture("badlogic.jpg");

        Gdx.input.setInputProcessor(this);
    }
    Color baseColor = new Color(1.0f, 1.0f, 1.0f, 0.99607843f);

    @Override
    public void render() {
        // set the ambient color values, this is the "global" light of your scene
        // imagine it being the sun.  Usually the alpha value is just 1, and you change the darkness/brightness with the Red, Green and Blue values for best effect
        Gdx.gl.glClearColor(0.3f,0.38f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setColor(baseColor);
        spriteBatch.begin();
            spriteBatch.draw(badLogic, 0,0);
        spriteBatch.end();


        lightBuffer.begin();
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
                spriteBatch.draw(lightSource, position.x, position.y);
            spriteBatch.end();
        lightBuffer.end();

        // now we render the lightBuffer to the default "frame buffer"
        // with the right blending !

        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        spriteBatch.begin();
        spriteBatch.draw(lightBuffer.getColorBufferTexture(), 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.end();
        // post light-rendering
        // you might want to render your statusbar stuff here
    }

    @Override
    public void resize(int lowDisplayW, int lowDisplayH) {
        // Fakedlight system (alpha blending)

        // if lightBuffer was created before, dispose, we recreate a new one
        if (lightBuffer!=null) lightBuffer.dispose();
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, lowDisplayW, lowDisplayH, false);

        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,lightBuffer.getHeight()-lowDisplayH,lowDisplayW,lowDisplayH);

        lightBufferRegion.flip(false, false);

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        vector3.set(screenX,screenY,0);
//        camera.unproject(vector3);
//        sprite.setPosition(vector3.x-sprite.getWidth()/2,vector3.y-sprite.getHeight()/2);
        position.x = screenX;
        position.y = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}