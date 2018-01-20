package com.sad.function.rogue.visibility;

import com.badlogic.gdx.math.Rectangle;
import com.sad.function.rogue.dungeon.HelperFunctions;
import com.sad.function.rogue.dungeon.LevelPoint;
import com.sad.function.rogue.dungeon.Rect;
import com.sad.function.rogue.objects.Map;

public class RayCastVisibility extends Visibility {
    private Map referenceMap;
    private boolean[][] fieldOfViewMap;

    public RayCastVisibility(Map mapObject) {
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
        LevelPoint origin = new LevelPoint(playerX, playerY);

        setVisible(playerX, playerY);

        if(rangeLimit != 0) {
            Rect area = new Rect(0, 0, referenceMap.MAP_WIDTH, referenceMap.MAP_HEIGHT);
            if(rangeLimit >= 0 ) {
                area.intersect(new Rect(origin.x-rangeLimit, origin.y-rangeLimit, rangeLimit*2+1, rangeLimit*2+1));
            }
            for(int x = area.Left(); x<area.Right(); x++) {

            }
        }
    }

    private void TraceLine(LevelPoint origin, int x2, int y2, int rangeLimit) {
        int xDiff = x2 - origin.x, yDiff = y2 - origin.y, xLen = Math.abs(xDiff), yLen = Math.abs(yDiff);
        int xInc = (int)Math.signum(xDiff), yInc = ((int)Math.signum(yDiff))<<16, index = (origin.y<<16) + origin.x;
        if(xLen < yLen) // make sure we walk along the long axis
        {
            int tempLen = xLen;
            xLen = yLen;
            yLen = tempLen;

            int tempInc = xInc;
            xInc = yInc;
            yInc =tempInc;

        }
        int errorInc = yLen*2, error = -xLen, errorReset = xLen*2;
        while(--xLen >= 0) // skip the first point (the origin) since it's always visible and should never stop rays
        {
            index += xInc; // advance down the long axis (could be X or Y)
            error += errorInc;
            if(error > 0) { error -= errorReset; index += yInc; }
            int x = index & 0xFFFF, y = index >> 16;
            if(rangeLimit >= 0 && HelperFunctions.getDistance(origin.x, origin.y, x, y) > rangeLimit) break;
            setVisible(x, y);
            if(referenceMap.map[x][y].blocksLight) break;
        }

    }
    private void setVisible(int x, int y) {
        fieldOfViewMap[x][y] = true;
    }

    /**
     * returns whether the passed coordinate is visible.
     * @param x
     * @param y
     * @return
     */
    public boolean isVisible(int x, int y) { return fieldOfViewMap[x][y]; }

}
