package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GdxTest extends ApplicationAdapter implements InputProcessor {

    FrameBuffer lightBuffer;
    TextureRegion lightBufferRegion;

    SpriteBatch spriteBatch;

    Vector3 vector3;

    Texture lightSource, badLogic;
    Sprite sprite;
    Vector2 position;
    Skin skin;

    Stage stage;
    InputMultiplexer input = new InputMultiplexer();
    @Override
    public void create() {
        position = new Vector2(0,0);
        vector3=new Vector3();
        spriteBatch=new SpriteBatch();

        lightSource =new Texture("bigOlLight.png");

        badLogic =new Texture("badlogic.jpg");

//        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);

        skin = new Skin(Gdx.files.internal("./data/default/skin/uiskin.json"));
        stage = new Stage();

        final TextButton button = new TextButton("Click me", skin, "default");

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        stage.addActor(button);

//        Gdx.input.setInputProcessor(stage);
        input.addProcessor(0, this);
        input.addProcessor(1, stage);

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render() {

        // post light-rendering
        // you might want to renderLighting your statusbar stuff here
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }

    @Override
    public void resize(int lowDisplayW, int lowDisplayH) {
        // Fakedlight system (alpha blending)

        // if lightBuffer was created before, dispose, we recreate a new one
        if (lightBuffer!=null) lightBuffer.dispose();
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, lowDisplayW, lowDisplayH, false);

        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,lightBuffer.getHeight()-lowDisplayH,lowDisplayW,lowDisplayH);

        lightBufferRegion.flip(false, false);

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
}