package com.sad.function.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class umpteen extends ApplicationAdapter implements InputProcessor {

    FrameBuffer lightBuffer;
    TextureRegion lightBufferRegion;

    SpriteBatch spriteBatch;
    OrthographicCamera camera;

    Vector3 vector3;

    Texture lightSource, badLogic;
    Sprite sprite;
    Vector2 position;

    Stage stage;
    InputMultiplexer input = new InputMultiplexer();

    SelectBox<String> lboDestBox;
    SelectBox<String> lboSourceBox;
    SelectBox<String> lboDestBox2;
    SelectBox<String> lboSourceBox2;

    @Override
    public void create() {
        position = new Vector2(0,0);
        vector3=new Vector3();
        camera=new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch=new SpriteBatch();

        lightSource =new Texture("light.png");

        badLogic =new Texture("badlogic.jpg");

        Gdx.gl.glClearColor(0.3f,0.3f,0.3f,1);

        Skin skin = new Skin(Gdx.files.internal("./data/default/skin/uiskin.json"));


        stage = new Stage(new StretchViewport(640, 480));

        lboSourceBox = new SelectBox<>(skin);
        lboSourceBox.setItems("GL_ZERO", "GL_ONE", "GL_SRC_COLOR", "GL_ONE_MINUS_SRC_COLOR", "GL_SRC_ALPHA", "GL_ONE_MINUS_SRC_ALPHA", "GL_DST_COLOR");
        lboSourceBox.setWidth(200);
        lboSourceBox.setPosition(0, 480 - lboSourceBox.getHeight());

        lboSourceBox.setSelected("GL_SRC_ALPHA");

        lboDestBox = new SelectBox<>(skin);
        lboDestBox.setItems("GL_ZERO", "GL_ONE", "GL_SRC_COLOR", "GL_ONE_MINUS_SRC_COLOR", "GL_SRC_ALPHA", "GL_ONE_MINUS_SRC_ALPHA", "GL_DST_COLOR");
        lboDestBox.setWidth(200);
        lboDestBox.setPosition(200, 480 - lboSourceBox.getHeight());
        lboDestBox.setSelected("GL_ONE");

        lboSourceBox2 = new SelectBox<>(skin);
        lboSourceBox2.setItems("GL_ZERO", "GL_ONE", "GL_SRC_COLOR", "GL_ONE_MINUS_SRC_COLOR", "GL_SRC_ALPHA", "GL_ONE_MINUS_SRC_ALPHA", "GL_DST_COLOR");
        lboSourceBox2.setWidth(200);
        lboSourceBox2.setPosition(0, 480 - lboDestBox.getHeight() * 2);
        lboSourceBox2.setSelected("GL_DST_COLOR");

        lboDestBox2 = new SelectBox<>(skin);
        lboDestBox2.setItems("GL_ZERO", "GL_ONE", "GL_SRC_COLOR", "GL_ONE_MINUS_SRC_COLOR", "GL_SRC_ALPHA", "GL_ONE_MINUS_SRC_ALPHA", "GL_DST_COLOR");
        lboDestBox2.setWidth(200);
        lboDestBox2.setPosition(200, 480 - lboDestBox.getHeight() * 2);
        lboDestBox2.setSelected("GL_ZERO");

        stage.addActor(lboDestBox);
        stage.addActor(lboSourceBox);
        stage.addActor(lboDestBox2);
        stage.addActor(lboSourceBox2);

        input.addProcessor(0, this);
        input.addProcessor(1, stage);

        Gdx.input.setInputProcessor(input);
    }

    Color baseColor = new Color(1.0f, 1.0f, 1.0f, 0.99607843f);

    private int returnGL20RenderType(String value) {
        if(value.equals("GL_ZERO")) {
            return GL20.GL_ZERO;
        }
        else if( value.equals("GL_ONE")) {
            return GL20.GL_ONE;
        }
        else if (value.equals("GL_SRC_COLOR")) {
            return GL20.GL_SRC_COLOR;
        }
        else if( value.equals("GL_ONE_MINUS_SRC_COLOR")) {
            return GL20.GL_ONE_MINUS_SRC_COLOR;
        }
        else if(value.equals("GL_SRC_ALPHA")) {
            return GL20.GL_SRC_ALPHA;
        }
        else if(value.equals("GL_ONE_MINUS_SRC_ALPHA")) {
            return GL20.GL_ONE_MINUS_SRC_ALPHA;
        }
        else if(value.equals("GL_DST_COLOR")) {
            return GL20.GL_DST_COLOR;
        }

        System.out.println("RETURNING DEFAULT INSTEAD OF MATCHING VALUE");
        return GL20.GL_ZERO;
    }

    @Override
    public void render() {

        stage.act();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//Badlogic render
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.setColor(baseColor);
        spriteBatch.begin();
            spriteBatch.draw(badLogic, 0, 0);
        spriteBatch.end();


        lightBuffer.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // setup the right blending
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        Gdx.gl.glBlendFunc(returnGL20RenderType(lboSourceBox.getSelected()), returnGL20RenderType(lboDestBox.getSelected()));
        Gdx.gl.glEnable(GL20.GL_BLEND);

            // start rendering the lights to our spriteBatch
            spriteBatch.begin();

                // set the color of your light (red,green,blue,alpha values)
//                spriteBatch.setColor(0.9f, 0.4f, 0f, 1f);
                spriteBatch.setColor(Color.WHITE);

                // and renderLighting the sprite
                spriteBatch.draw(lightSource, -92.0f, 393f, 512, 512);

                spriteBatch.setColor(Color.WHITE);
            spriteBatch.end();
        lightBuffer.end();

        // now we renderLighting the lightBuffer to the default "frame buffer"
        // with the right blending !

//        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        Gdx.gl.glBlendFunc(returnGL20RenderType(lboSourceBox2.getSelected()), returnGL20RenderType(lboDestBox2.getSelected()));
        spriteBatch.begin();
            spriteBatch.draw(lightBuffer.getColorBufferTexture(), 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.end();

        // post light-rendering
        // you might want to renderLighting your status bar stuff here
        stage.draw();
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
//
//        vector3.set(screenX,screenY,0);
////        camera.unproject(vector3);
////        sprite.setPosition(vector3.x-sprite.getWidth()/2,vector3.y-sprite.getHeight()/2);
//        position.x = screenX;
//        position.y = screenY;
//
//        System.out.println(position);
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