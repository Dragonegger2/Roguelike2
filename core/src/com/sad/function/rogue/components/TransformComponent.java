package com.sad.function.rogue.components;

/**
 * Component for objects that have a location on screen.
 */
public class TransformComponent extends Component {

    /**
     * Default constructor calls the Component base
     * class so that it gets assigned a unique UUID.
     */
    public TransformComponent() {
        super();
    }

    public float x;
    public float y;

    public float originX = 1.0f;
    public float originY = 1.0f;

    public boolean left = false;
}