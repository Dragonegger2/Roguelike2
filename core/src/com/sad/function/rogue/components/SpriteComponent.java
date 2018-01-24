package com.sad.function.rogue.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Component for static assets that are rendered to the screen.z
 */
public class SpriteComponent extends Component {

    public SpriteComponent(String textureLocation) {
        super();
        sprite = new Texture(textureLocation);
    }

    public SpriteComponent(Texture sprite) {
        this.sprite = sprite;
    }
    //Just a mask for static assets.
    public Texture sprite;
}