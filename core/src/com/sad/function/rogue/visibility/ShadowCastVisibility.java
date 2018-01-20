package com.sad.function.rogue.visibility;

import com.sad.function.rogue.dungeon.HelperFunctions;
import com.sad.function.rogue.dungeon.LevelPoint;
import com.sad.function.rogue.objects.Map;

public class ShadowCastVisibility extends Visibility {
    private Map referenceMap;
    private boolean[][] fieldOfViewMap;

    public ShadowCastVisibility(Map mapObject) {
        referenceMap = mapObject;
        this.fieldOfViewMap = new boolean[mapObject.MAP_WIDTH][mapObject.MAP_HEIGHT];

        //Set nothing visible by default.
        for(int x = 0; x < fieldOfViewMap.length; x++) {
            for(int y = 0; y < fieldOfViewMap[x].length; y++) {
                fieldOfViewMap[x][y] = false;
            }
        }
    }

    @Override
    public boolean[][] Compute(LevelPoint origin, int rangeLimit) {
        fieldOfViewMap[origin.x][origin.y] = true;

        for(int octant = 0; octant < 8; octant++ ) {
            Compute(octant, origin, rangeLimit, 1, new Slope(1,1), new Slope(0, 1));
        }

        return fieldOfViewMap;
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

                boolean inRange = rangeLimit < 0 || HelperFunctions.getDistance(origin, new LevelPoint(tx, ty)) <= rangeLimit;

                if(inRange) setVisible(tx, ty);

                boolean isOpaque = !inRange || referenceMap.map[tx][ty].blocksLight;
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
    public void setVisible(int x, int y) {
        fieldOfViewMap[x][y] = true;
    }
}
