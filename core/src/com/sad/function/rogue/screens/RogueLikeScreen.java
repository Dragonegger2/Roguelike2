package com.sad.function.rogue.screens;

public class RogueLikeScreen {//} implements BaseScreen{
  /*  private RayCastVisibility fovCalculator;

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

    public RogueLikeScreen() {
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
        *//*
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
        *//*

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

        camera.position.set(
                entityManager.getComponent(playerUUID, TransformComponent.class).x * 16,
                entityManager.getComponent(playerUUID, TransformComponent.class).y * 16,
                0
        );

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(fovRecompute) {
            fovRecompute = false;
            Set<UUID> lightSources = entityManager.getAllEntitiesPossessingComponents(new Class[] {LightSourceComponent.class, TransformComponent.class});
            for (UUID source: lightSources) {
                fovCalculator.Compute(entityManager.getComponent(mapUUID, MapComponent.class).dungeon,
                        entityManager.getComponent(source, TransformComponent.class).x,
                        entityManager.getComponent(source, TransformComponent.class).y,
                        entityManager.getComponent(source, LightSourceComponent.class).lightLevel);
            }
        }

        batch.begin();

        for(int x = 0; x < entityManager.getComponent(mapUUID, MapComponent.class).dungeon.MAP_WIDTH; x++) {
            for(int y = 0; y < entityManager.getComponent(mapUUID, MapComponent.class).dungeon.MAP_HEIGHT; y++) {
                boolean visible = fovCalculator.isVisible(x, y);
                boolean wall = entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map[x][y].blockSight;
                if(!visible) {
                    //Players can't see these things if they aren't explored.
                    if(entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map[x][y].explored) {
                        //Outside of FOV
                        if (wall) {
                            //dark wall
                            batch.draw(entityManager.getComponent(mapUUID, MapComponent.class).dungeon.wallDark, x * 16, y * 16);
                        } else {
                            //dark ground
                            batch.draw(entityManager.getComponent(mapUUID, MapComponent.class).dungeon.floorDark, x * 16, y * 16);
                        }
                    }
                }
                else {
                    //Player can see it.
                    if (wall) {
                        batch.draw(entityManager.getComponent(mapUUID, MapComponent.class).dungeon.wallLit, x * 16, y * 16);
                    } else {
                        batch.draw(entityManager.getComponent(mapUUID, MapComponent.class).dungeon.floorLit, x * 16, y * 16);
                    }
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map[x][y].explored = true;
                }
            }
        }

//        Set<UUID> drawables = entityManager.getAllEntitiesPossessingComponents(new Class[]{TransformComponent.class, SpriteComponent.class});
//        for (UUID drawable: drawables
//             ) {
//            batch.draw(entityManager.getComponent(drawable, SpriteComponent.class).sprite,
//                    entityManager.getComponent(drawable, TransformComponent.class).x * 16,
//                    entityManager.getComponent(drawable, TransformComponent.class).y * 16);
//        }
        batch.draw(entityManager.getComponent(playerUUID, SpriteComponent.class).sprite,
                entityManager.getComponent(playerUUID, PhysicsComponent.class).body.getPosition().x - 8,
                entityManager.getComponent(playerUUID, PhysicsComponent.class).body.getPosition().y - 8 ) ;

        batch.end();
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
    }*/
}
