package com.sad.function.rogue.dungeon;

public class HelperFunctions {
    public static double getDistance(LevelPoint pointA, LevelPoint pointB) {
        return Math.sqrt(Math.pow((double)pointA.x - (double)pointB.x, 2) +Math.pow((double)pointA.x - (double)pointB.x, 2));
    }

}
