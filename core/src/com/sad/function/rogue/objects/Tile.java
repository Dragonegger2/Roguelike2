package com.sad.function.rogue.objects;

public class Tile {

    public boolean blocked;
    public boolean blockSight;
    public boolean blocksLight;

    /**
     * Default constructor is a wall with regards to collisions and FOV.
     */
    public Tile() {
        this.blocked = false;
        this.blockSight = false;
        blocksLight = true;
    }

    public Tile(boolean blocked) {
        this.blocked = blocked;

        if(blocked) {
            blockSight = true;
            blocksLight = true;
        }
        else {
            this.blockSight = blocked;
            blocksLight = false;
        }
    }

}
