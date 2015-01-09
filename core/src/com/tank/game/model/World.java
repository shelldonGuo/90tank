package com.tank.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class World {
    /** The blocks making up the world * */
    Level level = new Level();
    Array<Rectangle> collisionRects = new Array<Rectangle>();

    Player player = new Player(new Vector2(8, 0));

    public Array<Rectangle> getCollisionRects() {
        return collisionRects;
    }

    public Level getLevel() {
        return level;
    }

    public List<Block> getDrawableBlocks(int width, int height) {
        int x = (int)player.getPosition().x - width;
        int y = (int)player.getPosition().y - height;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        int x2 = x + 2 * width;
        int y2 = y + 2 * height;
        if (x2 > level.getWidth()) {
            x2 = level.getWidth() - 1;
        }
        if (y2 > level.getHeight()) {
            y2 = level.getHeight() - 1;
        }

        List<Block> blocks = new ArrayList<Block>();
        for (int col = x; col <= x2; col++) {
            for (int row = y; row <= y2; row++) {
                Block block = level.getBlocks()[col][row];
                if (block != null) {
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    public Player getPlayer() {
        return player;
    }
}
