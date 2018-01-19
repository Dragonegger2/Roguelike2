package com.sad.function.rogue.dungeon;

import com.badlogic.gdx.math.Vector2;
import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.objects.Map;
import com.sad.function.rogue.objects.Tile;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonGenerator {
    public Map map;
    public GameEntity player;

    public static final int ROOM_MAX_SIZE = 10;
    public static final int ROOM_MIN_SIZE = 6;
    public static final int MAX_ROOMS = 30;


    public DungeonGenerator(Map map, GameEntity player) {
        this.map = map;
        this.player = player;
    }

    /**
     * Carve a room out of the map object.
     * @param room Accepts a rect object which describes a room.
     */
    public void createRoom(Rect room) {
        for(int x = room.x1 + 1; x < room.x2; x++) {
            for(int y = room.y1 + 1; y < room.y2; y++ ) {
                map.map[x][y].blocked = false;
                map.map[x][y].blockSight = false;
            }
        }
    }

    public void make_map() {
        sealDungeon();

        //Generate room list.
        ArrayList<Rect> rooms = new ArrayList<>();
        int num_rooms = 0;


        for(int i = 0; i < MAX_ROOMS; i++ ) {

            int w = ThreadLocalRandom.current().nextInt(ROOM_MIN_SIZE, ROOM_MAX_SIZE);
            int h = ThreadLocalRandom.current().nextInt(ROOM_MIN_SIZE, ROOM_MAX_SIZE);
            int x = ThreadLocalRandom.current().nextInt(0, map.MAP_WIDTH - w - 1);
            int y = ThreadLocalRandom.current().nextInt(0, map.MAP_HEIGHT - h - 1);

            System.out.println( x + " " + y + " " + w + " " + h);
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
                System.out.println("Room did not overlap!");
                createRoom(new_room);

                Vector2 newRoomCenter = new_room.center();

                if(num_rooms == 0) {
                    player.x = (int) newRoomCenter.x;
                    player.y = (int) newRoomCenter.y;
                }
                else {
                    Vector2 previousRoomCenter = rooms.get(num_rooms - 1).center();

                    if(ThreadLocalRandom.current().nextInt(0, 1) == 1) {
                        //First move horizontally then vertically.
                        createHorizontalTunnel((int)previousRoomCenter.x, (int) newRoomCenter.x, (int)previousRoomCenter.y );
                        createVerticalTunnel((int)previousRoomCenter.y, (int)newRoomCenter.y, (int)newRoomCenter.x );
                    }
                    else {
                        //First move vertically then horizontally
                        createVerticalTunnel((int)previousRoomCenter.y, (int)newRoomCenter.y, (int)previousRoomCenter.x);
                        createHorizontalTunnel((int)previousRoomCenter.x, (int)newRoomCenter.x, (int)previousRoomCenter.y);
                    }
                }
                rooms.add(new_room);
                num_rooms += 1;
            }
            else {
                System.out.println("Room would overlap!");
            }
        }
    }

    private void sealDungeon() {
        for(int x = 0; x < map.map.length; x++) {
            for(int y = 0; y < map.map[x].length; y++ ) {
                map.map[x][y] = new Tile(true);
            }
        }
    }

    private void createHorizontalTunnel(int x1, int x2, int y) {
        for(int x = Math.min(x1, x2); x < Math.max(x1, x2) + 1; x++ ) {
            map.map[x][y].blockSight = false;
            map.map[x][y].blocked = false;
        }
    }

    private void createVerticalTunnel(int y1, int y2, int x) {
        for(int y = Math.min(y1, y2); y < Math.max(y1, y2) + 1; y++ ) {
            map.map[x][y].blockSight = false;
            map.map[x][y].blocked = false;
        }
    }

}
