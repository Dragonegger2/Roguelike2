package com.sad.function.rogue.components;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Tags an entity as a player. That's all.
 */
public class PhysicsComponent extends Component{
    public Body body;
    public PhysicsComponent(Body body) {
        super();

        this.body = body;
    }
}
