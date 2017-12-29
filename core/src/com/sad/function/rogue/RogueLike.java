package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RogueLike extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;

	Screen gameScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gameScreen = new Screen();
	}

	@Override
	public void render () {
        float delta = Gdx.graphics.getDeltaTime();

        gameScreen.processInput();

        gameScreen.update(delta);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gameScreen.render(batch);
        batch.end();


    }

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
