package com.sad.function.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.RenderingSystem;

import java.util.UUID;

public class GameScreen implements BaseScreen{

    EntityManager manager;
    AssetManager assetManager;

    //Systems
    RenderingSystem renderingSystem;

    UUID player;

    private UUID[][] map;

    public GameScreen() {
        manager = new EntityManager();

        player = manager.createEntity();

        manager.addComponent(player, new TransformComponent());
        manager.addComponent(player, new SpriteComponent());

        renderingSystem = new RenderingSystem();
    }

    public void processInput() {
        System.out.println("Getting input...");
        //Query entityManager to get all entities that need input, add events to queue based on the input. Events get consumed in the update loop.
        //That's also where physics should be happening. (If there are physics.)
        if( Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            manager.getComponent(player,TransformComponent.class).x -= 16;
        }
        else if( Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            manager.getComponent(player,TransformComponent.class).x += 16;
        }
        else if( Gdx.input.isKeyPressed(Input.Keys.UP)) {
            manager.getComponent(player,TransformComponent.class).y += 16;
        }
        else if( Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            manager.getComponent(player,TransformComponent.class).y -= 16;
        }
    }

    public void update(float delta) {
        //Physics processing. Should add a peek function to the message queue to check for collisions.
        renderingSystem.run(delta, manager);
    }

    public void render(Batch batch) {
//        batch.draw(img, 0,0);
        renderingSystem.run(Gdx.graphics.getDeltaTime(), manager);
    }

    @Override
    public void dispose() {
    }
}
