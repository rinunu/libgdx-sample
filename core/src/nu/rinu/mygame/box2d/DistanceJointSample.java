package nu.rinu.mygame.box2d;

import java.util.Arrays;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import lombok.Value;
import lombok.experimental.Wither;

public class DistanceJointSample extends Box2dSampleBase {

    @Value
    @Wither
    public class JointOptions {
        private float dampingRatio;
        private float frequencyHz;
        private float length;
    }

    @Override
    protected void setupObjects() {
        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0, 5, 1, 1, 100, 0.1f, 0.1f);
        BoxDef staticDef = new BoxDef(BodyDef.BodyType.StaticBody, 10, 10, 1, 1, 10, 0.1f, 0.1f);
        JointOptions jointOptions = new JointOptions(10, 10, 3);

        float y = 0;
        float height = 8;
        float left = 3;
        float bottom = 2;
        for (float dam : Arrays.asList(1, 2, 3)) {
            float x = 0;
            for (float fr : Arrays.asList(0f, 0.1f, 0.5f, 1f, 20f)) {
                Body a = createBox(staticDef.withX(left + x * 10).withY(bottom + y * height + 5f));
                Body b = createBox(boxDef.withX(left + x * 10 + 1).withY(bottom + y * height + 5f));
//                setCaption(b, String.format("d:%.1f", dam));
                setCaption(b, String.format("f:%.1f", fr));
                createDistanceJoint(a, b, jointOptions.withDampingRatio(dam).withFrequencyHz(fr));
                x++;
            }

//            addFrame(new Rectangle(2, bottom + y * height + 0.1f, 30, height - 0.2f), String.format("frequencyHz:%.1f", fr));
            addFrame(new Rectangle(2, bottom + y * height + 0.1f, 45, height - 0.2f), String.format("damping ratio:%.1f", dam));
            y++;
        }
    }

    private void createDistanceJoint(Body a, Body b, JointOptions options) {
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.dampingRatio = options.getDampingRatio();
        jointDef.frequencyHz = options.getFrequencyHz();
        jointDef.length = options.getLength();
        jointDef.bodyA = a;
        jointDef.bodyB = b;
        world.createJoint(jointDef);
    }
}
