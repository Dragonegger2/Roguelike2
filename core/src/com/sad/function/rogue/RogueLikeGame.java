package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sad.function.rogue.screens.BaseScreen;
import com.sad.function.rogue.screens.RogueLikeScreen2;

public class RogueLikeGame extends ApplicationAdapter {

	SpriteBatch batch;

	BaseScreen gameScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();

		gameScreen = new RogueLikeScreen2();
    }

	@Override
	public void render () {
        float delta = Gdx.graphics.getDeltaTime();

        gameScreen.processInput();

        gameScreen.update(delta);

        gameScreen.render(batch);
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
