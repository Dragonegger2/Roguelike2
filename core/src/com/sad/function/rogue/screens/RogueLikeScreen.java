package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.FollowEntityCamera;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.objects.builder.PlayerBuilder;
import com.sad.function.rogue.systems.AssetManager;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.RenderingSystem;
import com.sad.function.rogue.systems.input.Action;
import com.sad.function.rogue.systems.input.GameContext;
import com.sad.function.rogue.systems.input.KeyBoardGameInput;

import java.util.UUID;

public class RogueLikeScreen implements BaseScreen{
    private static final float BOX_TO_WORLD = 16;
    private static final float WORLD_TO_BOX = 1/16;

    //Entity Manager Stuff.
    private EntityManager entityManager;


    private FollowEntityCamera camera;
//    private PerspectiveCamera uiCamera;

    private GameContext contextList = new GameContext();

    UUID playerUUID;
    UUID mapUUID;

    public RogueLikeScreen() {
        setupActions();

        entityManager = new EntityManager();
        mapUUID = entityManager.createEntity();

        //Create the player.
        playerUUID = PlayerBuilder.createPlayer(entityManager);

        //Create map object and then generate the dungeon.
        entityManager.addComponent(mapUUID, new MapComponent(entityManager));
        entityManager.getComponent(mapUUID, MapComponent.class).generateDungeon();

        camera = new FollowEntityCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), playerUUID, entityManager);
        camera.zoom /= 4;
    }

    public void processInput() {

        //Camera controls.
        if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            camera.zoom = camera.zoom / 2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom *= 2;
        }
    }

    public void update(float delta) {
    }

    public void render(Batch batch) {
        //Step the physics simulation.

        //Update the project matrix and then set the batch project matrix.
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Clear buffer before rendering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render box2d world.
        batch.begin();
            RenderingSystem.run(batch, entityManager);
        batch.end();

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
    public void dispose() {
        AssetManager.getInstance().dispose();
    }
}
