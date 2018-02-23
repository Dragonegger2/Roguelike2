package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.RenderAndLightingSystem;

public class GdxRenderAndLightingSystemTest extends ApplicationAdapter {
    private EntityManager em = new EntityManager();
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
    }


    @Override
    public void render() {
        RenderAndLightingSystem.getInstance().render(batch, em);
    }

    @Override
    public void resize(int lowDisplayW, int lowDisplayH) {

    }

}