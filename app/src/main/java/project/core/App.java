/*
 * This source file was generated by the Gradle 'init' task
 */
package project.core;

import com.almasb.fxgl.app.*;
import com.almasb.fxgl.dsl.components.FollowComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Locale;

import project.entities.*;
import project.misc.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static project.entities.Vegetation.*;

public class App extends GameApplication {
    protected static final int WIDTH = 800, HEIGHT = 640;
    private static final String VERSION = "1.0.0dev", TITLE = "Orc Master";
    private Player player;
    private Enemy enemy;
    private Trees tree;
    private Rocks rocks;

    private int count = 0, attackCount = 0;

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            embeddedShutdown();
        }
    }

    @Override
    protected void onPreInit() {
        super.onPreInit();
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle(TITLE);
        settings.setVersion(VERSION);

        System.out.println("OS Name: " + System.getProperty("os.name").toUpperCase(Locale.ENGLISH));
        System.out.println("Game Version: " + VERSION);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        getGameScene().setBackgroundColor(Color.BLACK);

        setLevelFromMap("tmx/Level_1.tmx");

        rocks = getGameWorld().spawn("rocks", 300, 300).getComponent(Rocks.class);

        tree = getGameWorld().spawn("trees", 200, 350).getComponent(Trees.class);

        player = getGameWorld().spawn("player", 128, 200).getComponent(Player.class);

        enemy = getGameWorld().spawn("enemy", 512, 200).getComponent(Enemy.class);
        enemy.getEntity().addComponent(new FollowComponent(player.getEntity(), 128, 128, 255));

    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        getGameTimer().runAtInterval(() -> {
            if (player.getEntity().getY() > tree.getEntity().getY() + tree.getEntity().getHeight()
                    || player.getEntity().getY() > rocks.getEntity().getY()) {
                // Player is below the tree, so move player in front of tree
                player.getEntity().setZIndex(2);
            } else {
                // Player is above the tree, so move player behind the tree
                if (player.getEntity().getX() <= tree.getEntity().getX() ||
                        player.getEntity().getRightX() >= tree.getEntity().getRightX() ||
                        player.getEntity().getX() <= rocks.getEntity().getX() ||
                        player.getEntity().getRightX() >= rocks.getEntity().getRightX()) {
                    player.getEntity().setZIndex(0);
                }
            }

            // For enemy
            if (enemy.getEntity().getY() > tree.getEntity().getY() + (tree.getEntity().getHeight())) {
                // Player is below the tree, so move player in front of tree
                enemy.getEntity().setZIndex(2);
            } else {
                // Player is above the tree, so move player behind the tree
                if (enemy.getEntity().getX() <= tree.getEntity().getX() ||
                        enemy.getEntity().getRightX() >= tree.getEntity().getRightX()) {
                    enemy.getEntity().setZIndex(0);
                }
            }

            // For player and enemy
            if (enemy.getAttack()) {
                enemy.getEntity().setZIndex(2);
                player.getEntity().setZIndex(0);
            }
            if (player.getAttack()) {
                player.getEntity().setZIndex(2);
                enemy.getEntity().setZIndex(0);
            } else if (enemy.getAttack() && player.getAttack()) {
                enemy.setHurt(false);
                player.setHurt(false);
            }
        }, Duration.seconds(0.0000024f));
        // System.out.println("App.onUpdate()" + player.health);

        if (CollisionDetection.isTouch(player, enemy) && enemy.getAttack()) {
            if (!player.isHurt()) {
                player.setHurt(true);
                player.health -= 2;
            } else if (player.isDead()) {
                enemy.setAttack(false);
                player.setHurt(false);
            }
        } else {
            player.setHurt(false);
            enemy.setAttack(false);
        }

        if (enemy.isHurt())
            attackCount++;
        if (attackCount >= 512)
            enemy.setDead(true);

        if (player.health <= 0) {
            player.setDeath(true);
            player.health = 0;
        }
    }

    @Override
    protected void initInput() {
        super.initInput();
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onActionBegin() {
                player.setAttack(false);
            }

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
            protected void onActionBegin() {
                player.setAttack(false);
            }

            @Override
            protected void onAction() {
                player.down();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
                player.setAttack(false);

            }
        }, KeyCode.DOWN);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onActionBegin() {
                player.setAttack(false);
            }

            @Override
            protected void onAction() {
                player.left();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
                player.setAttack(false);

            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onActionBegin() {
                player.setAttack(false);
            }

            @Override
            protected void onAction() {
                player.right();
            }

            @Override
            protected void onActionEnd() {
                player.stop();
                player.setAttack(false);
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("Attack") {
            @Override
            protected void onActionBegin() {
                player.setAttack(true);

                System.out.println("Enemy Hurt " + enemy.isHurt() +
                        " Player Attack " + player.getAttack() +
                        " Status: " + CollisionDetection.isTouch(player, enemy));

                if (player.getAttack() && CollisionDetection.isTouch(player, enemy))
                    enemy.setHurt(true);

                else if (enemy.isHurt())
                    player.setAttack(false);
            }

            @Override
            protected void onActionEnd() {
                player.stop();
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Run") {
            @Override
            protected void onAction() {
                System.out.println("App.onAction " + player.isRunning() + " " + count);
                player.setRun(count <= 64);
                count++;
            }

            @Override
            protected void onActionEnd() {
                player.setRun(false);
                if (count >= 64) {
                    count = 0;
                }
                // count = 0;
            }
        }, KeyCode.X);

        getInput().addAction(new UserAction("Debug Option") {
            @Override
            protected void onActionBegin() {
                System.out.println("App.onAction");
                System.out.println("--------------------------------------------------------");
                System.out.println("Player pos: " + player.getEntity().getPosition());
                System.out.println("Enemy pos: " + enemy.getEntity().getPosition());
                System.out.println("Player Actions " + player.getAction());
                System.out.println(player.getSpeed());
                System.out.println("--------------------------------------------------------");
            }

            @Override
            protected void onActionEnd() {
                attackCount = 0;
            }

        }, KeyCode.Z);
    }

    @Override
    protected void initPhysics() {
        super.initPhysics();
        getPhysicsWorld().setGravity(0, 0);

        Entity walls = entityBuilder()
                .type(EntityType.WALL)
                .collidable()
                .buildScreenBounds(256);
        getGameWorld().addEntity(walls);
    }

}