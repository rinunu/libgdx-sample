package nu.rinu.mygame.box2d;

import java.util.Arrays;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import lombok.Value;
import lombok.experimental.Wither;

public class BasicSample extends Box2dSampleBase {

    // 単位について
    // メートル
    // キログラム
    // 秒

    @Override
    protected void setupObjects() {
        BoxDef wallDef = new BoxDef(BodyDef.BodyType.StaticBody, 0f, 0f, 1f, 80f, 20f, 1f, 0f);
        createBox(wallDef.withY(10));
        createBox(wallDef.withY(20));

        BoxDef boxDef = new BoxDef(BodyDef.BodyType.DynamicBody, 0f, 10f, 1f, 1f, 20f, 0.1f, 0.2f);

        float x = 0;
        float interval = 4;
        for (float res : Arrays.asList(0f, 0.1f, 0.5f, 1f)) {
            setCaption(
                createBox(boxDef.withX(x++ * interval).withY(5).withRestitution(res)),
                "res:" + res
            );
        }

        x = 0;
        for (float fri : Arrays.asList(0f, 0.1f, 0.5f, 1f)) {
            setCaption(
                createBox(boxDef.withX(x++ * interval).withY(15).withFriction(fri)),
                "fri:" + fri
            );
        }

        x = 0;
        for (float den : Arrays.asList(0f, 10f, 20f, 100f)) {
            setCaption(
                createBox(boxDef.withX(x++ * interval).withY(25).withDensity(den)),
                "den:" + den
            );
        }

    }
}
