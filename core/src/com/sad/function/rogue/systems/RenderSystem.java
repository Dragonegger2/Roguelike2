package com.sad.function.rogue.systems;

/**
 * Renders things on the screen.
 *
 * In order to draw entities they have to have a sprite/animation and a position.
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.sad.function.rogue.components.MapComponent;
import com.sad.function.rogue.components.SpriteComponent;
import com.sad.function.rogue.components.TransformComponent;
import com.sad.function.rogue.objects.Dungeon;
import com.sad.function.rogue.objects.Tile;

import java.util.UUID;

/**
 * Rendering system like that mentioned here:
 * https://techblog.orangepixel.net/2015/07/shine-a-light-on-it/
 */
public class RenderSystem {

    private static FrameBuffer lightBuffer;
    private static TextureRegion lightBufferRegion;

    private static RenderSystem _instance;
    private static Color shadeColor = new Color(0.3f,0.3f,0.3f,1);
//    private static Color shadeColor = new Color(0.3f,0.38f,0.4f,1);

    private RenderSystem() {
        Gdx.gl.glClearColor(shadeColor.r, shadeColor.g, shadeColor.b,  shadeColor.a); //Default clear color I'm using. Can be overridden by calling before this.
    }

    public static RenderSystem getInstance() {
        if(_instance == null) {
            _instance = new RenderSystem();
        }
        return _instance;
    }

    public void render(Batch batch, EntityManager em) {
        Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(Color.WHITE);

        batch.begin();

        Dungeon dungeon = em.getComponent(em.getAllEntitiesPossessingComponent(MapComponent.class).iterator().next(), MapComponent.class).dungeon;
        Tile[][] map = em.getComponent(em.getAllEntitiesPossessingComponent(MapComponent.class).iterator().next(), MapComponent.class).dungeon.map;

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[x].length; y++) {

                boolean wall = map[x][y].blockSight;

                if(wall) {
                    batch.draw(dungeon.wallLit, x * 16, y * 16);
                }
                else {
                    batch.draw(dungeon.floorLit, x * 16, y * 16);
                }
                map[x][y].explored = true;
            }
        }
        //            Render all objects to the screen before applying lights.
        for(UUID entity : em.getAllEntitiesPossessingComponents(new Class[] {SpriteComponent.class, TransformComponent.class})) {

            batch.draw(em.getComponent(entity, SpriteComponent.class).sprite,
                    em.getComponent(entity, TransformComponent.class).x * 16,
                    em.getComponent(entity, TransformComponent.class).y * 16,
                    em.getComponent(entity, SpriteComponent.class).sprite.getWidth(),
                    em.getComponent(entity, SpriteComponent.class).sprite.getHeight());
        }

        batch.end();

    }

}