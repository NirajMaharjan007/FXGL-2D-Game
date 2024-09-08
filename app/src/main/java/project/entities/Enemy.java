package project.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Enemy extends Component {
    public static final int width = 64, height = 64, speed = 128;

    private final AnimatedTexture texture;

    // idle
    private final AnimationChannel animIdleUp, animIdleLeft, animIdleRight, animIdleDown;

    private final AnimationChannel animHurtUp, animHurtLeft, animHurtRight, animHurtDown;
    // walk
    private final AnimationChannel animWalkDown, animWalkUp, animWalkLeft, animWalkRight;
    // animWalkRight;
    // attack
    // private final AnimationChannel animAttackUp, animAttackDown, animAttackLeft,
    // animAttackRight;
    public boolean up = true, down = false, left = false, right = false;
    private boolean attack = false, hurt = false;

    private PhysicsComponent physics;

    public Enemy() {
        super();

        Image idle_image = new Image("assets/textures/enemy/idle.png");
        Image hurt_image = new Image("assets/textures/enemy/hurt.png");
        Image walk_image = new Image("assets/textures/enemy/walk.png");

        // Idle
        animIdleUp = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 4, 7);
        animIdleDown = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 0, 3);
        animIdleLeft = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 8, 11);
        animIdleRight = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 12, 15);

        // Hurt
        animHurtDown = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.64), 0, 5);
        animHurtUp = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.64), 6, 11);
        animHurtLeft = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.64), 12, 17);
        animHurtRight = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.64), 18, 23);

        // Walk
        animWalkUp = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.64), 6, 11);
        animWalkDown = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.64), 0, 5);
        animWalkLeft = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.64), 12, 17);
        animWalkRight = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.64), 18, 23);

        texture = new AnimatedTexture(animIdleUp);
        texture.loop();
    }

    @Override
    public void onAdded() {
        super.onAdded();
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);

        // move();

        if (!physics.isMoving()) {
            if (hurt) {
                texture.setOnCycleFinished(() -> hurt = false);
                if (texture.getAnimationChannel() != animHurtDown)
                    texture.loopAnimationChannel(animHurtDown);
            } else {
                if (down && texture.getAnimationChannel() != animIdleDown)
                    texture.loopAnimationChannel(animIdleDown);
                if (up && texture.getAnimationChannel() != animIdleUp)
                    texture.loopAnimationChannel(animIdleUp);
                if (right && texture.getAnimationChannel() != animIdleRight)
                    texture.loopAnimationChannel(animIdleRight);
                if (left && texture.getAnimationChannel() != animIdleLeft)
                    texture.loopAnimationChannel(animIdleLeft);
            }
        } else {
            if (left && texture.getAnimationChannel() != animWalkLeft)
                texture.loopAnimationChannel(animWalkLeft);
            else if (right && texture.getAnimationChannel() != animWalkRight)
                texture.loopAnimationChannel(animWalkRight);
            else if (up && texture.getAnimationChannel() != animWalkUp)
                texture.loopAnimationChannel(animWalkUp);
            else if (down && texture.getAnimationChannel() != animWalkDown)
                texture.loopAnimationChannel(animWalkDown);
        }
    }

    public void move() {
        down = true;
        right = left = up = false;

        physics.setLinearVelocity(0, speed);
    }

    public void move(double x, double y) {
        if (x < 0) {
            left = true;
            right = up = down = false;
        } else if (x > 0) {
            right = true;
            left = up = down = false;
        } else if (y > 0) {
            down = true;
            left = up = right = false;
        } else if (y < 0) {
            up = true;
            left = right = down = false;
        }

        physics.setLinearVelocity(x, y);

    }

    public void stop() {
        physics.setLinearVelocity(0, 0);
    }

    public void setPosition(double x, double y) {
        physics.overwritePosition(new Point2D(x, y));
    }

    public boolean getHurt() {
        return hurt;
    }

    public void setHurt(boolean b) {
        hurt = true;
    }
}
