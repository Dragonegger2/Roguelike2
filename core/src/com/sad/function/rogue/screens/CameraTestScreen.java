package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public class CameraTestScreen implements BaseScreen{

    OrthographicCamera camera;

    public CameraTestScreen() {
        camera = new OrthographicCamera();
    }

    public void processInput() {

    }

    public void update(float delta) {
    }

    public void render(Batch batch) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();


        batch.end();
    }

    @Override
    public void dispose() {
    }
}
