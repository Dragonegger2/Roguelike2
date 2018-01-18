package com.sad.function.rogue.dungeon;

import com.sad.function.rogue.objects.Tile;

public class DungeonGenerator {
    public Tile[][] map;

    public DungeonGenerator(Tile[][] map) {
        this.map = map;
    }

    /**
     * Carve a room out of the map object.
     * @param room Accepts a rect object which describes a room.
     */
    public void createRoom(Rect room) {
        for(int x = room.x1 + 1; x < room.x2; x++) {
            for(int y = room.y1 + 1; y < room.y2; y++ ) {
                map[x][y].blocked = false;
                map[x][y].blockSight = false;
            }
        }
    }

    public void make_map() {
        sealDungeon();

        Rect room1 = new Rect(20, 15, 10, 15);
        Rect room2 = new Rect(50, 15, 10, 15);

        createRoom(room1);
        createRoom(room2);

        createHorizontalTunnel(25, 55, 23);
    }

    private void sealDungeon() {
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[x].length; y++ ) {
                map[x][y] = new Tile(true);
            }
        }
    }

    private void createHorizontalTunnel(int x1, int x2, int y) {
        for(int x = Math.min(x1, x2); x < Math.max(x1, x2) + 1; x++ ) {
            map[x][y].blockSight = false;
            map[x][y].blocked = false;
        }
    }

    private void createVerticalTunnel(int y1, int y2, int x) {
        for(int y = Math.min(y1, y2); x < Math.max(y1, y2) + 1; y++ ) {
            map[x][y].blockSight = false;
            map[x][y].blocked = false;
        }
    }

}
