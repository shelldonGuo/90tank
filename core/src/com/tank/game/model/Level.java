package com.tank.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class Level {
    private int width = 26;
    private int height = 26;
    private Block[][] blocks;

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Block[][] getBlocks() {
        return blocks;
    }
    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }
    public Level() {
        loadLevel(0);
    }
    public Block get(int x, int y) {
        return blocks[x][y];
    }

    private void loadLevel(int nextLevel) {

        blocks = new Block[width][height];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                blocks[col][row] = null;
            }
        }

        String fileName = "levels/" + nextLevel;
        FileHandle file = Gdx.files.internal(fileName);
        if (!file.exists()) {
            throw new IllegalArgumentException("file not exist!");
        }
        String map = file.readString();
        String[] rows = map.split("\n");
        for (int i = rows.length - 1; i >= 0; --i) {
            char[] row = rows[i].toCharArray();
            int y = rows.length - 1 - i;
            for (int x = 0; x < row.length; ++x) {
                char c = row[x];
                switch (c) {
                    case '#':
                    case '@':
                    case '~':
                    case '%':
                    case '-':
                        blocks[x][y] = (new Block(new Vector2(x, y)));
                        break;
                    default:
                        blocks[x][y] = null;
                        break;
                }
            }
        }
    }
}
