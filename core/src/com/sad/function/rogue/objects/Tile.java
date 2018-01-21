package com.sad.function.rogue.objects;

public class Tile {

    public boolean blocked;
    public boolean blockSight;

    /**
     * Default constructor is a wall with regards to collisions and FOV.
     */
    public Tile() {
        this.blocked = false;
        this.blockSight = false;
    }

    public Tile(boolean blocked) {
        this.blocked = blocked;

        if(blocked) {
            blockSight = true;
        }
        else {
            this.blockSight = blocked;
        }
    }

}
