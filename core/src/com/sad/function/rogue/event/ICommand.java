package com.sad.function.rogue.event;

import com.sad.function.rogue.systems.EntityManager;

import java.util.UUID;

public interface ICommand {
    void Execute(EntityManager entityManager, UUID actor);
}
