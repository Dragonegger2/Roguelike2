package com.sad.function.rogue.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public class GameEntity implements Disposable {
    private int x;
    private int y;

    private Texture identifier;

    public GameEntity(Texture texture, int x, int y) {
        this.identifier = texture;
        this.x = x;
        this.y = y;
    }

    /**
     * Move the object.
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy, Tile[][] map) {
        //If you aren't blocked by a blocking thing.
        if(!map[x + dx][ y + dy].blocked){
            this.x += dx;
            this.y += dy;
        }
    }

    /**
     * Draw the object.
     * @param batch
     */
    public void draw(Batch batch) {
        batch.draw(identifier, x * 16, y * 16);
    }


    @Override
    public void dispose() {
        if(identifier != null) {
            identifier.dispose();
        }
    }
}
