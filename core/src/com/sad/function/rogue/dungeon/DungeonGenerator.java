package com.sad.function.rogue.dungeon;

import com.badlogic.gdx.math.Vector2;
import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.objects.Dungeon;
import com.sad.function.rogue.objects.Tile;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonGenerator {
    public Dungeon dungeon;
    public GameEntity player;

    public static final int ROOM_MAX_SIZE = 10;
    public static final int ROOM_MIN_SIZE = 6;
    public static final int MAX_ROOMS = 30;

    public static final int MAX_ROOM_MOONSTERS = 3;

    public DungeonGenerator(Dungeon dungeon, GameEntity player) {
        this.dungeon = dungeon;
        this.player = player;
    }

    /**
     * Carve a room out of the dungeon object.
     * @param room Accepts a rect object which describes a room.
     */
    public void createRoom(Rect room) {
        for(int x = room.x1 + 1; x < room.x2; x++) {
            for(int y = room.y1 + 1; y < room.y2; y++ ) {
                dungeon.map[x][y].blocked = false;
                dungeon.map[x][y].blockSight = false;
            }
        }
    }

    public void makeMap() {
        sealDungeon();

        //Generate room list.
        LinkedList<Rect> rooms = new LinkedList<>();

        for(int i = 0; i < MAX_ROOMS; i++ ) {

            int w = ThreadLocalRandom.current().nextInt(ROOM_MIN_SIZE, ROOM_MAX_SIZE);
            int h = ThreadLocalRandom.current().nextInt(ROOM_MIN_SIZE, ROOM_MAX_SIZE);
            int x = ThreadLocalRandom.current().nextInt(0, dungeon.MAP_WIDTH - w - 1);
            int y = ThreadLocalRandom.current().nextInt(0, dungeon.MAP_HEIGHT - h - 1);

            Rect new_room = new Rect(x, y, w, h);

            boolean failed = false;

            for (Rect room : rooms) {
                if (new_room.intersect(room)) {
                    failed = true;
                    break;
                }
            }

            //If the room did not fail...
            if(!failed) {
                createRoom(new_room);

                Vector2 newRoomCenter = new_room.center();

                //Put the player in the first location.
                if(rooms.size() == 0) {
                    player.x = (int) newRoomCenter.x;
                    player.y = (int) newRoomCenter.y;
                }

                //For all other
                else {
                    Vector2 previousRoomCenter = rooms.getLast().center();

                    if(ThreadLocalRandom.current().nextInt(0, 1) == 1) {
                        //First move horizontally then vertically.
                        createHorizontalTunnel((int)previousRoomCenter.x, (int) newRoomCenter.x, (int)previousRoomCenter.y );
                        createVerticalTunnel((int)previousRoomCenter.y, (int)newRoomCenter.y, (int)newRoomCenter.x );
                    }
                    else {
                        //First move vertically then horizontally
                        createVerticalTunnel((int)previousRoomCenter.y, (int)newRoomCenter.y, (int)previousRoomCenter.x);
                        createHorizontalTunnel((int)previousRoomCenter.x, (int)newRoomCenter.x, (int)newRoomCenter.y);
                    }
                }
                rooms.add(new_room);
            }
        }
    }

    private void sealDungeon() {
        for(int x = 0; x < dungeon.map.length; x++) {
            for(int y = 0; y < dungeon.map[x].length; y++ ) {
                dungeon.map[x][y] = new Tile(true);
            }
        }
    }

    private void createHorizontalTunnel(int x1, int x2, int y) {
        for(int x = Math.min(x1, x2); x < Math.max(x1, x2) + 1; x++ ) {
            dungeon.map[x][y].blockSight = false;
            dungeon.map[x][y].blocked = false;
        }
    }

    private void createVerticalTunnel(int y1, int y2, int x) {
        for(int y = Math.min(y1, y2); y < Math.max(y1, y2) + 1; y++ ) {
            dungeon.map[x][y].blockSight = false;
            dungeon.map[x][y].blocked = false;
        }
    }

    private void placeObjects(Rect room) {
        int numberOfMonsterToGenerate = ThreadLocalRandom.current().nextInt(0, MAX_ROOM_MOONSTERS);

        for(int i = 0; i < numberOfMonsterToGenerate; i++ ) {
            int x = ThreadLocalRandom.current().nextInt(room.x1, room.x2);
            int y = ThreadLocalRandom.current().nextInt(room.y1, room.y2);

            //TODO:Roll for monster creation.
        }
    }

}
