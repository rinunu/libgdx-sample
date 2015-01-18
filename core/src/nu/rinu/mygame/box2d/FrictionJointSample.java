package nu.rinu.mygame.box2d;

import java.util.Arrays;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;

public class FrictionJointSample extends Box2dSampleBase {

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 1, 1, 1, 0.1f, 0.1f);

        for (int i = 0; i < 10; i++) {
            Body a = createBox(boxDef.withX(3 * i).withY(10));
            Body b = createBox(boxDef.withX(3 * i).withY(10));
            createFrictionJoint(a, b, i, i);
            setCaption(a, String.format("%d", i));
            setCaption(b, String.format("%d", i));
        }
    }

    private void createFrictionJoint(Body a, Body b, float maxForce, float maxTorque) {
        FrictionJointDef jointDef = new FrictionJointDef();

        jointDef.collideConnected = true;
        jointDef.localAnchorA.set(0, 0);
        jointDef.localAnchorB.set(0, 0);
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        jointDef.maxForce = maxForce;
        jointDef.maxTorque = maxTorque;
        world.createJoint(jointDef);
    }
}
