package com.sad.function.rogue;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class FollowEntityCamera extends OrthographicCamera {

    private UUID followTarget;
    private EntityManager entityManager;

    public FollowEntityCamera(float viewportWidth, float viewportHeight, UUID followTarget, EntityManager entityManager) {
        super(viewportWidth, viewportHeight);

        this.followTarget = followTarget;
        this.entityManager = entityManager;
    }

    @Override
    public void update() {
        //Handle the initialization phase.
        try {
            //Point to entity:
            this.position.set(
                    entityManager.getComponent(followTarget, TransformComponent.class).x * 16,
                    entityManager.getComponent(followTarget, TransformComponent.class).y * 16,
                    0
            );
        }
        catch (NullPointerException e) {
            System.out.println("No entity to point at.");
        }

        super.update();
    }
}
