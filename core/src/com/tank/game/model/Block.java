package com.tank.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
    public static final float SIZE = 1f;

    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();

    public Block(Vector2 pos) {
        position = pos;
        bounds.width = SIZE;
        bounds.height = SIZE;
        bounds.setX(pos.x);
        bounds.setY(pos.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
