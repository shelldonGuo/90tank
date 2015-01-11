package com.tank.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tank.game.model.Block;
import com.tank.game.model.World;
import com.tank.game.view.WorldRender;

public class CollisionUtil {
    private static Array<Block> collidable = new Array<Block>();

    private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    /** Collision checking * */
    public static void checkCollisionWithBlocks(Vector2 position, Vector2 velocity, Rectangle bounds, World world, float delta) {
        boolean collision = false;
        // scale velocity to frame units
        velocity.scl(delta);
        // Obtain the rectangle from the pool instead of instantiating it
        Rectangle playerRect = rectPool.obtain();
        // set the rectangle to bob's bounding box
        playerRect.set(bounds);
        // we first check the movement on the horizontal X axis
        int startX, endX;
        int startY = (int) bounds.y;
        int endY = (int) (bounds.y + bounds.height);
        // if Bob is heading left then we check if he collides with the block on his left
        // we check the block on his right otherwise
        if (velocity.x < 0) {
            startX = endX = (int) Math.floor(bounds.x + velocity.x);
        } else {
            startX = endX = (int) Math.floor(bounds.x + bounds.width + velocity.x);
        }
        // get the block(s) bob can collide with
        populateCollidableBlocks(world, startX, startY, endX, endY);
        // simulate bob's movement on the X
        playerRect.x += velocity.x;
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
                    velocity.x = 0;
                    world.getCollisionRects().add(block.getBounds());
                    break;
                }
            }
        }
        // reset the x position of the collision box
        playerRect.x = position.x;
        // the same thing but on the vertical Y axis
        startX = (int) bounds.x;
        endX = (int) (bounds.x + bounds.width);
        if (velocity.y < 0) {
            startY = endY = (int) Math.floor(bounds.y + velocity.y);
        } else {
            startY = endY = (int) Math.floor(bounds.y + bounds.height + velocity.y);
        }
        populateCollidableBlocks(world, startX, startY, endX, endY);
        playerRect.y += velocity.y;
        if (playerRect.y < 0 || playerRect.y + playerRect.height > WorldRender.CAM_HEIGHT) {
            collision = true;
        } else {
            for (Block block : collidable) {
                if (block == null) continue;
                if (playerRect.overlaps(block.getBounds())) {
                    collision = true;
                    velocity.y = 0;
                    world.getCollisionRects().add(block.getBounds());
                    break;
                }
            }
        }
        // reset the collision box's position on Y
        playerRect.y = position.y;
        // update Bob's position
        if (!collision) {
            position.add(velocity);
            bounds.x = position.x;
            bounds.y = position.y;
        }
        // un-scale velocity (not in frame time)
        velocity.scl(1 / delta);

        rectPool.free(playerRect);
    }

    /** populate the collidable array with the blocks found in the enclosing coordinates * */
    private static void populateCollidableBlocks(World world, int startX, int startY, int endX, int endY) {
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
