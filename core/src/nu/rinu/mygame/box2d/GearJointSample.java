package nu.rinu.mygame.box2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import lombok.Value;
import lombok.experimental.Wither;

public class GearJointSample extends Box2dSampleBase {

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

        {
            Body a = createBox(boxDef.withX(10).withY(10));
            setCaption(a, String.format("rev"));
            RevoluteJoint jointA = createRevoluteJointWithGround(a);

            Body b = createBox(boxDef.withX(12).withY(12));
            setCaption(b, String.format("rev"));
            RevoluteJoint jointB = createRevoluteJointWithGround(b);
            createGearJoint(a, jointA, b, jointB, 1);

            Body c = createBox(boxDef.withX(15).withY(10));
            setCaption(c, String.format("rev"));
            RevoluteJoint jointC = createRevoluteJointWithGround(c);
            createGearJoint(b, jointB, c, jointC, 1);

            Body d = createBox(boxDef.withX(10).withY(20).withWidth(10));
            setCaption(d, String.format("pr"));
            PrismaticJoint jointD = createPrismaticJointWithGround(d);
            createGearJoint(a, jointA, d, jointD, 1);

            Body e = createBox(boxDef.withX(30).withY(25).withWidth(10));
            setCaption(e, String.format("pr"));
            PrismaticJoint jointE = createPrismaticJointWithGround(e);
            createGearJoint(d, jointD, e, jointE, 1);
        }
    }

    private RevoluteJoint createRevoluteJointWithGround(Body a) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.localAnchorA.set(a.getPosition());
        jointDef.localAnchorB.set(0, 0);
        jointDef.bodyA = groundBody;
        jointDef.bodyB = a;
        return (RevoluteJoint) world.createJoint(jointDef);
    }

    private PrismaticJoint createPrismaticJointWithGround(Body a) {
        PrismaticJointDef jointDef = new PrismaticJointDef();
        jointDef.localAnchorA.set(a.getPosition());
        jointDef.localAnchorB.set(0, 0);
        jointDef.bodyA = groundBody;
        jointDef.bodyB = a;
        return (PrismaticJoint) world.createJoint(jointDef);
    }

    private GearJoint createGearJoint(Body a, Joint joint1, Body b, Joint joint2, float ratio) {
        GearJointDef jointDef = new GearJointDef();

        jointDef.joint1 = joint1;
        jointDef.joint2 = joint2;
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        jointDef.ratio = ratio;
        return (GearJoint) world.createJoint(jointDef);
    }
}


