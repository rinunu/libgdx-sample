package nu.rinu.mygame.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class RopeJointSample extends Box2dSampleBase {

    @Override
    protected void setupObjects() {
        CircleDef circleDef = new CircleDef(BodyDef.BodyType.DynamicBody, 0, 5, 0.5f, 1, 0.1f, 0.1f);

        Body last = null;
        for (int x = 0; x < 10; x++) {
            Body a = createCircle(circleDef.withX(x * 2).withY(10));
            if (last != null) {
                createWeldJoint(last, a);
            }
            last = a;
        }
        setCaption(last, "rope");

        last = null;
        for (int x = 0; x < 10; x++) {
            Body a = createCircle(circleDef.withX(x * 2).withY(10));
            if (last != null) {
                createDistanceJoint(last, a);
            }
            last = a;
        }
        setCaption(last, "distance");
    }

    private DistanceJoint createDistanceJoint(Body a, Body b) {
        DistanceJointDef jointDef = new DistanceJointDef();

//        jointDef.collideConnected = true;
        jointDef.localAnchorA.set(0, 0);
        jointDef.localAnchorB.set(0, 0);
        jointDef.length = 1;
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        return (DistanceJoint) world.createJoint(jointDef);
    }

    private RopeJoint createWeldJoint(Body a, Body b) {
        RopeJointDef jointDef = new RopeJointDef();

//        jointDef.collideConnected = true;
        jointDef.localAnchorA.set(0, 0);
        jointDef.localAnchorB.set(0, 0);
        jointDef.maxLength = 1;
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        return (RopeJoint) world.createJoint(jointDef);
    }
}


