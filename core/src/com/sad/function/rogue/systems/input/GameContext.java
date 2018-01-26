package com.sad.function.rogue.systems.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains the list of contexts available in the game.
 *
 * These are directly tied to actions.
 *
 * Actions have input that triggers them.
 */
public class GameContext {
    public enum GameContexts { GAME }

    Map<GameContexts, Action> mappedContexts = new HashMap<>();

    /**
     * Registers an action to be bound to a context.
     * @param contextToMapTo
     * @param gameAction
     */
    public void registerActionToContext(GameContexts contextToMapTo, Action gameAction) {
        mappedContexts.put(contextToMapTo, gameAction);
    }
}
