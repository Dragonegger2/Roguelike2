package com.sad.function.rogue.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Component for static assets that are rendered to the screen.
 */
public class SpriteComponent extends Component {

    public SpriteComponent() {
        super();
    }
    public String textureLocation = "badlogic.jpg";

    //Just a mask for static assets.
    private Texture sprite = new Texture(textureLocation);
    public TextureRegion Sprite = new TextureRegion(sprite);

    public float width;
    public float height;

    //Need these for certain rendering actions, but we won't be scaling up.
    public static final float  scaleX = 1.0f;
    public static final float scaleY = 1.0f;

    //No rotation in Roguelike-bitches
    public static final float rotation = 0.0f;
}