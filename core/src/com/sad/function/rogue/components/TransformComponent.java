package com.sad.function.rogue.components;

/**
 * Component for objects that have a location on screen.
 */
public class TransformComponent extends Component {

    /**
     * Default constructor calls the Component base
     * class so that it gets assigned a unique UUID.
     */
    public TransformComponent(int x, int y) {
        super();

        this.x = x;
        this.y = y;
    }

    public int x = 0;
    public int y = 0;
}