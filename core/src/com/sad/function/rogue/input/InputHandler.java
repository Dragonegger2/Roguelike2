package com.sad.function.rogue.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.sad.function.rogue.event.ICommand;
import com.sad.function.rogue.event.MoveOrAttackCommand;

public class InputHandler {

    public ICommand handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            return moveLeft;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            return moveRight;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            return moveUp;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            return moveDown;
        }

        return null;
    }

    private MoveOrAttackCommand moveLeft = new MoveOrAttackCommand(-1, 0);
    private MoveOrAttackCommand moveRight = new MoveOrAttackCommand(1, 0);
    private MoveOrAttackCommand moveUp = new MoveOrAttackCommand(0, 1);
    private MoveOrAttackCommand moveDown = new MoveOrAttackCommand(0, -1);
}

