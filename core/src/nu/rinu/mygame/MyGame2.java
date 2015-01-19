package nu.rinu.mygame;

import com.badlogic.gdx.Game;

public class MyGame2 extends Game {
    @Override
    public void create() {
        setScreen(new Menu(this));
    }
}
