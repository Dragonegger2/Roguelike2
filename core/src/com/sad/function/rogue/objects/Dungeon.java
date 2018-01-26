package com.sad.function.rogue.objects;

import com.badlogic.gdx.graphics.Texture;

public class Dungeon {
    public int MAP_WIDTH = 80;
    public int MAP_HEIGHT = 50;

    public Tile[][] map;

    public Texture floorLit = new Texture("DungeonFloorLight.png");
    public Texture wallLit = new Texture("DungeonWallLight.png");
    public Texture floorDark = new Texture("DungeonFloorDark.png");
    public Texture wallDark = new Texture("DungeonWallDark.png");

    public Dungeon() {
        map = new Tile[MAP_WIDTH][MAP_HEIGHT];
        initializeMap();
    }

    public Dungeon(int width, int height) {
        MAP_WIDTH = width;
        MAP_HEIGHT = height;

        map = new Tile[MAP_HEIGHT][MAP_HEIGHT];
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
