package com.tank.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tank.game.screens.GameScreen;

public class FirstGame extends Game {
    private Stage stage;
    private Player player;

    @Override
    public void create() {
        setScreen(new GameScreen());
        /*
        stage = new Stage();
        player = new Player("player");
        stage.addActor(player);
        Gdx.input.setInputProcessor(stage);
        */
    }

    /*
    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    */
}
