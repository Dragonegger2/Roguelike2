package com.sad.function.rogue.systems;

import com.sad.function.rogue.event.ICommand;

import java.util.LinkedList;

public class EventQueue {

    public LinkedList<ICommand> events;

    private static EventQueue ourInstance;

    private EventQueue() {
        events = new LinkedList<ICommand>();
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
