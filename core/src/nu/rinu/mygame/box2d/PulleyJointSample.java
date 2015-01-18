package nu.rinu.mygame.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import lombok.Value;
import lombok.experimental.Wither;

public class PulleyJointSample extends Box2dSampleBase {

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 2, 2, 1, 0.1f, 0.1f);

        {
            Body a = createBox(boxDef.withX(1).withY(10).withDensity(10));
            Body b = createBox(boxDef.withX(3).withY(12).withDensity(20));
            setCaption(a, String.format("d:10"));
            setCaption(b, String.format("d:20"));
            createDistanceJoint(a, b);
        }
    }

    private void createDistanceJoint(Body a, Body b) {
        PulleyJointDef jointDef = new PulleyJointDef();

        jointDef.collideConnected = true;
        jointDef.groundAnchorA.set(10, 10);
        jointDef.groundAnchorB.set(20, 10);
        jointDef.localAnchorA.set(-0.5f, -0.5f);
        jointDef.localAnchorB.set(-0.5f, -0.5f);
        jointDef.lengthA = 5;
        jointDef.lengthB = 3;
        jointDef.ratio = 1f;
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        world.createJoint(jointDef);
    }
}
