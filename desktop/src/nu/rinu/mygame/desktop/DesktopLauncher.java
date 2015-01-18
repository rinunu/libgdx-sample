package nu.rinu.mygame.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nu.rinu.mygame.box2d.BasicSample;
import nu.rinu.mygame.box2d.DistanceJointSample;
import nu.rinu.mygame.box2d.FrictionJointSample;
import nu.rinu.mygame.box2d.GearJointSample;
import nu.rinu.mygame.box2d.PrismaticJointSample;
import nu.rinu.mygame.box2d.PulleyJointSample;
import nu.rinu.mygame.box2d.RevoluteJointSample;
import nu.rinu.mygame.box2d.RopeJointSample;
import nu.rinu.mygame.box2d.WeldJointSample;
import nu.rinu.mygame.box2d.WheelJointSample;

public class DesktopLauncher {
    public static void main(String[] arg) {
//        ApplicationAdapter game = new MyGame();
        ApplicationAdapter game = new FrictionJointSample();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(game, config);
    }
}
