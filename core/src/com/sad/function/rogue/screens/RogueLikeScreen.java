package com.sad.function.rogue.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sad.function.rogue.DungeonToPhysicsWorld;
import com.sad.function.rogue.FollowEntityCamera;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.MoverComponent;
import com.sad.function.rogue.components.PhysicsComponent;
import com.sad.function.rogue.components.PlayerComponent;
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

    //BOX2D Stuff
    private World world = new World(new Vector2(0, 0), false);

    private RayHandler rayHandler;

    //World Camera
    private FollowEntityCamera camera;
    private FollowEntityCamera uiCamera;

    //TODO: Create another camera for UI rendering.

    //TODO: Turn this into a generator/emitter.
    private DungeonToPhysicsWorld dTPWorld;

    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private GameContext contextList = new GameContext();
    private Texture t = new Texture("badlogic.jpg");

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

        //Multiply the height  by aspect ratio.

        dTPWorld = new DungeonToPhysicsWorld(entityManager.getComponent(mapUUID, MapComponent.class), world);
        dTPWorld.GeneratePhysicsBodies(entityManager);

//        rayHandler = new RayHandler(world);
//        rayHandler.setShadows(true);

//        rayHandler.setAmbientLight(Color.DARK_GRAY);
        //TODO: Add a tweening function that will slowly flicker the lights be modifying the distances.
//        new PointLight(rayHandler, 32, Color.YELLOW, 10*16, 0,0 ).attachToBody(entityManager.getComponent(playerUUID, PhysicsComponent.class).body);
//        new PointLight(rayHandler, 16, Color.RED, 7*16, 0,0).attachToBody(entityManager.getComponent(playerUUID, PhysicsComponent.class).body);

        camera = new FollowEntityCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), playerUUID, entityManager);
        camera.zoom /= 4;

        uiCamera = new FollowEntityCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), playerUUID, entityManager);
        uiCamera.zoom /= 4;

    }

    public void processInput() {

        float VELOCITY = 10f;

        Vector2 newVelocity = new Vector2(0,0);

        if(contextList.value("LEFT") > 0 ){
            newVelocity.x = -VELOCITY;
        }
        else if( contextList.value("RIGHT") > 0 ) {
            newVelocity.x = VELOCITY;
        }
        else {
            newVelocity.x = 0;
        }

        if( contextList.value("UP")  > 0) {
            newVelocity.y = VELOCITY;
        }
        else if( contextList.value("DOWN")  > 0) {
            newVelocity.y = -VELOCITY;
        }
        else {
            newVelocity.y = 0;
        }

        //Camera controls.
        if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            camera.zoom = camera.zoom / 2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom *= 2;
        }

        entityManager.getComponent(entityManager.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next(), MoverComponent.class).moveOrAttack(newVelocity.x, newVelocity.y);
    }

    public void update(float delta) {
    }

    public void render(Batch batch) {
        //Step the physics simulation.
        world.step(1/60f, 6, 2);

        //Update the project matrix and then set the batch project matrix.
        camera.update();

        //Clear buffer before rendering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render box2d world.
        batch.begin();
            batch.setProjectionMatrix(camera.combined);
            RenderingSystem.run(batch, entityManager);
        batch.end();

        batch.begin();
            batch.setProjectionMatrix(uiCamera.combined);
            batch.draw(t, 0,0);
        batch.end();

//        rayHandler.setCombinedMatrix(camera);
//        rayHandler.updateAndRender();
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
        world.dispose();
        rayHandler.dispose();
        AssetManager.getInstance().dispose();
    }
}
