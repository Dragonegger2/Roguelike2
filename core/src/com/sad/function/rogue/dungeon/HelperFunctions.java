package com.sad.function.rogue.dungeon;

public class HelperFunctions {
    /**
     * Returns the distance from the origin (0,0);
     * @param point
     * @return
     */
    public static int getDistance(LevelPoint point) {
        return (int)Math.hypot(point.x, point.y);
    }
    public static int getDistance(LevelPoint pointA, LevelPoint pointB) {
        //return Math.sqrt(Math.pow((double)pointA.x - (double)pointB.x, 2) +Math.pow((double)pointA.x - (double)pointB.x, 2));
        return (int)Math.hypot(pointA.x - pointB.x, pointA.y - pointB.y);

    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.hypot(x1 - x2, y1 - y2);
    }
}
