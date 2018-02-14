package com.sad.function.rogue.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sad.function.rogue.DungeonToPhysicsWorld;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.PhysicsComponent;
import com.sad.function.rogue.components.PlayerComponent;
import com.sad.function.rogue.objects.builder.PlayerBuilder;
import com.sad.function.rogue.systems.AssetManager;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.RenderingSystem;
import com.sad.function.rogue.systems.input.Action;
import com.sad.function.rogue.systems.input.KeyBoardGameInput;

import java.util.UUID;

public class RogueLikeScreen2 implements BaseScreen{
    private static final float BOX_TO_WORLD = 16;
    private static final float WORLD_TO_BOW = 1/16;

    private EntityManager entityManager;

    private UUID mapUUID;
    private UUID playerUUID;

    private Action moveLeft;
    private Action moveRight;
    private Action moveUp;
    private Action moveDown;

    //BOX2D Stuff
    //No gravity, hence why y is zero.
    private World world = new World(new Vector2(0, 0), false);
    private RayHandler rayHandler;

    private OrthographicCamera camera;
    private DungeonToPhysicsWorld dTPWorld;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private PointLight fuckingTorch;

    private RenderingSystem renderingSystem = new RenderingSystem();

    public RogueLikeScreen2() {
        setupActions();

        entityManager = new EntityManager();
        mapUUID = entityManager.createEntity();

        //Create the player.
        playerUUID = PlayerBuilder.createPlayer(entityManager);

        //Create map object and then generate the dungeon.
        entityManager.addComponent(mapUUID, new MapComponent(entityManager));
        entityManager.getComponent(mapUUID, MapComponent.class).generateDungeon();

        //Multiply the height  by aspect ratio.
        camera = new OrthographicCamera(80, 50);

        dTPWorld = new DungeonToPhysicsWorld(entityManager.getComponent(mapUUID, MapComponent.class), world);
        dTPWorld.GeneratePhysicsBodies(entityManager);

        rayHandler = new RayHandler(world);
        rayHandler.setShadows(true);
//        rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 1f);

        fuckingTorch = new PointLight(rayHandler, 32, Color.YELLOW, 10, 0,0 );
        fuckingTorch.attachToBody(entityManager.getComponent(playerUUID, PhysicsComponent.class).body);
        fuckingTorch.setXray(true);

        camera.zoom /= 4;

    }

    public void processInput() {
        float VELOCITY = 10f;
        Vector2 newVelocity = new Vector2(0,0);

        if(moveLeft.value() > 0 ){
            newVelocity.x = -VELOCITY;
        }
        else if( moveRight.value() > 0 ) {
            newVelocity.x = VELOCITY;
        }
        else {
            newVelocity.x = 0;
        }

        if( moveUp.value() > 0) {
            newVelocity.y = VELOCITY;
        }
        else if( moveDown.value() > 0) {
            newVelocity.y = -VELOCITY;
        }
        else {
           newVelocity.y = 0;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            camera.zoom = camera.zoom / 2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom *= 2;
        }

        entityManager.getComponent(playerUUID, PhysicsComponent.class).body.setLinearVelocity(newVelocity);
    }

    public void update(float delta) {
    }

    public void render(Batch batch) {
        //Step the physics simulation.
        world.step(1/60f, 6, 2);


        UUID playerUUID = entityManager.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next();

        //Follow player. Make sure you convert to world COORDINATES
        camera.position.set(
                entityManager.getComponent(playerUUID, PhysicsComponent.class).body.getPosition().x ,
                entityManager.getComponent(playerUUID, PhysicsComponent.class).body.getPosition().y ,
                0
        );

        //Update the project matrix and then set the batch project matrix.
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Clear buffer before rendering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render box2d world.

        batch.begin();
            renderingSystem.run(batch, entityManager);
        batch.end();

        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();

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
        world.dispose();
        rayHandler.dispose();
        AssetManager.getInstance().dispose();
    }
}
