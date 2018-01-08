package com.sad.function.rogue;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.sad.function.rogue.components.Position;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class BaseScreen implements Disposable {

    EntityManager manager;
    AssetManager assetManager;
    Texture img;

    public BaseScreen() {
        manager = new EntityManager();
        img = new Texture("badlogic.jpg");

        UUID player = manager.createEntity();

        manager.addComponent(player, new Position(0, 0));
    }

    public void processInput() {
        //Query entityManager to get all entities that need input, add events to queue based on the input. Events get consumed in the update loop.
        //That's also where physics should be happening. (If there are physics.)

    }

    public void update(float delta) {
        //Physics processing. Should add a peek function to the message queue to check for collisions.
    }

    public void render(Batch batch) {
        batch.draw(img, 0,0);
    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
