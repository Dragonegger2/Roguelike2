package com.sad.function.rogue.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public interface BaseScreen extends Disposable {

    void processInput() ;

    void update(float delta);

    void render(Batch batch);
}
