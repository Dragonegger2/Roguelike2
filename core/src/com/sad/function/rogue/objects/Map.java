package com.sad.function.rogue.objects;

import com.badlogic.gdx.graphics.Texture;

public class Map {
    public int MAP_WIDTH = 80;
    public int MAP_HEIGHT = 50;

    public Tile[][] map;

    public Texture floor = new Texture("DungeonFloor.png");
    public Texture wall = new Texture("DungeonWall.png");

    public Map() {
        map = new Tile[MAP_WIDTH][MAP_HEIGHT];
        initializeMap();
    }

    private void initializeMap() {
        for( int i = 0; i < MAP_WIDTH; i++ ) {
            for( int j = 0; j < MAP_HEIGHT; j++ ) {
                map[i][j] = new Tile();
            }
        }
    }

}
