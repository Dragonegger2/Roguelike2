package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.components.*;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.EventQueue;
import com.sad.function.rogue.visibility.RayCastVisibility;

import java.util.Set;
import java.util.UUID;

public class RogueLikeScreen implements BaseScreen{
    private RayCastVisibility fovCalculator;

    private boolean fovRecompute = true;

    private EntityManager entityManager;

    //NameOfAction, Matchign State?
//    Map<String, Action> inputMap;

    private UUID mapUUID;
    private UUID playerUUID;

    public RogueLikeScreen() {

        entityManager = new EntityManager();
        playerUUID = entityManager.createEntity();
        mapUUID = entityManager.createEntity();

        entityManager.addComponent(playerUUID, new TransformComponent(0,0));
        entityManager.addComponent(playerUUID, new SpriteComponent(new Texture("player3.png")));
        entityManager.addComponent(playerUUID, new MoverComponent(entityManager, playerUUID));
        entityManager.addComponent(playerUUID, new LightSourceComponent());
        entityManager.addComponent(playerUUID, new PlayerComponent());

        entityManager.addComponent(mapUUID, new MapComponent(entityManager));


        entityManager.getComponent(mapUUID, MapComponent.class).generateDungeon();

        fovCalculator = new RayCastVisibility();

    }

    public void processInput() {
        if( Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            entityManager.getComponent(playerUUID, MoverComponent.class).move(
                    -1,
                    0,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            entityManager.getComponent(playerUUID, MoverComponent.class).move(
                    1,
                    0,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            entityManager.getComponent(playerUUID, MoverComponent.class).move(
                    0,
                    1,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
        else if( Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            entityManager.getComponent(playerUUID, MoverComponent.class).move(
                    0,
                    -1,
                    entityManager.getComponent(mapUUID, MapComponent.class).dungeon.map);
            fovRecompute = true;
        }
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

        Set<UUID> drawables = entityManager.getAllEntitiesPossessingComponents(new Class[]{TransformComponent.class, SpriteComponent.class});
        for (UUID drawable: drawables
             ) {
            batch.draw(entityManager.getComponent(drawable, SpriteComponent.class).sprite,
                    entityManager.getComponent(drawable, TransformComponent.class).x * 16,
                    entityManager.getComponent(drawable, TransformComponent.class).y * 16);
        }
        batch.end();
    }

    @Override
    public void dispose() {
    }
}
