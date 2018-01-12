package com.sad.function.rogue.objects;

public class Tile {

    public boolean blocked;
    public boolean blockSight;

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

    public Tile(boolean blocked, boolean blockSight){
        this.blocked = blocked;
        this.blockSight = blockSight;
    }

}
