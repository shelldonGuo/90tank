package com.tank.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.tank.game.model.Block;
import com.tank.game.model.Bullet;
import com.tank.game.model.Player;
import com.tank.game.model.World;

public class WorldRender {
    public static final float CAM_WIDTH = 35f;
    public static final float CAM_HEIGHT = 26f;
    private static final float RUNNING_FRAME_DURATION = 0.06f;

    public static World world;
    Bullet bullet;
    private OrthographicCamera cam;
    private TextureRegion blockTexture;
    private TextureRegion playerTexture;
    private TextureRegion playerGreenTexture;
    private TextureRegion bulletTexture;
    private SpriteBatch spriteBatch = new SpriteBatch();
    int width;
    int height;
    float ppuX = 1;
    float ppuY = 1;

    Animation animation;
    Animation animBullet;
    TextureRegion[] walksFrame;
    private ImageButton button;
    private TextureRegion buttonUp;
    private TextureRegion buttonDown;
    private TextureRegion buttonLeft;
    private TextureRegion buttonRight;
    private TextureRegion buttonFire;
    private Sound soundWalk;

    boolean debug = false;

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    public WorldRender(World world, boolean debug) {
        this.world = world;
        this.debug = debug;
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT);
        cam.position.set(CAM_WIDTH / 2f, CAM_HEIGHT / 2f, 0);
        cam.update();

        loadTexture();
        loadSound();
    }

    private void loadSound(){
        soundWalk = Gdx.audio.newSound(Gdx.files.internal("sounds/background.ogg"));
    }

    public void setSize(int w, int h) {
        /*
        width = w;
        height = h;
        ppuX = width / CAM_WIDTH;
        ppuY = height / CAM_HEIGHT;
        */
    }

    public void render() {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        drawBlocks();
        drawPlayer();
        drawButton();
        drawBullet();
        spriteBatch.end();

        drawCollisionBlocks();
        if (debug) {
            debugRender();
        }
    }

    private void loadTexture() {
        Texture texture = new Texture(Gdx.files.internal("sprites.gif"));
        blockTexture = new TextureRegion(texture, 56, 64, 8, 8);
        playerTexture = new TextureRegion(texture, 0, 0, 14, 14);
        playerGreenTexture = new TextureRegion(texture, 16, 0, 14, 14);
        bulletTexture = new TextureRegion(texture, 72, 72, 8, 8);

        walksFrame = new TextureRegion[30];
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0) {
                walksFrame[i] = playerTexture;
            } else {
                walksFrame[i] = playerGreenTexture;
            }
        }
        animation = new Animation(RUNNING_FRAME_DURATION, walksFrame);

        Texture tex = new Texture(Gdx.files.internal("controls.png"));
        TextureRegion[][] tmp = TextureRegion.split(tex, 64, 64);
        buttonLeft = tmp[0][0];
        buttonRight = tmp[0][1];
        buttonUp = tmp[0][2];
        buttonDown = TextureRegion.split(tex, 64, 64)[0][2];
        buttonDown.flip(false, true);

        buttonFire = tmp[0][3];

    }

    /*
    private void initButton() {
        Texture tex = new Texture(Gdx.files.internal("button.jpg"));
        TextureRegion[][] tmp = TextureRegion.split(tex, 50, 50);

        TextureRegion buttonUp = tmp[0][0];
        TextureRegion buttonDown = tmp[0][1];

        TextureRegionDrawable up = new TextureRegionDrawable(buttonUp);
        TextureRegionDrawable down = new TextureRegionDrawable(buttonDown);

        button = new ImageButton(up, down);

        button.setPosition(10, 10);
        button.getImage().setScale(0.1f);
    }
    */

    private void drawPlayer() {
        Player player = world.getPlayer();
        TextureRegion currentFrame = playerTexture;
        if (player.getState().equals(Player.State.WALKING)) {
            currentFrame = animation.getKeyFrame(player.getStateTime(), true);
            soundWalk.play();
        }
        switch (player.getFacing()) {
            case UP:
                spriteBatch.draw(currentFrame,
                        player.getPosition().x * ppuX,
                        player.getPosition().y * ppuY,
                        Player.SIZE * ppuX,
                        Player.SIZE * ppuY);
                break;
            case DOWN:
                spriteBatch.draw(currentFrame,
                        player.getPosition().x * ppuX,
                        player.getPosition().y * ppuY,
                        Player.SIZE / 2f, Player.SIZE / 2f,
                        Player.SIZE * ppuX,
                        Player.SIZE * ppuY,
                        1f, 1f, 180f);
                break;
            case LEFT:
                spriteBatch.draw(currentFrame,
                        player.getPosition().x * ppuX,
                        player.getPosition().y * ppuY,
                        Player.SIZE / 2f, Player.SIZE / 2f,
                        Player.SIZE * ppuX,
                        Player.SIZE * ppuY,
                        1f, 1f, 90f);
                break;
            case RIGHT:
                spriteBatch.draw(currentFrame,
                        player.getPosition().x * ppuX,
                        player.getPosition().y * ppuY,
                        Player.SIZE / 2f, Player.SIZE / 2f,
                        Player.SIZE * ppuX,
                        Player.SIZE * ppuY,
                        1f, 1f, 270f);
                break;
            default:
                break;
        }
    }

    private void drawBlocks() {
        for (Block block : world.getDrawableBlocks((int) CAM_WIDTH, (int) CAM_HEIGHT)) {
            spriteBatch.draw(blockTexture,
                    block.getPosition().x * ppuX,
                    block.getPosition().y * ppuY,
                    Block.SIZE * ppuX,
                    Block.SIZE * ppuY);
        }
    }

    private void drawButton() {
        spriteBatch.draw(buttonLeft, 0, 3, 3f, 3f);
        spriteBatch.draw(buttonRight, 6, 3, 3f, 3f);
        spriteBatch.draw(buttonUp, 3, 6, 3f, 3f);
        spriteBatch.draw(buttonDown, 3, 0, 3f, 3f);
        spriteBatch.draw(buttonFire, CAM_WIDTH - 3f, 0, 3f, 3f);
    }

    private void drawBullet() {
        Player player = world.getPlayer();
        if (player.getState().equals(Player.State.FIRE)) {
            bullet = new Bullet(player.getPosition(), player.getFacing());
        }
        if (bullet != null && bullet.state == Bullet.State.FLYING) {
            spriteBatch.draw(bulletTexture,
                    bullet.pos.x,
                    bullet.pos.y,
                    bullet.bounds.width,
                    bullet.bounds.height);
            bullet.update(0.06f);
        }
    }

    private void debugRender() {
        // render blocks
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        for (Block block : world.getDrawableBlocks((int) CAM_WIDTH, (int) CAM_HEIGHT)) {
            Rectangle rect = block.getBounds();
            float x1 = block.getPosition().x;
            float y1 = block.getPosition().y;
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(x1, y1, rect.width, rect.height);
        }

        Player player = world.getPlayer();
        Rectangle rect = player.getBounds();
        float x1 = player.getPosition().x;
        float y1 = player.getPosition().y;
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(x1, y1, rect.width, rect.height);

        debugRenderer.end();
    }

    private void drawCollisionBlocks() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Filled);
        debugRenderer.setColor(new Color(1, 1, 1, 1));
        for (Rectangle rect : world.getCollisionRects()) {
            debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        debugRenderer.end();
    }

    public void dispose() {
        soundWalk.dispose();
    }
}
