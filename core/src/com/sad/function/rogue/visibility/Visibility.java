package com.sad.function.rogue.visibility;

public abstract class Visibility {
    /**
     * Returns true if the object is in the field of view.
     * @param playerX
     * @param playerY
     * @param rangeLimit
     * @return
     */
    public abstract void Compute(int playerX, int playerY, int rangeLimit);

    public abstract boolean isVisible(int x, int y);
}
