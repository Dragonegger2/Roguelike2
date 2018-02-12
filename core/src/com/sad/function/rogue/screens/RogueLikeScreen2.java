package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sad.function.rogue.DungeonToPhysicsWorld;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.MoverComponent2;
import com.sad.function.rogue.components.PlayerComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.objects.builder.PlayerBuilder;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.EventQueue;
import com.sad.function.rogue.systems.input.Action;
import com.sad.function.rogue.systems.input.KeyBoardGameInput;
import com.sad.function.rogue.visibility.RayCastVisibility;

import java.util.UUID;

public class RogueLikeScreen2 implements BaseScreen{
    private RayCastVisibility fovCalculator;

    private boolean fovRecompute = true;

    private EntityManager entityManager;

    //NameOfAction, Matching State?

    //Map<String, KeyState> inputMap;
    //Context has Actions tied to Inputs
        //Actions are the events that I dispatch to the event queue.

    private UUID mapUUID;
    private UUID playerUUID;

    private Action moveLeft;
    private Action moveRight;
    private Action moveUp;
    private Action moveDown;

    //BOX2D Stuff
    //No gravity, hence why y is zero.
    private World world = new World(new Vector2(0, 0), false);

    private OrthographicCamera camera;
    private DungeonToPhysicsWorld dTPWorld;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public RogueLikeScreen2() {
        setupActions();

        entityManager = new EntityManager();
        mapUUID = entityManager.createEntity();

        //Create the player.
        playerUUID = PlayerBuilder.createPlayer(entityManager);

        //Create map object and then generate the dungeon.
        entityManager.addComponent(mapUUID, new MapComponent(entityManager));
        entityManager.getComponent(mapUUID, MapComponent.class).generateDungeon();

        //TODO: I should probably rewrite this so that it is just a static function.
        fovCalculator = new RayCastVisibility();

        //Multiply the height  by aspect ratio.
        camera = new OrthographicCamera(16 * 80, 50 * 16);

        dTPWorld = new DungeonToPhysicsWorld(entityManager.getComponent(mapUUID, MapComponent.class), world);
        dTPWorld.GeneratePhysicsBodies(entityManager);

    }

    public void processInput() {
        /*
        if(moveLeft.value() > 0 ){
            entityManager.getComponent(playerUUID, MoverComponent2.class).move(
                    -1,
                    0,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        else if( moveRight.value() > 0 ) {
            entityManager.getComponent(playerUUID, MoverComponent2.class).move(
                    10,
                    0,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        else if( moveUp.value() > 0) {
            entityManager.getComponent(playerUUID, MoverComponent2.class).move(
                    0,
                    10,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        else if( moveDown.value() > 0) {
            entityManager.getComponent(playerUUID, MoverComponent2.class).move(
                    0,
                    -10,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        */

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            //Move left
            entityManager.getComponent(playerUUID, MoverComponent2.class).move(
                    -10f,
                    0f);

        }
    }

    public void update(float delta) {
        //Process event queue.
        while(!EventQueue.getInstance().events.isEmpty()) {
            EventQueue.getInstance().events.removeFirst().Execute();
        }
    }

    public void render(Batch batch) {
        UUID playerUUID = entityManager.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next();

        //Follow player.
        camera.position.set(
                entityManager.getComponent(playerUUID, TransformComponent.class).x * 16,
                entityManager.getComponent(playerUUID, TransformComponent.class).y * 16,
                0
        );

        //Update the project matrix and then set the batch project matrix.
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Clear buffer before rendering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        debugRenderer.render(world, camera.combined);

        world.step(1/60f, 6, 2);
    }

    private void setupActions() {

        moveLeft = new Action();
        moveLeft.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.LEFT));
        moveLeft.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.A));

        moveRight = new Action();
        moveRight.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.RIGHT));
        moveRight.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.D));

        moveDown = new Action();
        moveDown.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.DOWN));
        moveDown.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.S));

        moveUp = new Action();
        moveUp.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.UP));
        moveUp.registerInput(new KeyBoardGameInput(KeyBoardGameInput.STATE.IS_KEY_PRESSED, Input.Keys.W));

    }
    @Override
    public void dispose() {
    }
}
