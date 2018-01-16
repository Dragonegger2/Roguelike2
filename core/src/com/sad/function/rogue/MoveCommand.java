package com.sad.function.rogue;

import com.sad.function.rogue.objects.GameEntity;
import com.sad.function.rogue.objects.Tile;

public class MoveCommand implements ICommand {
    private int dx;
    private int dy;
    private Tile[][] map;
    GameEntity commandTarget;

    public MoveCommand(int dx, int dy, Tile[][] map, GameEntity target) {
        this.dx = dx;
        this.dy = dy;
        this.map = map;
        commandTarget = target;
    }


    @Override
    public void Execute() {
        commandTarget.move(dx, dy, map);
    }
}
