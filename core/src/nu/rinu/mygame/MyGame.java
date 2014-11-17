package nu.rinu.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;

/**
 * http://code.google.com/p/libgdx-backend-android-livewallpaper/source/browse/gdx-backend-android-livewallpaper-example/src/com/badlogic/gdx/tests/box2d/Box2DTest.java?r=ba02aaf34a8ca07daa0c30493bab993067c652f9
 */
public class MyGame extends ApplicationAdapter {
    private Texture texture;

    protected OrthographicCamera camera;

    protected Box2DDebugRenderer box2dDebugrenderer;

    SpriteBatch batch;
    SpriteBatch hudBatch;
    BitmapFont font;

    protected World world;

    protected Body groundBody;

    protected MouseJoint mouseJoint = null;

    protected Body hitBody = null;

    private Array<Sprite> sprites = new Array<Sprite>();
    private Array<Body> bodies = new Array<Body>();

    private Matrix4 tmpMatrix = new Matrix4();

    @Override
    public void create() {
        // TODO 解放処理

        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        texture = new Texture("icon.jpeg");
        font = new BitmapFont();

        camera = new OrthographicCamera(48, 32);
        camera.position.set(0, 15, 0);

        box2dDebugrenderer = new Box2DDebugRenderer();

        setupWorld();

        Gdx.input.setInputProcessor(inputProcessor);
    }

    /**
     * Box2D のオブジェクトを配置します
     */
    private void setupWorld() {
        world = new World(new Vector2(0, -10), true);

        BodyDef bodyDef = new BodyDef();
        groundBody = world.createBody(bodyDef);

        PolygonShape groundPoly = new PolygonShape();
        groundPoly.setAsBox(50, 1);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBody = world.createBody(groundBodyDef);

        groundBody.createFixture(groundPoly, 10);
        groundPoly.dispose();

        for (int i = 0; i < 200; i++) {
            createBox(new Vector2(
                MathUtils.random(-24, 24),
                MathUtils.random(10, 110)));
        }
    }

    private void createBox(Vector2 pos) {
        PolygonShape boxPoly = new PolygonShape();
        try {
            float w = 2;
            float h = 2;
            Sprite sprite = new Sprite(texture);
            sprites.add(sprite);
            sprite.setSize(w, h);
            sprite.setOrigin(w / 2, h / 2);

            boxPoly.setAsBox(w / 2, h / 2);
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
            boxBodyDef.position.set(pos);
            Body body = world.createBody(boxBodyDef);
            body.createFixture(boxPoly, 20);
            body.setUserData(sprite);
            bodies.add(body);
        } finally {
            boxPoly.dispose();
        }
    }

    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        camera.update();
        world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        updateSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        drawSprites();
        batch.end();

        hudBatch.begin();
        font.draw(hudBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20);
        hudBatch.end();

        box2dDebugrenderer.render(world, camera.combined);
    }

    private void updateSprites() {
        for (Body body : bodies) {
            Sprite sprite = (Sprite) body.getUserData();
            sprite.setRotation(body.getAngle() * MathUtils.radDeg);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    private void drawSprites() {
        for (Sprite sprite : sprites) {
            Texture texture = sprite.getTexture();
            batch.draw(sprite.getTexture(), sprite.getX() - sprite.getOriginX(), sprite.getY() - sprite.getOriginY(),
                sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1.0f, 1.0f,
                sprite.getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        }
    }

    Vector3 tmpVector = new Vector3();

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(tmpVector.x, tmpVector.y)) {
                hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    private InputProcessor inputProcessor = new InputAdapter() {

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            camera.unproject(tmpVector.set(x, y, 0));
            hitBody = null;
            world.QueryAABB(callback, tmpVector.x - 0.0001f, tmpVector.y - 0.0001f, tmpVector.x + 0.0001f, tmpVector.y + 0.0001f);

            if (hitBody == groundBody) hitBody = null;

            if (hitBody != null && hitBody.getType() == BodyDef.BodyType.KinematicBody) {
                return false;
            }

            if (hitBody != null) {
                MouseJointDef def = new MouseJointDef();
                def.bodyA = groundBody;
                def.bodyB = hitBody;
                def.collideConnected = true;
                def.target.set(tmpVector.x, tmpVector.y);
                def.maxForce = 1000.0f * hitBody.getMass();

                mouseJoint = (MouseJoint) world.createJoint(def);
                hitBody.setAwake(true);
            }

            return false;
        }

        Vector2 target = new Vector2();

        @Override
        public boolean touchDragged(int x, int y, int pointer) {
            if (mouseJoint != null) {
                camera.unproject(tmpVector.set(x, y, 0));
                mouseJoint.setTarget(target.set(tmpVector.x, tmpVector.y));
            }
            return false;
        }

        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            if (mouseJoint != null) {
                world.destroyJoint(mouseJoint);
                mouseJoint = null;
            }
            return false;
        }
    };
}
