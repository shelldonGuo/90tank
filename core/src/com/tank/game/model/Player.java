package com.tank.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public enum State {
        IDLE,
        WALKING,
        FIRE,
        DYING
    }

    public enum Facing {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public static final float SPEED = 4f;  // unit per second
    public static final float SIZE = 1.75f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    Facing facing = Facing.UP;

    float stateTime = 0;

    public Player(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void update(float delta){
        stateTime += delta;
        //position.add(velocity.cpy().scl(delta));
    }
    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    public Facing getFacing() {
        return facing;
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

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public float getStateTime() {
        return stateTime;
    }
}
