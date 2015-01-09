package com.tank.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.controller.PlayerController;
import com.tank.game.model.World;
import com.tank.game.view.WorldRender;

public class GameScreen implements Screen, InputProcessor {
    private WorldRender renderer;
    private PlayerController controller;

    private int width, height;

    @Override
    public void show() {
        final World world = new World();
        renderer = new WorldRender(world, true);
        controller = new PlayerController(world);
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        renderer.render();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
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
        float x = screenX / (float) Gdx.graphics.getWidth() * 35f;
        float y = screenY / (float) Gdx.graphics.getHeight() * 26f;
        if (x < 3f && y > 20f && y < 23f) {
            controller.leftPressed();
        } else if (x > 6f && x < 9f && y > 20f && y < 23f) {
            controller.rightPressed();
        } else if (x > 3f && x < 6f && y > 17f && y < 20f) {
            controller.upPressed();
        } else if (x > 3f && x < 6f && y > 23f) {
            controller.downPressed();
        } else if (x > 32f && y > 23f) {
            controller.firePressed();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float x = screenX / (float) Gdx.graphics.getWidth() * 35f;
        float y = screenY / (float) Gdx.graphics.getHeight() * 26f;
        if (x < 3f && y > 20f && y < 23f) {
            controller.leftReleased();
        } else if (x > 6f && x < 9f && y > 20f && y < 23f) {
            controller.rightReleased();
        } else if (x > 3f && x < 6f && y > 17f && y < 20f) {
            controller.upReleased();
        } else if (x > 3f && x < 6f && y > 23f) {
            controller.downReleased();
        } else if (x > 32f && y > 23f) {
            controller.fireReleased();
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
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
