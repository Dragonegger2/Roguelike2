package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsTestScreen implements BaseScreen{
    private World world = new World(new Vector2(0,0),false );
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public PhysicsTestScreen() {

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
