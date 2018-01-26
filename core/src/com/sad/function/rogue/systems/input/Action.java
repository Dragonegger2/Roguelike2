package com.sad.function.rogue.systems.input;


import java.util.LinkedList;

public class Action {
    private LinkedList<GameInput> gInputs = new LinkedList<>();

    public void registerInput(GameInput gInput) {
        gInputs.add(gInput);
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
