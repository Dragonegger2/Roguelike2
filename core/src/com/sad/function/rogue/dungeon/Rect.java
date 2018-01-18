package com.sad.function.rogue.dungeon;

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
}
