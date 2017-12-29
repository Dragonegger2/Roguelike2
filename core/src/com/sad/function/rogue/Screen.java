package com.sad.function.rogue;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.sad.function.rogue.systems.EntityManager;

public class Screen {

    EntityManager manager;

    public Screen() {
        manager = new EntityManager();
    }
    public void processInput() {
        //Query entityManager to get all entities that need input, add events to queue based on the input. Events get consumed in the update loop.
        //That's also where physics should be happening.
    }

    public void update(float delta) {
        //Physics processing. Should add a peek function to the message queue to check for collisions.
    }

    public void render(Batch batch) {

    }
}
