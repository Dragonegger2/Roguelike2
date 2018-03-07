package com.sad.function.rogue.objects.builder;

import com.badlogic.gdx.graphics.Texture;
import com.sad.function.rogue.components.*;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class PlayerBuilder {

    public static UUID createPlayer(EntityManager entityManager) {
        UUID playerUUID = entityManager.createEntity();

        entityManager.addComponent(playerUUID, new TransformComponent(0,0));
        entityManager.addComponent(playerUUID, new SpriteComponent(new Texture("goblin.png")));
        entityManager.addComponent(playerUUID, new PlayerComponent());
        return playerUUID;
    }

}
