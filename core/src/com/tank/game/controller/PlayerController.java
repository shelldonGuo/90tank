package com.tank.game.controller;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tank.game.model.Block;
import com.tank.game.model.Player;
import com.tank.game.model.World;
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

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

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
        checkCollisionWithBlocks(delta);
        player.update(delta);
    }

    /** Change Bob's state and parameters based on input controls **/
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

    /** Collision checking * */
    private void checkCollisionWithBlocks(float delta) {
        boolean collision = false;
        // scale velocity to frame units
        player.getVelocity().scl(delta);
        // Obtain the rectangle from the pool instead of instantiating it
        Rectangle playerRect = rectPool.obtain();
        // set the rectangle to bob's bounding box
        playerRect.set(player.getBounds());
        // we first check the movement on the horizontal X axis
        int startX, endX;
        int startY = (int) player.getBounds().y;
        int endY = (int) (player.getBounds().y + player.getBounds().height);
        // if Bob is heading left then we check if he collides with the block on his left
        // we check the block on his right otherwise
        if (player.getVelocity().x < 0) {
            startX = endX = (int) Math.floor(player.getBounds().x + player.getVelocity().x);
        } else {
            startX = endX = (int) Math.floor(player.getBounds().x + player.getBounds().width + player.getVelocity().x);
        }
        // get the block(s) bob can collide with
        populateCollidableBlocks(startX, startY, endX, endY);
        // simulate bob's movement on the X
        playerRect.x += player.getVelocity().x;
        if (playerRect.x < 0 || playerRect.x + playerRect.width > WorldRender.CAM_HEIGHT) {
            collision = true;
        } else {
            // clear collision boxes in world
            world.getCollisionRects().clear();
            // if bob collides, make his horizontal velocity 0
            for (Block block : collidable) {
                if (block == null) continue;
                if (playerRect.overlaps(block.getBounds())) {
                    collision = true;
                    player.getVelocity().x = 0;
                    world.getCollisionRects().add(block.getBounds());
                    break;
                }
            }
        }
        // reset the x position of the collision box
        playerRect.x = player.getPosition().x;
        // the same thing but on the vertical Y axis
        startX = (int) player.getBounds().x;
        endX = (int) (player.getBounds().x + player.getBounds().width);
        if (player.getVelocity().y < 0) {
            startY = endY = (int) Math.floor(player.getBounds().y + player.getVelocity().y);
        } else {
            startY = endY = (int) Math.floor(player.getBounds().y + player.getBounds().height + player.getVelocity().y);
        }
        populateCollidableBlocks(startX, startY, endX, endY);
        playerRect.y += player.getVelocity().y;
        if (playerRect.y < 0 || playerRect.y + playerRect.height > WorldRender.CAM_HEIGHT) {
            collision = true;
        } else {
            for (Block block : collidable) {
                if (block == null) continue;
                if (playerRect.overlaps(block.getBounds())) {
                    collision = true;
                    player.getVelocity().y = 0;
                    world.getCollisionRects().add(block.getBounds());
                    break;
                }
            }
        }
        // reset the collision box's position on Y
        playerRect.y = player.getPosition().y;
        // update Bob's position
        if (!collision) {
            player.getPosition().add(player.getVelocity());
            player.getBounds().x = player.getPosition().x;
            player.getBounds().y = player.getPosition().y;
        }
        // un-scale velocity (not in frame time)
        player.getVelocity().scl(1 / delta);

        rectPool.free(playerRect);
    }

    /** populate the collidable array with the blocks found in the enclosing coordinates * */
    private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
        collidable.clear();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x >= 0 && x < world.getLevel().getWidth() && y >= 0 && y < world.getLevel().getHeight()) {
                    collidable.add(world.getLevel().get(x, y));
                }
            }
        }
    }
}
