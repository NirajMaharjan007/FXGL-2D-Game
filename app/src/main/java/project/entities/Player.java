package project.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Player extends Component {
    public static final int width = 64, height = 64, speed = 128;

    private final AnimatedTexture texture;
    private final AnimationChannel animIdleUp, animIdleLeft, animIdleRight, animIdleDown,
            animWalkDown, animWalkUp, animWalkLeft, animWalkRight;
    private PhysicsComponent physics;

    private boolean up = true, down = false, left = false, right = false;


    public Player() {
        super();

        Image idle_image = new Image("assets/textures/player/player_idle.png");
        Image walk_image = new Image("assets/textures/player/player_walk.png");
        //Idle
        animIdleUp = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 4, 7);
        animIdleLeft = new AnimationChannel(idle_image, 4,
                width, height, Duration.seconds(0.75), 8, 11);
        animIdleDown = new AnimationChannel(idle_image, 4,
                width, height, Duration.seconds(0.75), 0, 3);
        animIdleRight = new AnimationChannel(idle_image, 4,
                width, height, Duration.seconds(0.75), 12, 15);


        //Walk
        animWalkUp = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 6, 11);
        animWalkDown = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 0, 5);
        animWalkLeft = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 12, 17);
        animWalkRight = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 18, 23);

        texture = new AnimatedTexture(animIdleUp);
        texture.loop();
    }


    public void setPosition(double x, double y) {
        physics.overwritePosition(new Point2D(x, y));
    }

    @Override
    public void onAdded() {
        super.onAdded();
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);

//        System.out.println("Player.onUpdate " + left + " " + right + " " + up + " " + down);

        if (physics.isMoving()) {
            if (left && texture.getAnimationChannel() != animWalkLeft)
                texture.loopAnimationChannel(animWalkLeft);
            else if (right && texture.getAnimationChannel() != animWalkRight)
                texture.loopAnimationChannel(animWalkRight);
            else if (up && texture.getAnimationChannel() != animWalkUp)
                texture.loopAnimationChannel(animWalkUp);
            else if (down && texture.getAnimationChannel() != animWalkDown)
                texture.loopAnimationChannel(animWalkDown);
        } else {
            if (left && texture.getAnimationChannel() != animIdleLeft)
                texture.loopAnimationChannel(animIdleLeft);
            else if (right && texture.getAnimationChannel() != animIdleRight)
                texture.loopAnimationChannel(animIdleRight);
            else if (up && texture.getAnimationChannel() != animIdleUp)
                texture.loopAnimationChannel(animIdleUp);
            else if (down && texture.getAnimationChannel() != animIdleDown)
                texture.loopAnimationChannel(animIdleDown);
        }

    }

    public void left() {
        left = true;
        right = up = down = false;
        physics.setLinearVelocity(-speed, 0);
    }

    public void right() {
        right = true;
        left = up = down = false;
        physics.setLinearVelocity(speed, 0);
    }

    public void up() {
        up = true;
        right = left = down = false;
        physics.setLinearVelocity(0, -speed);
    }

    public void down() {
        down = true;
        right = left = up = false;
        physics.setLinearVelocity(0, speed);
    }

    public void stop() {
        physics.setLinearVelocity(0, 0);
    }
}

