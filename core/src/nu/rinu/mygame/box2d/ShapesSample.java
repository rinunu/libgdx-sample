package nu.rinu.mygame.box2d;

import java.util.Arrays;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class ShapesSample extends Box2dSampleBase {

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0f, 10f, 2f, 2f, 20f, 0.1f, 0.2f);
        CircleDef circleDef = new CircleDef(BodyDef.BodyType.DynamicBody, 0f, 0f, 2f, 20f, 1f, 0f);

        setCaption(createCircle(circleDef.withX(10).withY(10)), "circle");

        setCaption(createBox(boxDef.withX(10).withY(10)), "polygon");

        setCaption(createBody(new Vector2(10, 10), createPolygonShape()), "polygon");

        setCaption(createBody(new Vector2(10, 20), createEdge()), "edge");

        setCaption(createBody(new Vector2(10, 20), createChain()), "chain");
    }

    private Body createBody(Vector2 pos, Shape shape) {
        try {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(pos);

            bodyDef.angularDamping = 0.1f;
            bodyDef.linearDamping = 0.1f;

            Body body = world.createBody(bodyDef);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 20;
            fixtureDef.friction = 0.1f;
            fixtureDef.restitution = 0.2f;

            body.createFixture(fixtureDef);
            return body;
        } finally {
            shape.dispose();
        }
    }

    private Shape createPolygonShape() {
        Vector2[] vertices = {
            new Vector2(-1.5f, -0.5f),
            new Vector2(0.5f, -1f),
            new Vector2(1.0f, -0.5f),
            new Vector2(1.0f, 1.5f),
            new Vector2(0.5f, 1.5f),
            new Vector2(-0.5f, 1.3f),
        };

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        return shape;
    }

    private Shape createEdge() {
        EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(0, 0), new Vector2(10, 0));
        return shape;
    }

    private Shape createChain() {
        Vector2[] vs = {
            new Vector2(2f, 0.0f),
            new Vector2(1.0f, 1f),
            new Vector2(0.0f, 0.0f),
            new Vector2(-2f, 1f) };

        ChainShape shape = new ChainShape();
        shape.createChain(vs);
        return shape;
    }
}
