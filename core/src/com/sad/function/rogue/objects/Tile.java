package com.sad.function.rogue.objects;

public class Tile {

    public boolean blocked;
    public boolean blockSight;
    public boolean explored;

    /**
     * Default constructor is a wall with regards to collisions and FOV.
     */
    public Tile() {
        blocked = false;
        blockSight = false;
        explored = false;
    }

    public Tile(boolean blocked) {
        this.blocked = blocked;
        explored = false;

        if(blocked) {
            blockSight = true;
        }
        else {
            blockSight = false;
        }
    }

}
