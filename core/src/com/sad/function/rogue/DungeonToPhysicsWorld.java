package com.sad.function.rogue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sad.function.rogue.components.*;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class DungeonToPhysicsWorld {
    private World world;
    private MapComponent map;

    private float WORLD_TO_BOX = 1/16f;
    private SpriteComponent defaultDungeonWall;

    public DungeonToPhysicsWorld(MapComponent map, World world) {
        this.map = map;
        this.world = world;
    }

    //WORLD 16px - PHYSICS 1px
    public void GeneratePhysicsBodies(EntityManager entityManager) {

        PolygonShape polyBox = new PolygonShape();
        defaultDungeonWall = new SpriteComponent("DungeonWallLight.png");

        for(int x = 0; x < map.dungeon.MAP_WIDTH; x++ ) {
            for(int y = 0; y < map.dungeon.MAP_HEIGHT; y++ ) {
                BodyDef groundBodyDef = new BodyDef();
                groundBodyDef.position.set(new Vector2((x * 16 + 8) * WORLD_TO_BOX  , (y * 16 + 8) * WORLD_TO_BOX));

                Body wallBody = world.createBody(groundBodyDef);

                if(map.dungeon.map[x][y].blockSight) {
                    //TODO Add a flag to check if it's blocked because of an entity. Entities are something else entirely.

                    //Create a solid physics body.
                    polyBox.setAsBox(8 * WORLD_TO_BOX, 8 * WORLD_TO_BOX);
                    wallBody.createFixture(polyBox, 0.0f);

                    UUID wallUUID = entityManager.createEntity();
                    entityManager.addComponent(wallUUID, new PhysicsComponent(wallBody));
                    entityManager.addComponent(wallUUID, defaultDungeonWall);
                }
            }
        }


        CreatePlayer(entityManager);

        polyBox.dispose();
    }

    private void CreatePlayer(EntityManager entityManager) {
        //Create Player and other entities boxes.
        UUID player = entityManager.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next();//Get just the first one (in our game there's only one player.)

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; //Prevent my dynamic bodies from rotating.

        bodyDef.position.set(
                (entityManager.getComponent(player, TransformComponent.class).x * 16 + 8) * WORLD_TO_BOX ,
                (entityManager.getComponent(player, TransformComponent.class).y * 16 + 8) * WORLD_TO_BOX
        );
        Body playerBody = world.createBody(bodyDef);

        CircleShape playerCircle = new CircleShape();
        playerCircle.setRadius(6.5f * WORLD_TO_BOX);
//        playerBody.createFixture(polyBox, 0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerCircle;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.0f;

        Fixture fixture = playerBody.createFixture(fixtureDef);

        entityManager.addComponent(player, new PhysicsComponent(playerBody));
    }
}
