package com.sad.function.rogue.systems.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Input manager for mah game.
 */
public class InputManager implements InputProcessor{
    private boolean[] keyStates = new boolean[256];

    private ArrayList<KeyState> managedKeys = new ArrayList<KeyState>();

    public InputManager() {

    }

    /**
     * Queries the current keyboard state.
     * @param keycode
     * @return Returns true if the key is down.
     */
    public boolean isKeyDown(int keycode) {
//        if(managedKeys.)
        return false;
    }

    /**
     * Queries the current keyboard state.
     * @param keycode
     * @return Returns true if the key is up.
     */
    public boolean isKeyUp(int keycode) {
        return !keyStates[keycode];
    }

    @Override
    public boolean keyDown(int keycode) {
        keyStates[keycode] = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyStates[keycode] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        public int keyCode;
        public char character;

        public boolean isPressed;
    }
}
