package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sad.function.rogue.components.Light;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.AssetManager;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.RenderAndLightingSystem;

import java.util.UUID;

public class GdxRenderAndLightingSystemTest extends ApplicationAdapter implements InputProcessor {
    private EntityManager em = new EntityManager();
    private SpriteBatch batch;

    Texture light;

    @Override
    public void create() {
        batch = new SpriteBatch();
        UUID badLogic = em.createEntity();
        em.addComponent(badLogic, new SpriteComponent("badlogic.jpg"));
        em.addComponent(badLogic, new TransformComponent(0,0));
//
//        UUID standAloneLightSource = em.createEntity();
//        em.addComponent(standAloneLightSource, new Light(10, new Color(0.9f,0.4f,0f,1), "bigOlLight.png"));
//        em.addComponent(standAloneLightSource, new TransformComponent(50,50));
        //If we want stand alone lights, we can just create a new uuid, and add a transform component and a light component to it.
        //If it follows something, just add a light component to an existing uuid.

        Gdx.input.setInputProcessor(this);

    }


    @Override
    public void render() {
        RenderAndLightingSystem.getInstance().render(batch, em);
    }

    @Override
    public void resize(int lowDisplayW, int lowDisplayH) {
        RenderAndLightingSystem.getInstance().resize(lowDisplayW, lowDisplayH);
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
        int width  = AssetManager.getInstance().get("bigOlLight3.png").getWidth();
        int height = AssetManager.getInstance().get("bigOlLight3.png").getHeight();

        UUID newLight = em.createEntity();
        em.addComponent(newLight, new Light(10, Color.WHITE, "bigOlLight3.png"));
        em.addComponent(newLight, new TransformComponent(screenX - width / 2, screenY - height / 2));

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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