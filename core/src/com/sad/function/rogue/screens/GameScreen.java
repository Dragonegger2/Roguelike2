package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.ICommand;
import com.sad.function.rogue.MoveCommand;
import com.sad.function.rogue.dungeon.DungeonGenerator;
import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.objects.Map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameScreen implements BaseScreen{
    private Map map = new Map();

    private List<GameEntity> gameObjects = new ArrayList<GameEntity>();
    private GameEntity player;

    private LinkedList<ICommand> eventQueue = new LinkedList<ICommand>();

    private DungeonGenerator dungeonGenerator;

    public GameScreen() {
        player = new GameEntity(new Texture("player3.png"), 0,0);
        gameObjects.add(player);

        dungeonGenerator = new DungeonGenerator(map, player);
        dungeonGenerator.make_map();

    }

    public void processInput() {
        if( Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
//            player.move(-1, 0, map.map);
            eventQueue.add(new MoveCommand(-1, 0, map.map, player));
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
//            player.move(1, 0, map.map);
            eventQueue.add(new MoveCommand(1, 0, map.map, player));
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//            player.move(0, 1, map.map);
            eventQueue.add(new MoveCommand(0,1, map.map, player));
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
//            player.move(0, -1, map.map);
            eventQueue.add(new MoveCommand(0, -1, map.map, player));
        }
        else if ( Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            dungeonGenerator.make_map();
        }
    }

    public void update(float delta) {
        //Process event queue.
        while(!eventQueue.isEmpty()) {
            eventQueue.removeFirst().Execute();
        }
    }

    public void render(Batch batch) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for(int x = 0; x < map.MAP_WIDTH; x++) {
            for(int y = 0; y < map.MAP_HEIGHT; y++) {
                boolean wall = map.map[x][y].blockSight;
                if(wall) {
                    batch.draw(map.wall, x * 16, y * 16);
                }
                else {
                    batch.draw(map.floor, x*16, y*16);
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
