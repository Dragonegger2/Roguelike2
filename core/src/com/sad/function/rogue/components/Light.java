package com.sad.function.rogue.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.sad.function.rogue.systems.AssetManager;

/**
 * Pairs with a Transform Component? to draw itself on the world.
 */
public class Light extends Component {
    /**
     *
     * @param distance how far the light will reach.
     * @param color, Color of the light source. They should all be created as white circles on a transparent background.
     * @param textureLocationName, uses the AssetManager to load the requested asset into memory. TODO: Make this better.
     */
    public Light(float distance, Color color, String textureLocationName) {
        this.distance = distance;
        this.color = color;
        this.source = AssetManager.getInstance().forceLoadAndReturnOfTexture(textureLocationName);
    }

    /**
     * Distance the light will be rendered.
     */
    public float distance;

    /**
     * Color of the light.
     */
    public Color color;

    /**
     * This should be a texture source, or a reference from the image source manager.
     */
    public Texture source;
}
