package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.ICommand;
import com.sad.function.rogue.MoveCommand;
import com.sad.function.rogue.dungeon.DungeonGenerator;
import com.sad.function.rogue.objects.Dungeon;
import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.visibility.RayCastVisibility;
import com.sad.function.rogue.visibility.Visibility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class GameScreen implements BaseScreen{
    private Dungeon dungeon = new Dungeon();

    private List<GameEntity> gameObjects = new ArrayList<>();
    private GameEntity player;

    private LinkedList<ICommand> eventQueue = new LinkedList<>();

    private DungeonGenerator dungeonGenerator;

    private final static int TORCH_RADIUS = 10;
    private Visibility fovCalculator;

    private boolean fovRecompute = true;

    private EntityManager entityManager;
    UUID playerUUID;

    public GameScreen() {

        entityManager = new EntityManager();
        playerUUID = entityManager.createEntity();

        player = new GameEntity(new Texture("player3.png"), 0,0);
        gameObjects.add(player);

        dungeonGenerator = new DungeonGenerator(dungeon, player);
        dungeonGenerator.makeMap();

        fovCalculator = new RayCastVisibility(dungeon);
    }

    public void processInput() {
        if( Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            eventQueue.add(new MoveCommand(-1, 0, dungeon.map, player));
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            eventQueue.add(new MoveCommand(1, 0, dungeon.map, player));
            fovRecompute = true;

        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            eventQueue.add(new MoveCommand(0,1, dungeon.map, player));
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            eventQueue.add(new MoveCommand(0, -1, dungeon.map, player));
            fovRecompute = true;

        }
//        else if ( Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
//            dungeonGenerator.makeMap();
//        }
    }

    public void update(float delta) {

        //Process event queue.
        while(!eventQueue.isEmpty()) {
            eventQueue.removeFirst().Execute();
        }

    }

    public void render(Batch batch) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(fovRecompute) {
            fovRecompute = false;
            fovCalculator.Compute(player.x, player.y, TORCH_RADIUS);
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
