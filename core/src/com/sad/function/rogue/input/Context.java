package com.sad.function.rogue.input;


import com.sad.function.rogue.event.ICommand;

import java.util.Map;

public class Context {
//    private MoveOrAttackCommand arrows = new MoveOrAttackCommand()
    //Map<Action ... (Map multiple inputs to the same command, Command>

    //
    private Map<GameInput, ICommand> contextMaps;
    
    public void register(ICommand mappedCOmmand, GameInput ... inputs) {

        for(GameInput input : inputs) {
            contextMaps.put(input, mappedCOmmand);
        }

    }
}

