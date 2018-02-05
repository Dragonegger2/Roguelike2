package com.sad.function.rogue.objects.builder;

import com.sad.function.rogue.components.BlocksComponent;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MonsterBuilder {
    private int x;
    private int y;
    private boolean blocks;
    private String textureLocation;

    private EntityManager entityManager;

    public MonsterBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MonsterBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public MonsterBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public MonsterBuilder setBlocks(boolean blocks) {
        this.blocks = blocks;
        return this;
    }

    public MonsterBuilder setTextureLocation(String textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    public UUID createMonster() {
        UUID monsterUUID = entityManager.createEntity();
        entityManager.addComponent(monsterUUID, new TransformComponent(x, y));
        entityManager.addComponent(monsterUUID, new SpriteComponent(textureLocation));
        if(blocks)
            entityManager.addComponent(monsterUUID, new BlocksComponent());

        return UUID.randomUUID();
    }
}
