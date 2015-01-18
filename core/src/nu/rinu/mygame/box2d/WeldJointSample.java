package nu.rinu.mygame.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;

public class WeldJointSample extends Box2dSampleBase {

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 1, 1, 1, 0.1f, 0.1f);

        Body last = null;
        for (int x = 0; x < 10; x++) {
            Body a = createBox(boxDef.withX(x * 2).withY(10));
            if (last != null) {
                createWeldJoint(last, a);
            }
            last = a;
        }
    }

    private WeldJoint createWeldJoint(Body a, Body b) {
        WeldJointDef jointDef = new WeldJointDef();

        jointDef.collideConnected = true;
        jointDef.localAnchorA.set(-0.5f, 0);
        jointDef.localAnchorB.set(0.5f, 0);
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        return (WeldJoint) world.createJoint(jointDef);
    }
}


