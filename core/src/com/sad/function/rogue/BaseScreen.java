package com.sad.function.rogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.systems.RenderingSystem;

import java.util.UUID;

public class BaseScreen implements Disposable {

    EntityManager manager;
    AssetManager assetManager;
    Texture img;

    //Systems
    RenderingSystem renderingSystem;

    UUID player;

    public BaseScreen() {
        manager = new EntityManager();
        img = new Texture("badlogic.jpg");

        player = manager.createEntity();

        TransformComponent playerTransformComponent = new TransformComponent();
        SpriteComponent spriteComponent = new SpriteComponent();

        manager.addComponent(player, new TransformComponent());
        manager.addComponent(player, spriteComponent);

        renderingSystem = new RenderingSystem();
    }

    public void processInput() {
        System.out.println("Getting input...");
        //Query entityManager to get all entities that need input, add events to queue based on the input. Events get consumed in the update loop.
        //That's also where physics should be happening. (If there are physics.)
        if( Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //Throw an event! Woot
            manager.getComponent(player,TransformComponent.class).x -= 1;
        }
        else if( Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //Throw an event! Woot
            manager.getComponent(player,TransformComponent.class).x += 1;
        }
        else if( Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //Throw an event! Woot
            manager.getComponent(player,TransformComponent.class).y += 1;
        }
        else if( Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //Throw an event! Woot
            manager.getComponent(player,TransformComponent.class).y -= 1;

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
        img.dispose();
    }
}
