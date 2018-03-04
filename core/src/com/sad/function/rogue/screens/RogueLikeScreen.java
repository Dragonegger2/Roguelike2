package com.sad.function.rogue.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sad.function.rogue.FollowEntityCamera;
import com.sad.function.rogue.components.Light;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.objects.builder.PlayerBuilder;
import com.sad.function.rogue.systems.AssetManager;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.LightingSystem;
import com.sad.function.rogue.systems.RenderSystem;
import com.sad.function.rogue.systems.input.Action;
import com.sad.function.rogue.systems.input.GameContext;
import com.sad.function.rogue.systems.input.KeyBoardGameInput;

import java.util.UUID;

public class RogueLikeScreen implements ApplicationListener {

    //Entity Manager Stuff.
    private EntityManager entityManager;


    private FollowEntityCamera camera;

    private GameContext contextList = new GameContext();

    private UUID playerUUID;
    private UUID mapUUID;

    private Batch batch;

    public void processInput() {
        //Camera controls.
        if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            camera.zoom = camera.zoom / 2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom *= 2;
        }
    }

    /**
     * Sets up this current game context and it's actions.
     */
    private void setupActions() {
        contextList = new GameContext();

        contextList.registerActionToContext("LEFT",
            new Action(
              new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.LEFT),
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.A)
            )
        );

        contextList.registerActionToContext("RIGHT",
            new Action(
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.RIGHT),
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.D)
            )
        );

        contextList.registerActionToContext("UP",
            new Action(
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.UP),
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.W)
            )
        );

        contextList.registerActionToContext("DOWN",
            new Action(
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.DOWN),
                new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.S)
            )
        );
    }

    @Override
    public void create() {
        setupActions();

        entityManager = new EntityManager();
        mapUUID = entityManager.createEntity();

        //Create the player.
        playerUUID = PlayerBuilder.createPlayer(entityManager);

        //Create map object and then generate the dungeon.
        entityManager.addComponent(mapUUID, new MapComponent(entityManager));
        entityManager.getComponent(mapUUID, MapComponent.class).generateDungeon();

        camera = new FollowEntityCamera(80 * 4, 50 * 4, playerUUID, entityManager);

        entityManager.addComponent(playerUUID, new Light(10, Color.RED, "light3.png"));

        UUID lights = entityManager.createEntity();
        entityManager.addComponent(lights, new TransformComponent(0,0));
        entityManager.addComponent(lights, new Light(100, Color.WHITE, "light3.png"));

        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {
        LightingSystem.getInstance().resize(width, height);
    }

    @Override
    public void render() {
        //process input
        processInput();

        //updateGame

        //render

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Render all game entities.
        RenderSystem.getInstance().render(batch, entityManager);

        LightingSystem.getInstance().renderLighting(batch, entityManager, camera);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        AssetManager.getInstance().dispose();
    }
}
