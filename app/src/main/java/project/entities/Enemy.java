package project.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.*;
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
    // attack
    private final AnimationChannel animAttackUp, animAttackDown, animAttackLeft, animAttackRight;
    // Death
    private final AnimationChannel animDeathUp, animDeathLeft, animDeathRight, animDeathDown;

    public boolean up, down, left, right;
    private boolean attack = false, hurt = false, death = false;
    private PhysicsComponent physics;

    public Enemy() {
        super();

        Image idle_image = new Image("assets/textures/enemy/idle.png");
        Image hurt_image = new Image("assets/textures/enemy/hurt.png");
        Image walk_image = new Image("assets/textures/enemy/walk.png");
        Image attack_image = new Image("assets/textures/enemy/attack.png");
        Image death_image = new Image("assets/textures/enemy/death.png");

        // Death
        animDeathDown = new AnimationChannel(death_image, 8, width, height,
                Duration.seconds(0.85), 0, 7);
        animDeathUp = new AnimationChannel(death_image, 8, width, height,
                Duration.seconds(0.85), 8, 15);
        animDeathLeft = new AnimationChannel(death_image, 8, width, height,
                Duration.seconds(0.85), 16, 23);
        animDeathRight = new AnimationChannel(death_image, 8, width, height,
                Duration.seconds(0.85), 24, 31);

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

        // Attack
        animAttackDown = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 0, 7);
        animAttackUp = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 8, 15);
        animAttackLeft = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 16, 23);
        animAttackRight = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 24, 31);

        texture = new AnimatedTexture(animIdleUp);
        texture.loop();
    }

    @Override
    public void onAdded() {
        super.onAdded();
        entity.getViewComponent().addChild(texture);
    }

    private void idle() {
        if (hurt) {
            texture.setOnCycleFinished(() -> hurt = false);
            if (down && texture.getAnimationChannel() != animHurtDown)
                texture.loopAnimationChannel(animHurtDown);
            else if (up && texture.getAnimationChannel() != animHurtUp)
                texture.loopAnimationChannel(animHurtUp);
            else if (left && texture.getAnimationChannel() != animHurtLeft)
                texture.loopAnimationChannel(animHurtLeft);
            else if (right && texture.getAnimationChannel() != animHurtRight)
                texture.loopAnimationChannel(animHurtRight);
        } else if (attack) {
            texture.setOnCycleFinished(() -> attack = false);
            if (down && texture.getAnimationChannel() != animAttackDown)
                texture.loopAnimationChannel(animAttackDown);
            else if (up && texture.getAnimationChannel() != animAttackUp)
                texture.loopAnimationChannel(animAttackUp);
            else if (right && texture.getAnimationChannel() != animAttackRight)
                texture.loopAnimationChannel(animAttackRight);
            else if (left && texture.getAnimationChannel() != animAttackLeft)
                texture.loopAnimationChannel(animAttackLeft);
        } else if (death) {
            if (down && texture.getAnimationChannel() != animDeathDown)
                texture.playAnimationChannel(animDeathDown);
            else if (up && texture.getAnimationChannel() != animDeathUp)
                texture.playAnimationChannel(animDeathUp);
            else if (left && texture.getAnimationChannel() != animDeathLeft)
                texture.playAnimationChannel(animDeathLeft);
            else if (right && texture.getAnimationChannel() != animDeathRight)
                texture.playAnimationChannel(animDeathRight);
        } else {
            if (down && texture.getAnimationChannel() != animIdleDown)
                texture.loopAnimationChannel(animIdleDown);
            else if (up && texture.getAnimationChannel() != animIdleUp)
                texture.loopAnimationChannel(animIdleUp);
            else if (right && texture.getAnimationChannel() != animIdleRight)
                texture.loopAnimationChannel(animIdleRight);
            else if (left && texture.getAnimationChannel() != animIdleLeft)
                texture.loopAnimationChannel(animIdleLeft);
        }
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
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
            this.idle();
        }
    }

    public void move(double x, double y) {
        physics.setAngularVelocity(0);
        if (!physics.isMovingX())
            left = right = false;
        if (!physics.isMovingY())
            up = down = false;

        if (physics.isMovingX()) {
            if (x < 0) {
                left = true;
                right = false;
            } else if (x > 0) {
                right = true;
                left = false;
            } else
                right = left = false;
        } else if (physics.isMovingY()) {
            if (y > 0) {
                down = true;
                up = false;
            } else if (y < 0) {
                up = true;
                down = false;
            } else
                up = down = false;
        }

        physics.setLinearVelocity(x, y);

        // System.out.println(x + " " + y);
    }

    public void stop() {
        physics.setLinearVelocity(0, 0);
    }

    public void setPosition(double x, double y) {
        physics.overwritePosition(new Point2D(x, y));
    }

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean b) {
        hurt = true;
    }

    public boolean getAttack() {
        return attack;
    }

    public void setAttack(boolean b) {
        this.attack = b;
    }

    public boolean isDead() {
        return death;
    }

    public void setDead(boolean b) {
        death = true;
    }
}