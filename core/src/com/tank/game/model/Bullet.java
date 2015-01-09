package com.tank.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Bullet {

	public enum State {
		FLYING,
		DEAD
	}

	static final float VELOCITY = 4f;

	public State state = State.FLYING;

	public Vector2 pos = new Vector2();
	Vector2 vel = new Vector2();
	public Rectangle bounds = new Rectangle();

	public Bullet(Vector2 pos, Player.Facing dir) {

		if (dir.equals(Player.Facing.UP)) {
			this.pos.set(pos.x + 0.37f, pos.y + 1.7f);
			vel.set(0, VELOCITY);
		} else if (dir.equals(Player.Facing.DOWN)) {
			this.pos.set(pos.x + 0.37f, pos.y - 0.3f);
			vel.set(0, -VELOCITY);
		} else if (dir.equals(Player.Facing.LEFT)) {
			this.pos.set(pos.x - 0.3f, pos.y + 0.37f);
			vel.set(-VELOCITY, 0);
		} else if (dir.equals(Player.Facing.RIGHT)) {
			this.pos.set(pos.x + 1.7f, pos.y + 0.37f);
			vel.set(VELOCITY, 0);
		}
		bounds.x = pos.x + 0.1f;
		bounds.y = pos.y + 0.1f;
		bounds.width = 0.8f;
		bounds.height = 0.8f;
	}

	public void update(float deltaTime) {
		if (state.equals(State.FLYING)) {
			pos.add(vel.x * deltaTime, vel.y * deltaTime);
			bounds.x = pos.x + 0.1f;
			bounds.y = pos.y + 0.1f;
			/*
			if (checkHit()) {
				state = State.DEAD;
			}
			*/
		}
	}

	Rectangle[] r = {new Rectangle(), new Rectangle(), new Rectangle(), new Rectangle()};

	private boolean checkHit () {
		/*
		fetchCollidableRects();
		for (int i = 0; i < r.length; i++) {
			if (bounds.overlaps(r[i])) {
				return true;
			}
		}

		if (bounds.overlaps(map.bob.bounds)) {
			if (map.bob.state != Bob.DYING) {
				map.bob.state = Bob.DYING;
				map.bob.stateTime = 0;
			}
			return true;
		}

		if (bounds.overlaps(map.cube.bounds)) {
			return true;
		}
		*/

		return false;
	}

	private void fetchCollidableRects () {
		/*
		int p1x = (int)bounds.x;
		int p1y = (int)Math.floor(bounds.y);
		int p2x = (int)(bounds.x + bounds.width);
		int p2y = (int)Math.floor(bounds.y);
		int p3x = (int)(bounds.x + bounds.width);
		int p3y = (int)(bounds.y + bounds.height);
		int p4x = (int)bounds.x;
		int p4y = (int)(bounds.y + bounds.height);

		int[][] tiles = map.tiles;
		int tile1 = tiles[p1x][map.tiles[0].length - 1 - p1y];
		int tile2 = tiles[p2x][map.tiles[0].length - 1 - p2y];
		int tile3 = tiles[p3x][map.tiles[0].length - 1 - p3y];
		int tile4 = tiles[p4x][map.tiles[0].length - 1 - p4y];

		if (tile1 != Map.EMPTY)
			r[0].set(p1x, p1y, 1, 1);
		else
			r[0].set(-1, -1, 0, 0);
		if (tile2 != Map.EMPTY)
			r[1].set(p2x, p2y, 1, 1);
		else
			r[1].set(-1, -1, 0, 0);
		if (tile3 != Map.EMPTY)
			r[2].set(p3x, p3y, 1, 1);
		else
			r[2].set(-1, -1, 0, 0);
		if (tile4 != Map.EMPTY)
			r[3].set(p4x, p4y, 1, 1);
		else
			r[3].set(-1, -1, 0, 0);
			*/
	}
}

