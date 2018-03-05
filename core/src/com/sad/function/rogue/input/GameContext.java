package com.sad.function.rogue.input;

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
    public enum GameContexts {
        GAME
    }

    Map<String, Action> mappedContexts = new HashMap<>();

    /**
     * Registers an action to be bound to a context.
     * @param actionName Look up name.
     * @param gameAction Action that gets fired.
     */
    public void registerActionToContext(String actionName, Action gameAction) {
        mappedContexts.put(actionName, gameAction);
    }

    /**
     * Returns whether any registered input as been fired.
     * @param actionName
     * @return
     */
    public float value(String actionName) {
        //return mappedContexts.get(actionName).value();
        if(mappedContexts.get(actionName).value() != null) {
            return 1;
        }
        return 0;
    }
}
