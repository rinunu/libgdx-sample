package nu.rinu.mygame.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Wither;
import nu.rinu.mygame.Menu;
import nu.rinu.mygame.MyGame2;

/**
 * http://code.google.com/p/libgdx-backend-android-livewallpaper/source/browse/gdx-backend-android-livewallpaper-example/src/com/badlogic/gdx/tests/box2d/Box2DTest.java?r=ba02aaf34a8ca07daa0c30493bab993067c652f9
 */
public class Box2dSampleBase extends BaseScreen {
    @Setter
    private MyGame2 game;

    protected OrthographicCamera camera;

    protected Box2DDebugRenderer box2dDebugrenderer;

    SpriteBatch hudBatch;
    ShapeRenderer shapeRenderer;

    BitmapFont font;

    private Color frameColor = new Color(1f, 1f, 1f, 0.5f);

    protected World world;

    protected Body groundBody;

    protected MouseJoint mouseJoint = null;

    protected Body hitBody = null;

    private Array<Body> bodies = new Array<Body>();

    protected Vector2 worldSize = new Vector2(48, 32);

    @Override
    public void show() {
        // TODO 解放処理

        hudBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer(100);
        font = new BitmapFont();

        camera = new OrthographicCamera(worldSize.x, worldSize.y);
        camera.position.set(worldSize.x / 2, worldSize.y / 2, 0);

        box2dDebugrenderer = new Box2DDebugRenderer();

        setupBox2d();


        Gdx.input.setInputProcessor(inputProcessor);
    }

    /**
     * Box2D のオブジェクトを配置します
     */
    private void setupBox2d() {
        world = new World(new Vector2(0, -10), true);
        groundBody = setupGround();
        setupObjects();
    }

    /**
     * Box2D の世界に存在するオブジェクトを生成します
     */
    protected void setupObjects() {
        for (int i = 0; i < 200; i++) {
            createBox(new Vector2(
                    MathUtils.random(-24, 24),
                    MathUtils.random(10, 110)), 2, 2
            );
        }
    }

    @Value
    private class Caption {
        private Body body;
        private String caption;
    }

    @Value
    private class Frame {
        private Rectangle rect;
        private String caption;
    }

    private Array<Caption> captions = new Array<Caption>();

    private Array<Frame> frames = new Array<Frame>();

    /**
     * 対象の Body に説明文を表示します
     */
    protected void setCaption(Body body, String caption) {
        captions.add(new Caption(body, caption));
    }

    /**
     * 説明用の枠を追加します
     */
    protected void addFrame(Rectangle rect, String caption) {
        frames.add(new Frame(rect, caption));
    }

    /**
     * 地面をセットアップします
     */
    protected Body setupGround() {
        Body a = createStaticBox(new Vector2(0, 0), worldSize.x * 2, 3);
        createStaticBox(new Vector2(0, worldSize.y), worldSize.x * 2, 3);
        createStaticBox(new Vector2(0, 0), 1, worldSize.y * 2);
        createStaticBox(new Vector2(worldSize.x, 0), 1, worldSize.y * 2);
        return a;
    }

    @Value
    @Wither
    public class BoxDef {
        private final BodyDef.BodyType bodyType;
        private final float x;
        private final float y;
        private final float height;
        private final float width;
        private final float density;
        private final float friction;
        private final float restitution;

        public Vector2 getPos() {
            return new Vector2(x, y);
        }
    }

    protected Body createBox(BoxDef boxDef) {
        PolygonShape boxPoly = new PolygonShape();
        try {
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = boxDef.getBodyType();
            boxBodyDef.position.set(boxDef.getPos());

            boxBodyDef.angularDamping = 0.1f;
            boxBodyDef.linearDamping = 0.1f;

            Body body = world.createBody(boxBodyDef);

            boxPoly.setAsBox(boxDef.getWidth() / 2, boxDef.getHeight() / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = boxPoly;
            fixtureDef.friction = boxDef.getFriction();
            fixtureDef.restitution = boxDef.getRestitution();
            fixtureDef.density = boxDef.getDensity();

            body.createFixture(fixtureDef);
            return body;
        } finally {
            boxPoly.dispose();
        }
    }

    @Value
    @Wither
    public class CircleDef {
        private final BodyDef.BodyType bodyType;
        private final float x;
        private final float y;
        private final float radius;
        private final float density;
        private final float friction;
        private final float restitution;

        public Vector2 getPos() {
            return new Vector2(x, y);
        }
    }

    protected Body createCircle(CircleDef circleDef) {
        CircleShape shape = new CircleShape();
        try {
            shape.setRadius(circleDef.getRadius());

            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = circleDef.getBodyType();
            boxBodyDef.position.set(circleDef.getPos());

            boxBodyDef.angularDamping = 0.1f;
            boxBodyDef.linearDamping = 0.1f;

            Body body = world.createBody(boxBodyDef);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.friction = circleDef.getFriction();
            fixtureDef.restitution = circleDef.getRestitution();
            fixtureDef.density = circleDef.getDensity();

            body.createFixture(fixtureDef);
            return body;
        } finally {
            shape.dispose();
        }
    }

    /**
     */
    protected Body createStaticBox(Vector2 pos, float width, float height) {
        PolygonShape boxPoly = new PolygonShape();
        try {
            boxPoly.setAsBox(width / 2, height / 2);
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyDef.BodyType.StaticBody;
            boxBodyDef.position.set(pos);
            Body body = world.createBody(boxBodyDef);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 10;
            fixtureDef.friction = 1f;
            fixtureDef.restitution = 0f;
            fixtureDef.shape = boxPoly;

            body.createFixture(fixtureDef);
            return body;
        } finally {
            boxPoly.dispose();
        }
    }

    /**
     */
    protected Body createBox(Vector2 pos, float width, float height) {
        PolygonShape boxPoly = new PolygonShape();
        try {
            boxPoly.setAsBox(width / 2, height / 2);
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
            boxBodyDef.position.set(pos);
            Body body = world.createBody(boxBodyDef);
            body.createFixture(boxPoly, 10);
            bodies.add(body);
            return body;
        } finally {
            boxPoly.dispose();
        }
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    private void update() {
        camera.update();
        world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
    }

    Matrix4 screenMat = new Matrix4();

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        box2dDebugrenderer.render(world, camera.combined);

        drawHub();
    }

    private Vector3 v3 = new Vector3();
    private Vector3 v3_2 = new Vector3();

    private void drawHub() {
        hudBatch.begin();

        font.setColor(1f, 1f, 1f, 1f);
        font.draw(hudBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20);

        font.setColor(1f, 1f, 1f, 1f);
        for (Caption caption : captions) {
            Vector2 pos = caption.getBody().getPosition();
            camera.project(v3.set(pos.x, pos.y, 0));
            font.draw(hudBatch, caption.getCaption(), v3.x, v3.y);
        }

        font.setColor(frameColor);
        for (Frame frame : frames) {
            Rectangle r = frame.rect;
            camera.project(v3.set(r.x, r.y + r.height, 0));
            font.draw(hudBatch, frame.getCaption(), v3.x + 2, v3.y - 2);
        }

        drawGrid();

        hudBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Frame frame : frames) {
            Rectangle r = frame.rect;
            camera.project(v3.set(r.x, r.y, 0));
            camera.project(v3_2.set(r.x + r.width, r.y + r.height, 0));
            shapeRenderer.setColor(frameColor);
            shapeRenderer.rect(v3.x, v3.y, v3_2.x - v3.x, v3_2.y - v3.y);
        }
        shapeRenderer.end();
    }

    private void drawGrid() {
        font.setColor(0.5f, 0.5f, 0.5f, 1f);
        for (int x = 0; x < 100; x += 5) {
            camera.project(v3.set(x, 1, 0));
            font.draw(hudBatch, x + "m", v3.x, v3.y);
        }
    }

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(v3.x, v3.y)) {
                hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    private InputProcessor inputProcessor = new InputAdapter() {

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            camera.unproject(v3.set(x, y, 0));
            hitBody = null;
            world.QueryAABB(callback, v3.x - 0.0001f, v3.y - 0.0001f, v3.x + 0.0001f, v3.y + 0.0001f);

            if (hitBody == groundBody) hitBody = null;

            if (hitBody != null && hitBody.getType() == BodyDef.BodyType.KinematicBody) {
                return false;
            }

            if (hitBody != null) {
                MouseJointDef def = new MouseJointDef();
                def.bodyA = groundBody;
                def.bodyB = hitBody;
                def.collideConnected = true;
                def.target.set(v3.x, v3.y);
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
                camera.unproject(v3.set(x, y, 0));
                mouseJoint.setTarget(target.set(v3.x, v3.y));
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

        @Override
        public boolean keyDown(int keycode) {
            game.setScreen(new Menu(game));
            return true;
        }
    };
}
