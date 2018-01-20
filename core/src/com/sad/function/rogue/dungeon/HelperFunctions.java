package com.sad.function.rogue.dungeon;

public class HelperFunctions {
    public static int getDistance(LevelPoint pointA, LevelPoint pointB) {
        //return Math.sqrt(Math.pow((double)pointA.x - (double)pointB.x, 2) +Math.pow((double)pointA.x - (double)pointB.x, 2));
        return (int)Math.hypot(pointA.x - pointB.x, pointA.y - pointB.y);

    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.hypot(x1 - x2, y1 - y2);
    }
}
