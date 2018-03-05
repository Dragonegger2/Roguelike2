package com.sad.function.rogue.event;

import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MoveOrAttackCommand implements ICommand {
    private int dx;
    private int dy;
    private EntityManager entityManager;

    /**
     *
     * @param dx Delta of X
     * @param dy Delta of Y
     * @param entityManager Current Screen's entity manager.
     */
    public MoveOrAttackCommand(int dx, int dy, EntityManager entityManager) {
        this.dx = dx;
        this.dy = dy;
        this.entityManager = entityManager;
    }


    @Override
    public void Execute(UUID target) {
//        player

    }
}
