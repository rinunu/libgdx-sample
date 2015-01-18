package nu.rinu.mygame.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import lombok.Value;
import lombok.experimental.Wither;

public class PrismaticJointSample extends Box2dSampleBase {

    @Wither
    @Value
    private class JointOptions {
        public float referenceAngle;
        public boolean enableLimit;
        public float lowerTranslation;
        public float upperTranslation;
        public boolean enableMotor;
        public float maxMotorForce;
        public float motorSpeed;
    }

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 2, 2, 1, 0.1f, 0.1f);
        JointOptions jointOptions = new JointOptions(0, false, 0, 0, false, 0, 0);

        {
            Body a = createBox(boxDef.withX(1).withY(10));
            Body b = createBox(boxDef.withX(3).withY(12));
            setCaption(b, String.format("no limit"));
            createJoint(a, b, jointOptions);
        }

        {
            Body a = createBox(boxDef.withX(1).withY(10));
            Body b = createBox(boxDef.withX(3).withY(12));
            setCaption(b, String.format("limit"));
            createJoint(a, b, jointOptions
                .withEnableLimit(true)
                .withLowerTranslation(5)
                .withUpperTranslation(15));
        }

        {
            Body a = createBox(boxDef.withX(1).withY(10));
            Body b = createBox(boxDef.withX(3).withY(12));
            setCaption(b, String.format("motor"));
            createJoint(a, b, jointOptions
                    .withLowerTranslation(10f)
                    .withUpperTranslation(20f)
                    .withEnableLimit(true)
                    .withMaxMotorForce(1)
                    .withMotorSpeed(1f)
                    .withEnableMotor(true)
            );
        }
    }

    private void createJoint(Body a, Body b, JointOptions options) {
        PrismaticJointDef jointDef = new PrismaticJointDef();
        jointDef.referenceAngle = options.referenceAngle;
        jointDef.enableLimit = options.enableLimit;
        jointDef.lowerTranslation = options.lowerTranslation;
        jointDef.upperTranslation = options.upperTranslation;
        jointDef.enableMotor = options.enableMotor;
        jointDef.maxMotorForce = options.maxMotorForce;
        jointDef.motorSpeed = options.motorSpeed;

        jointDef.collideConnected = true;
        jointDef.localAnchorA.set(-0.5f, -0.5f);
        jointDef.localAnchorB.set(-0.5f, -0.5f);
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        world.createJoint(jointDef);
    }
}
