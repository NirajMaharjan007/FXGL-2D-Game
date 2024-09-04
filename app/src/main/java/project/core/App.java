/*
 * This source file was generated by the Gradle 'init' task
 */
package project.core;

import com.almasb.fxgl.app.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import project.entities.Player;
import project.misc.*;

import static com.almasb.fxgl.dsl.FXGL.*;

public class App extends GameApplication {
    private Player player;

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            embeddedShutdown();
        }
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setWidth(800);
        settings.setHeight(640);
        settings.setTitle("Basic Game");
        settings.setVersion("0.0.1");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        getGameScene().setBackgroundColor(Color.BLACK);

        setLevelFromMap("tmx/Level_1.tmx");

        getGameWorld().spawn("enemy", 512, 200);

        Entity player_entity = getGameWorld().spawn("player", 128, 200);
        player = player_entity.getComponent(Player.class);
    }

    @Override
    protected void initInput() {
        super.initInput();
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player.up();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
            }
        }, KeyCode.UP);

        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.down();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
            }
        }, KeyCode.DOWN);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.left();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.right();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("Attack") {
            @Override
            protected void onActionBegin() {
                player.setAttack(true);
            }

            @Override
            protected void onActionEnd() {
                player.stop();
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Run") {
            @Override
            protected void onAction() {
                player.setRun(true);
            }

            @Override
            protected void onActionEnd() {
                player.setRun(false);
            }
        }, KeyCode.X);

        getInput().addAction(new UserAction("Debug Option") {
            @Override
            protected void onActionBegin() {
                System.out.println("App.onAction");
                System.out.println("--------------------------------------------------------");
                System.out.println("Player pos: " + player.getEntity().getPosition());
                System.out.println("Player Actions " + player.getAction());
                System.out.println(player.getAnimationStatus());
                System.out.println(player.getSpeed());
                System.out.println("--------------------------------------------------------");
            }

        }, KeyCode.Z);
    }

    @Override
    protected void initPhysics() {
        super.initPhysics();
        getPhysicsWorld().setGravity(0, 0);
    }

}