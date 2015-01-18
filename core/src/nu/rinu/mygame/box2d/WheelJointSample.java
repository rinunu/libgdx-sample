package nu.rinu.mygame.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import lombok.Value;
import lombok.experimental.Wither;

public class WheelJointSample extends Box2dSampleBase {

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 1, 1, 1, 0.1f, 0.1f);
        CircleDef circleDef = new CircleDef(BodyDef.BodyType.DynamicBody, 0, 0, 1f, 1, 0.1f, 0.1f);

        {
            Body a = createCircle(circleDef.withX(8).withY(10));
            Body b = createCircle(circleDef.withX(13).withY(10));
            Body c = createBox(boxDef.withX(11).withY(15).withWidth(5));
            createWheelJoint(c, a, new Vector2(-2, -2));
            createWheelJoint(c, b, new Vector2(2, -2));
            setCaption(c, String.format("wheel"));
        }
    }

    private WheelJoint createWheelJoint(Body a, Body b, Vector2 anchorA) {
        WheelJointDef jointDef = new WheelJointDef();

        jointDef.collideConnected = true;
        jointDef.localAnchorA.set(anchorA);
        jointDef.localAnchorB.set(0, 0);
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        jointDef.localAxisA.set(0, 1);
        return (WheelJoint) world.createJoint(jointDef);
    }
}


