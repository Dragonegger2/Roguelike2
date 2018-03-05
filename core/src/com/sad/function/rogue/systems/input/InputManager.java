package com.sad.function.rogue.systems.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.LinkedList;

/**
 * Input manager for mah game.
 */
public class InputManager implements InputProcessor{

    public LinkedList<KeyState> keyPresses = new LinkedList<KeyState>();

    @Override
    public boolean keyDown(int keycode) {
        keyPresses.add(new KeyState(keycode, true, false, false));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyPresses.add(new KeyState(keycode, false, true, false));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        keyPresses.add(new KeyState(character, false, false, true));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public class KeyState {
        public KeyState(int keyCode, boolean isDown, boolean isUp, boolean justReleased) {
            this.isDown = isDown;
            this.isUp = isUp;
            this.wasPressed = justReleased;

            characterCode = Input.Keys.toString(keyCode);
        }

        public KeyState(char characterCode, boolean isDown, boolean isUp, boolean wasPressed) {
            this.isDown = isDown;
            this.isUp = isUp;
            this.wasPressed = wasPressed;


            this.characterCode = Input.Keys.toString(characterCode );
        }

        public String characterCode;

        public boolean isDown;
        public boolean isUp;
        public boolean wasPressed;
    }
}
