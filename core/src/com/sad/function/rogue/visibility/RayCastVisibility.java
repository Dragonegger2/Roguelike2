package com.sad.function.rogue.visibility;

import com.sad.function.rogue.dungeon.HelperFunctions;
import com.sad.function.rogue.dungeon.LevelPoint;
import com.sad.function.rogue.dungeon.Rect;
import com.sad.function.rogue.objects.Dungeon;

public class RayCastVisibility {
    boolean[][] fieldOfViewMap;

    public RayCastVisibility() {
    }

    private boolean[][] emptyFOV() {
        //Set nothing visible by default.
        for(int x = 0; x < fieldOfViewMap.length; x++) {
            for(int y = 0; y < fieldOfViewMap[x].length; y++) {
                fieldOfViewMap[x][y] = false;
            }
        }

        return fieldOfViewMap;
    }

    public void Compute(Dungeon dungeon, int playerX, int playerY, int rangeLimit) {
        fieldOfViewMap = new boolean[dungeon.MAP_WIDTH][dungeon.MAP_HEIGHT];

        emptyFOV();

        LevelPoint origin = new LevelPoint(playerX, playerY);

        setVisible(playerX, playerY);

        if(rangeLimit != 0) {
            Rect area = new Rect(0, 0, dungeon.MAP_WIDTH, dungeon.MAP_HEIGHT);
            if(rangeLimit >= 0 ) {
                area.intersect(new Rect(origin.x-rangeLimit, origin.y-rangeLimit, rangeLimit*2+1, rangeLimit*2+1));
            }
            for(int x = area.Left(); x<area.Right(); x++) {
                TraceLine(origin, x, area.Top(), rangeLimit, dungeon);
                TraceLine(origin, x, area.Bottom() - 1, rangeLimit, dungeon);
            }
            for(int y=area.Top() +1; y< area.Bottom()-1; y++) {
                TraceLine(origin, area.Left(), y, rangeLimit, dungeon);
                TraceLine(origin, area.Right() - 1, y, rangeLimit, dungeon);
            }
        }
    }

    private void TraceLine(LevelPoint origin, int x2, int y2, int rangeLimit, Dungeon dungeon) {
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
            if(dungeon.map[x][y].blockSight) break;
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
