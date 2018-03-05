package com.sad.function.rogue.event;

import java.util.UUID;

public interface ICommand {
    void Execute(UUID actor);
}
