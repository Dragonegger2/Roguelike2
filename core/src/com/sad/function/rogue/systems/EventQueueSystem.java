package com.sad.function.rogue.systems;

import com.sad.function.rogue.event.GameEvent;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Where events get dispatched to when actions are take by the player and the like.
 */
public class EventQueueSystem {
    private static EventQueueSystem ourInstance = new EventQueueSystem();

    public static EventQueueSystem getInstance() {
        return ourInstance;
    }

    private Queue<GameEvent> gameEventQueue;
    private EventQueueSystem() {
        gameEventQueue = new LinkedList<GameEvent>();

    }
}
