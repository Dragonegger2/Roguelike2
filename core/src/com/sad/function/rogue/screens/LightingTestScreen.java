package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.sad.function.rogue.systems.AssetManager;

public class LightingTestScreen implements BaseScreen{
    FrameBuffer frameBuffer;

    Texture lightSpot;
    Texture badLogic;
    public LightingTestScreen() {
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        lightSpot = new Texture("light.png");
        badLogic  = new Texture("badlogic.jpg");

    }

    public void processInput() {

    }

    public void update(float delta) {
    }

    public void render(Batch batch) {
        frameBuffer.begin();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
            batch.draw(badLogic, 0, 0);
        batch.end();
        frameBuffer.end();

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
            batch.draw(lightSpot, 0,0);
        batch.end();

        batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);

        batch.begin();
            batch.draw(frameBuffer.getColorBufferTexture(), -1, 1, 2, -2);
        batch.end();
    }

    @Override
    public void dispose() {
        AssetManager.getInstance().dispose();
    }
}
