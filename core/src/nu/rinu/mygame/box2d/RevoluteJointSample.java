package nu.rinu.mygame.box2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import lombok.Value;
import lombok.experimental.Wither;

public class RevoluteJointSample extends Box2dSampleBase {

    @Wither
    @Value
    private class JointOptions {
        private boolean collideConnected;

        private float referenceAngle;
        private boolean enableLimit;
        private float lowerAngle;
        private float upperAngle;
        private boolean enableMotor;
        private float motorSpeed;
        private float maxMotorTorque;
    }

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 2, 2, 100, 0.1f, 0.1f);
        BoxDef staticDef = new BoxDef(BodyDef.BodyType.StaticBody, 10, 10, 1, 1, 10, 0.1f, 0.1f);
        JointOptions jointOptions = new JointOptions(true, 0, false, 0, 0, false, 0, 0);

        jointOptions.withReferenceAngle(1);
        {
            Body a = createBox(boxDef.withX(1).withY(10));
            Body b = createBox(boxDef.withX(3).withY(12));
            setCaption(b, String.format("limit"));
            createJoint(a, b,
                jointOptions.withEnableLimit(true)
                    .withLowerAngle(MathUtils.degRad * (180 - 40))
                    .withUpperAngle(MathUtils.degRad * (180 + 40)));
        }

        {
            Body a = createBox(boxDef.withX(1).withY(10));
            Body b = createBox(boxDef.withX(3).withY(12));
            setCaption(b, String.format("no limit"));
            createJoint(a, b, jointOptions);
        }

        {
            Body a = createBox(boxDef.withX(1).withY(10));
            Body b = createBox(boxDef.withX(3).withY(12));
            setCaption(b, String.format("motor"));
            createJoint(a, b, jointOptions
                    .withCollideConnected(false)
                    .withEnableMotor(true)
                    .withMotorSpeed(10f)
                    .withMaxMotorTorque(10f)
            );
        }

//        float y = 0;
//        float height = 8;
//        float left = 3;
//        float bottom = 2;
//        for (float dam : Arrays.asList(1)) {
//            float x = 0;
//            for (float fr : Arrays.asList(0f)) {
//                Body a = createBox(boxDef.withX(left + x * 10 + 1).withY(bottom + y * height + 5f));
//                Body b = createBox(boxDef.withX(left + x * 10 + 1).withY(bottom + y * height + 5f));
//
//                setCaption(b, String.format("f:%.1f", fr));
//                createDistanceJoint(a, b, jointOptions);
//                x++;
//            }
//
//            y++;
//        }
    }

    private void createJoint(Body a, Body b, JointOptions options) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
//        jointDef.
//        jointDef.dampingRatio = options.getDampingRatio();
//        jointDef.frequencyHz = options.getFrequencyHz();
//        jointDef.length = options.getLength();

        jointDef.referenceAngle = options.referenceAngle;
        jointDef.enableLimit = options.enableLimit;
        jointDef.lowerAngle = options.lowerAngle;
        jointDef.upperAngle = options.upperAngle;
        jointDef.enableMotor = options.enableMotor;
        jointDef.motorSpeed = options.motorSpeed;
        jointDef.maxMotorTorque = options.maxMotorTorque;

        jointDef.collideConnected = options.collideConnected;
        jointDef.localAnchorA.set(-1f, -1f);
        jointDef.localAnchorB.set(-1f, -1f);
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        world.createJoint(jointDef);
    }
}
