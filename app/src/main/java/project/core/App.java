/*
 * This source file was generated by the Gradle 'init' task
 */
package project.core;

import com.almasb.fxgl.app.*;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import project.misc.Factory;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;


public class App extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Basic Game");
        settings.setVersion("0.0.1");
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new Factory());
        Entity player = getGameWorld().spawn("Player", 180, 180);
    }
}
