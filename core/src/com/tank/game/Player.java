package com.tank.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    Texture texture;
    TextureRegion player1;
    TextureRegion player2;
    Animation animation;
    TextureRegion[] walksFrame;
    float stateTime;

    public Player(String name) {
        setName(name);
        texture = new Texture("sprites.gif");
        player1 = new TextureRegion(texture, 0, 0, 16, 16);
        player2 = new TextureRegion(texture, 16, 0, 16, 16);
        setHeight(player1.getRegionHeight());
        setWidth(player1.getRegionWidth());

        walksFrame = new TextureRegion[30];
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0) {
                walksFrame[i] = player1;
            } else {
                walksFrame[i] = player2;
            }
        }
        animation = new Animation(0.25f, walksFrame);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        setX(getX() + 1);
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY());
    }
}
