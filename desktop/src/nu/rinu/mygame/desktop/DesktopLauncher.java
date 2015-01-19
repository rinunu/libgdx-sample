package nu.rinu.mygame.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nu.rinu.mygame.Menu;
import nu.rinu.mygame.MyGame;
import nu.rinu.mygame.MyGame2;
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
        MyGame2 game = new MyGame2();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(game, config);
    }
}
