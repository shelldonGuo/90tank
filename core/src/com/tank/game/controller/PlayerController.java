package com.tank.game.controller;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tank.game.model.Block;
import com.tank.game.model.Player;
import com.tank.game.model.World;
import com.tank.game.utils.CollisionUtil;
import com.tank.game.view.WorldRender;

import java.util.HashMap;
import java.util.Map;

public class PlayerController {

    enum Keys {
        LEFT, RIGHT, UP, DOWN, FIRE
    }

    private World world;
    private Player player;
    private Array<Block> collidable = new Array<Block>();

    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.FIRE, false);
    }



    public PlayerController(World world) {
        this.world = world;
        this.player = world.getPlayer();
    }

    // ** Key presses and touches **************** //

    public void leftPressed() {
        keys.put(Keys.LEFT, true);
    }

    public void rightPressed() {
        keys.put(Keys.RIGHT, true);
    }

    public void upPressed() {
        keys.put(Keys.UP, true);
    }

    public void downPressed() {
        keys.put(Keys.DOWN, true);
    }

    public void firePressed() {
        keys.put(Keys.FIRE, true);
    }

    public void leftReleased() {
        keys.put(Keys.LEFT, false);
    }

    public void rightReleased() {
        keys.put(Keys.RIGHT, false);
    }

    public void upReleased() {
        keys.put(Keys.UP, false);
    }

    public void downReleased() {
        keys.put(Keys.DOWN, false);
    }

    public void fireReleased() {
        keys.put(Keys.FIRE, false);
    }

    /** The main update method **/
    public void update(float delta) {
        processInput();
        CollisionUtil.checkCollisionWithBlocks(player.getPosition(), player.getVelocity(), player.getBounds(), delta);
        player.update(delta);
    }

    /** Change Bob's state and parameters based on input controls * */
    private void processInput() {
        if (keys.get(Keys.LEFT)) {
            // left is pressed
            player.setFacing(Player.Facing.LEFT);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = -Player.SPEED;
        } else if (keys.get(Keys.RIGHT)) {
            // left is pressed
            player.setFacing(Player.Facing.RIGHT);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = Player.SPEED;
        } else if (keys.get(Keys.UP)) {
            // left is pressed
            player.setFacing(Player.Facing.UP);
            player.setState(Player.State.WALKING);
            player.getVelocity().y = Player.SPEED;
        } else if (keys.get(Keys.DOWN)) {
            // left is pressed
            player.setFacing(Player.Facing.DOWN);
            player.setState(Player.State.WALKING);
            player.getVelocity().y = -Player.SPEED;
        }

        // need to check if both or none direction are pressed, then Bob is idle
        if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT) && keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
                (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN))) {
            player.setState(Player.State.IDLE);
            player.getVelocity().x = 0;
            player.getVelocity().y = 0;
        }

        if (keys.get(Keys.FIRE)) {
            player.setState(Player.State.FIRE);
            keys.put(Keys.FIRE, false);
        }
    }


}
