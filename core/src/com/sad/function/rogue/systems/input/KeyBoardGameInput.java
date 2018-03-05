package com.sad.function.rogue.systems.input;

import com.badlogic.gdx.Gdx;

/**
 * Represents an object that defines how the input is handled.
 */
public class KeyBoardGameInput implements GameInput {
    private STATE reactState;
    private int keyCode;

    public KeyBoardGameInput(STATE reactState, int KeyCode) {
        this.reactState = reactState;
        this.keyCode = KeyCode;
    }

    @Override
    public float getValue() {
        if(reactState == STATE.IS_KEY_PRESSED) {
            if(Gdx.input.isKeyPressed(keyCode)){
                return 1;
            }
            return 0;
        }
        else if(reactState == STATE.IS_KEY_JUST_PRESSED) {
            if(Gdx.input.isKeyJustPressed(keyCode)){
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public enum STATE { IS_KEY_JUST_PRESSED, IS_KEY_PRESSED }
}
