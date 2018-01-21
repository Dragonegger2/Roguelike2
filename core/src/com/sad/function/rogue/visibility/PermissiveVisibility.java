package com.sad.function.rogue.visibility;

import com.sad.function.rogue.dungeon.HelperFunctions;
import com.sad.function.rogue.dungeon.LevelPoint;
import com.sad.function.rogue.objects.Map;

public class PermissiveVisibility extends Visibility {
    private Map referenceMap;
    private boolean[][] fieldOfViewMap;

    public PermissiveVisibility(Map mapObject) {
        referenceMap = mapObject;

        SetMap(mapObject);
    }

    /**
     * Sets the map object that ShadowCastVisibility will use for calculations.
     *
     * Allows me to reuse the calculator object over and over again without needing to recreate one;
     * I just pass a new map to it and it will begin calculating shadows for me.
     *
     * @param map
     */
    public void SetMap(Map map) {
        this.referenceMap = map;

        this.fieldOfViewMap = new boolean[referenceMap.MAP_WIDTH][referenceMap.MAP_HEIGHT];

        //Set nothing visible by default.
        for(int x = 0; x < fieldOfViewMap.length; x++) {
            for(int y = 0; y < fieldOfViewMap[x].length; y++) {
                fieldOfViewMap[x][y] = false;
            }
        }
    }


    @Override
    public void Compute(int playerX, int playerY, int rangeLimit) {

    }

    private boolean blocksLight(int x, int y) {
        try {
            return referenceMap.map[x][y].blockSight;
        }
        catch(IndexOutOfBoundsException e) {
            return false;
        }
    }

    private void Compute(int octant, LevelPoint origin, int rangeLimit, int x, Slope top, Slope bottom) {
        for(; x <= rangeLimit; x++) {
            int topY = top.X ==1 ? x: ((x*2+1) * top.Y + top.X - 1)/ (top.X*2);
            int bottomY = bottom.Y == 0 ? 0 : ((x * 2 - 1) * bottom.Y + bottom.X ) / (bottom.X * 2);

            int wasOpaque = -1; //0:false, 1:true, -1 not applicable.
            for(int y=topY; y >= bottomY; y--){
                int tx = origin.x, ty = origin.y;
                switch(octant) {
                    case 0: tx += x; ty -= y; break;
                    case 1: tx += y; ty -= x; break;
                    case 2: tx -= y; ty -= x; break;
                    case 3: tx -= x; ty -= y; break;
                    case 4: tx -= x; ty += y; break;
                    case 5: tx -= y; ty += x; break;
                    case 6: tx += y; ty += x; break;
                    case 7: tx += x; ty += y; break;
                }

                boolean inRange = rangeLimit < 0 || HelperFunctions.getDistance(new LevelPoint(tx, ty)) <= rangeLimit;

                if(inRange) setVisible(tx, ty);
                boolean isOpaque = !inRange || blocksLight(tx, ty);

                if( x != rangeLimit) {
                    if(isOpaque) {
                        if(wasOpaque == 0) {
                            Slope newBottom = new Slope(y*2+1, x*2-1);
                            if(!inRange || y == bottomY) {
                                bottom = newBottom;
                                break;
                            }
                            else {
                                Compute(octant, origin, rangeLimit, x+1, top, newBottom);
                            }
                            wasOpaque = 1;
                        }
                        else {
                            if(wasOpaque > 0) {
                                top = new Slope(2*y+1, x*2+1);
                                wasOpaque = 0;
                            }
                        }
                    }

                    if(wasOpaque != 0) {
                        break;
                    }
                }
            }
        }
    }
    public class Slope {
        public int Y, X;

        public Slope(int y, int x) { Y = y; X = x; }
    }

    /**
     * Helper method.
     */
    private void setVisible(int x, int y) {
        try {
            fieldOfViewMap[x][y] = true;
        }
        catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    /**
     * returns whether the passed coordinate is visible.
     * @param x
     * @param y
     * @return
     */
    public boolean isVisible(int x, int y) { return fieldOfViewMap[x][y]; }
}
