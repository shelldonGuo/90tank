package com.tank.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TankGame extends ApplicationAdapter {

	int len = 8;
	SpriteBatch batch;
	Texture img;
	TextureRegion bricks;
	Sprite player;
	Sprite brick1;
	Sprite brick2;
	Sprite brick3;
	Sprite brick4;

	Sprite tileEmpty, tileBrick, tileWhiteBrick, tileSteel, tileGrass, tileWater, tileWater1, tileWater2, tileFroze;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("sprites.gif");
		bricks = new TextureRegion(img, 56, 64, 8, 8);
		player = new Sprite(img, 0, 0, 16, 16);
		player.scale(2);

		brick1 = new Sprite(bricks, 0, 0, 8, 8);
		brick1.setPosition(30, 30);
		brick1.scale(2);
		brick2 = new Sprite(bricks, 8, 0, 8, 8);
		brick2.setPosition(100, 100);
		brick2.scale(2);
		brick3 = new Sprite(bricks, 8, 8, 8, 8);
		brick3.setPosition(50, 50);
		brick4 = new Sprite(bricks, 0, 8, 8, 8);
		brick4.setPosition(60, 60);

		initTile();
	}

	private void initTile() {
		int scale = 3;
		tileEmpty = new Sprite(img, 48, 64, len, len);
		tileBrick = new Sprite(img, 48, 64, len, len);
		tileBrick.setPosition(100, 100);
		tileBrick.setScale(scale);
		tileSteel = new Sprite(img, 48, 72, len, len);
		tileSteel.setPosition(100 + len * scale, 100);
		tileSteel.setScale(scale);
		tileWhiteBrick = new Sprite(img, 56, 64, len, len);
		tileGrass = new Sprite(img, 56, 72, len, len);
		tileWater = new Sprite(img, 64, 64, len, len);
		tileWater1 = tileWater;
		tileWater2 = new Sprite(img, 72, 64, len, len);
		tileFroze = new Sprite(img, 64, 72, len, len);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		tileBrick.draw(batch);
		tileSteel.draw(batch);
		/*
		batch.draw(img, 0, 0);
		player.draw(batch);
		brick1.draw(batch);
		brick2.draw(batch);
		brick3.draw(batch);
		brick4.draw(batch);
		*/
		batch.end();
	}
}
