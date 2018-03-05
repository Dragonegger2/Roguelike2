package com.sad.function.rogue.event;

import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public class MoveOrAttackEvent implements IEvent {
    private int dx;
    private int dy;
    private UUID target;
    private EntityManager entityManager;

    /**
     *
     * @param dx Delta of X
     * @param dy Delta of Y
     * @param target Registering object.
     */
    public MoveOrAttackEvent(int dx, int dy, UUID target, EntityManager entityManager) {
        this.dx = dx;
        this.dy = dy;
        this.target = target;
        this.entityManager = entityManager;
    }


    @Override
    public void Execute() {
//        player
    }
}
