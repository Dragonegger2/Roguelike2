package com.sad.function.rogue;

import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.objects.Tile;

import java.util.UUID;

public class MoveEvent implements IEvent {
    private int dx;
    private int dy;
    private Tile[][] map;
    GameEntity commandTarget;
    UUID target;

    /**
     *
     * @param dx Delta of X
     * @param dy Delta of Y
     * @param map Tile map, used to check for occupied spaces.
     * @param target Registering object.
     */
    public MoveEvent(int dx, int dy, Tile[][] map, GameEntity target) {
        this.dx = dx;
        this.dy = dy;
        this.map = map;
        commandTarget = target;
    }

    public MoveEvent(int dx, int dy, Tile[][] map, UUID target) {
        this.dx = dx;
        this.dy = dy;
        this.map = map;
        this.target = target;
    }

    @Override
    public void Execute() {
        commandTarget.move(dx, dy, map);
    }
}
