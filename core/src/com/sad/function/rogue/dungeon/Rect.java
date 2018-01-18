package com.sad.function.rogue.dungeon;

import com.badlogic.gdx.math.Vector2;

public class Rect {
    public int x1;
    public int y1;

    public int x2;
    public int y2;

    public Rect(int x, int y, int w, int h) {
        this.x1 = x;
        this.y1 = y;
        this.x2 = x+w;
        this.y2 = y+h;
    }

    /**
     * Returns the center of the room/space.
     * @return
     */
    public Vector2 center() {
        int centerX = (x1 + x2) / 2;
        int centerY = (y1 + y2) / 2;

        return new Vector2(centerX, centerY);
    }

    /**
     * Returns whether another rectangle/room intersects with this one.
     * @param other Rect to check intersections with.
     * @return Whether they are intersecting.
     */
    public boolean intersect(Rect other) {
        return x1 <= other.x2 && x2 >= other.x1 &&
               y1 <= other.y2 && y2 >= other.y1;

    }
}
