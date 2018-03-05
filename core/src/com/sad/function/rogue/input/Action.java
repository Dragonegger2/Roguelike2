package com.sad.function.rogue.input;


import com.sad.function.rogue.event.ICommand;

import java.util.LinkedList;

public class Action {
    private LinkedList<GameInput> gInputs = new LinkedList<>();
    private ICommand command;

    //TODO Should also have a command it executes. Maybe?

    public Action() {

    }

    public Action(GameInput ... gameInputs) {
        registerInput(gameInputs);
    }

    public void registerInput(GameInput ... gInput) {
        for (GameInput input : gInput
             ) {
            gInputs.add(input);
        }
    }

    public void registerCommand(ICommand registeredCommand) {
        this.command = registeredCommand;
    }

    public ICommand value() {
        for (GameInput input: gInputs) {
            if(input.getValue() > 0 && command != null) {
                return command;
            }
        }
        return null;
    }
}
