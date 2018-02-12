package com.sad.function.rogue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.PhysicsComponent;
import com.sad.function.rogue.components.PlayerComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class DungeonToPhysicsWorld {
    private World world;
    private MapComponent map;

    public DungeonToPhysicsWorld(MapComponent map, World world) {
        this.map = map;
        this.world = world;
    }

    //WORLD 16px - PHYSICS 1px
    public void GeneratePhysicsBodies(EntityManager entityManager) {

        PolygonShape polyBox = new PolygonShape();

        for(int x = 0; x < map.dungeon.MAP_WIDTH; x++ ) {
            for(int y = 0; y < map.dungeon.MAP_HEIGHT; y++ ) {
                BodyDef groundBodyDef = new BodyDef();
                groundBodyDef.position.set(new Vector2(x * 16 + 8, y * 16 + 8));

                Body wallBody = world.createBody(groundBodyDef);

                if(map.dungeon.map[x][y].blockSight) {
                    //TODO Add a flag to check if it's blocked because of an entity. Entities are something else entirely.

                    //Create a solid physics body.
                    polyBox.setAsBox(8, 8);
                    wallBody.createFixture(polyBox, 0.0f);
                }
            }
        }

        //Create Player and other entities boxes.
        UUID player = entityManager.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next();//Get just the first one (in our game there's only one player.)

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                entityManager.getComponent(player, TransformComponent.class).x * 16 + 8,
                entityManager.getComponent(player, TransformComponent.class).y * 16 + 8
        );

        Body playerBody = world.createBody(bodyDef);
        polyBox.setAsBox(8, 8);
        playerBody.createFixture(polyBox, 0.5f);

        entityManager.addComponent(player, new PhysicsComponent(playerBody));
        polyBox.dispose();
    }
}
