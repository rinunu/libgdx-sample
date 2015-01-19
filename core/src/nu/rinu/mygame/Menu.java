package nu.rinu.mygame;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Value;
import nu.rinu.mygame.box2d.BasicSample;
import nu.rinu.mygame.box2d.Box2dSampleBase;
import nu.rinu.mygame.box2d.DistanceJointSample;
import nu.rinu.mygame.box2d.FrictionJointSample;
import nu.rinu.mygame.box2d.GearJointSample;
import nu.rinu.mygame.box2d.PrismaticJointSample;
import nu.rinu.mygame.box2d.PulleyJointSample;
import nu.rinu.mygame.box2d.RevoluteJointSample;
import nu.rinu.mygame.box2d.RopeJointSample;
import nu.rinu.mygame.box2d.BaseScreen;
import nu.rinu.mygame.box2d.ShapesSample;
import nu.rinu.mygame.box2d.WeldJointSample;
import nu.rinu.mygame.box2d.WheelJointSample;

public class Menu extends BaseScreen {

    private final MyGame2 game;
    private Stage stage;
    private Skin skin;
    private Table table;

    public Menu(MyGame2 game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        createMenu();
    }

    private void createMenu() {

        skin = new Skin(Gdx.files.internal("skin.json"));

        // TODO ツールでデザインできるようにする
        Table questTable = new Table();
        ScrollPane questScrollPane = new ScrollPane(questTable);
        questScrollPane.setWidth(700);

        for (final MenuItem menuItem : getMenuItems()) {
            TextButton button = new TextButton(menuItem.getName(), skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    show(menuItem.class_);
                }
            });
            button.setUserObject(menuItem);
            questTable.add(button).width(300).height(30).pad(10).row();
        }

        questTable.bottom();
        table.add(questScrollPane);
    }

    private void show(Class<?> class_) {
        try {
            Box2dSampleBase scene = (Box2dSampleBase) class_.newInstance();
            scene.setGame(game);
            game.setScreen(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Value
    class MenuItem {
        private Class<?> class_;

        public String getName() {
            return class_.getSimpleName();
        }
    }

    private List<MenuItem> getMenuItems() {
        return Arrays.asList(
            new MenuItem(ShapesSample.class),
            new MenuItem(BasicSample.class),
            new MenuItem(DistanceJointSample.class),
            new MenuItem(RevoluteJointSample.class),
            new MenuItem(PrismaticJointSample.class),
            new MenuItem(PulleyJointSample.class),
            new MenuItem(GearJointSample.class),
            new MenuItem(WheelJointSample.class),
            new MenuItem(WeldJointSample.class),
            new MenuItem(RopeJointSample.class),
            new MenuItem(FrictionJointSample.class)
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
