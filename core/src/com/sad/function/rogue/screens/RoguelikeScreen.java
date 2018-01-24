package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.MoveEvent;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.dungeon.DungeonGenerator;
import com.sad.function.rogue.objects.Dungeon;
import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.EventQueue;
import com.sad.function.rogue.visibility.RayCastVisibility;

import java.util.UUID;

public class RoguelikeScreen implements BaseScreen{
    private Dungeon dungeon = new Dungeon();



    private DungeonGenerator dungeonGenerator;

    private final static int TORCH_RADIUS = 10;
    private RayCastVisibility fovCalculator;

    private boolean fovRecompute = true;

    private EntityManager entityManager;
    UUID playerUUID;

    public RoguelikeScreen() {

        entityManager = new EntityManager();
        playerUUID = entityManager.createEntity();

        entityManager.addComponent(playerUUID, new TransformComponent(0,0));
        entityManager.addComponent(playerUUID, new SpriteComponent(new Texture("player3.png")));

//        player = new GameEntity(new Texture("player3.png"), 0,0);

//        gameObjects.add(player);

        dungeonGenerator = new DungeonGenerator(dungeon, entityManager.getComponent(playerUUID, TransformComponent.class));
        dungeonGenerator.makeMap();

        fovCalculator = new RayCastVisibility();
    }

    public void processInput() {
        if( Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            EventQueue.getInstance().events.add(new MoveEvent(-1, 0, dungeon.map, player));
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            EventQueue.getInstance().events.add(new MoveEvent(1, 0, dungeon.map, player));
            fovRecompute = true;

        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            EventQueue.getInstance().events.add(new MoveEvent(0,1, dungeon.map, player));
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            EventQueue.getInstance().events.add(new MoveEvent(0, -1, dungeon.map, player));
            fovRecompute = true;

        }
//        else if ( Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
//            dungeonGenerator.makeMap();
//        }
    }

    public void update(float delta) {

        //Process event queue.
        while(!EventQueue.getInstance().events.isEmpty()) {
            EventQueue.getInstance().events.removeFirst().Execute();
        }

    }

    public void render(Batch batch) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(fovRecompute) {
            fovRecompute = false;
            fovCalculator.Compute(dungeon, player.x, player.y, TORCH_RADIUS);
        }

        batch.begin();

        for(int x = 0; x < dungeon.MAP_WIDTH; x++) {
            for(int y = 0; y < dungeon.MAP_HEIGHT; y++) {
                boolean visible = fovCalculator.isVisible(x, y);
                boolean wall = dungeon.map[x][y].blockSight;
                if(!visible) {
                    //Players can't see these things if they aren't explored.
                    if(dungeon.map[x][y].explored) {
                        //Outside of FOV
                        if (wall) {
                            //dark wall
                            batch.draw(dungeon.wallDark, x * 16, y * 16);
                        } else {
                            //dark ground
                            batch.draw(dungeon.floorDark, x * 16, y * 16);
                        }
                    }
                }
                else {
                    //Player can see it.
                    if (wall) {
                        batch.draw(dungeon.wallLit, x * 16, y * 16);
                    } else {
                        batch.draw(dungeon.floorLit, x * 16, y * 16);
                    }
                    dungeon.map[x][y].explored = true;
                }
            }
        }

        for (GameEntity entity : gameObjects) {
            entity.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
    }
}
