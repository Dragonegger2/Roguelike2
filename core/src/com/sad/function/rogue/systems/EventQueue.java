package com.sad.function.rogue.systems;

import com.sad.function.rogue.event.IEvent;

import java.util.LinkedList;

public class EventQueue {

    public LinkedList<IEvent> events;

    private static EventQueue ourInstance;

    private EventQueue() {
        events = new LinkedList<IEvent>();
        ourInstance = this;
    }

    /**
     *
     * @return
     */
    public static EventQueue getInstance() {
        if(ourInstance == null) {
            ourInstance = new EventQueue();
        }
        return ourInstance;
    }
}
