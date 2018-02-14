package com.sad.function.rogue.systems.input;


import java.util.LinkedList;

public class Action {
    private LinkedList<GameInput> gInputs = new LinkedList<>();

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

    public float value() {
        for (GameInput input: gInputs) {
            if(input.getValue() > 0 ) {
                return 1;
            }
        }
        return 0;
    }
}
