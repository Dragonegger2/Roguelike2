package com.sad.function.rogue.components;

import com.badlogic.gdx.graphics.Texture;

/**
 * Component for static assets that are rendered to the screen.
 */
public class SpriteComponent extends Component {

    public SpriteComponent() {
        super();
    }
    public String textureLocation = "badlogic.jpg";
    public Texture Sprite = new Texture(textureLocation);

    public float width;
    public float height;

    //Need these for certain rendering actions, but we won't be scaling up.
    public static final float  scaleX = 1.0f;
    public static final float scaleY = 1.0f;

    //No rotation in Roguelike-bitches
    public static final float rotation = 0.0f;
}