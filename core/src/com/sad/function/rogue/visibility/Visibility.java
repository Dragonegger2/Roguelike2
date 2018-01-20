package com.sad.function.rogue.visibility;

import com.sad.function.rogue.dungeon.LevelPoint;

public abstract class Visibility {
    /**
     * Returns true if the object is in the field of view.
     * @param origin
     * @param rangeLimit
     * @return
     */
    public abstract boolean[][] Compute(LevelPoint origin, int rangeLimit);
}
