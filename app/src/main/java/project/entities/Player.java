package project.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Player extends Component {
    public static final int width = 64, height = 64, speed = 128;
    private final AnimatedTexture texture;
    // idle
    private final AnimationChannel animIdleUp, animIdleLeft, animIdleRight, animIdleDown;
    // walk
    private final AnimationChannel animWalkDown, animWalkUp, animWalkLeft, animWalkRight;
    // walk_attack
    private final AnimationChannel animWalkAttackUp, animWalkAttackDown, animWalkAttackLeft, animWalkAttackRight;

    // attack
    private final AnimationChannel animAttackUp, animAttackDown, animAttackLeft, animAttackRight;
    // run
    private final AnimationChannel animRunUp, animRunDown, animRunLeft, animRunRight;

    private PhysicsComponent physics;

    public boolean up = true, down = false, left = false, right = false;
    private boolean attack = false, run = false;

    public Player() {
        super();

        Image idle_image = new Image("assets/textures/player/player_idle.png");
        Image walk_image = new Image("assets/textures/player/player_walk.png");
        Image attack_image = new Image("assets/textures/player/player_attack.png");
        Image walk_attack_image = new Image("assets/textures/player/player_walk_attack.png");
        Image run_image = new Image("assets/textures/player/player_run.png");

        // Idle
        animIdleUp = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 4, 7);
        animIdleLeft = new AnimationChannel(idle_image, 4,
                width, height, Duration.seconds(0.75), 8, 11);
        animIdleDown = new AnimationChannel(idle_image, 4,
                width, height, Duration.seconds(0.75), 0, 3);
        animIdleRight = new AnimationChannel(idle_image, 4,
                width, height, Duration.seconds(0.75), 12, 15);

        // Walk
        animWalkUp = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 6, 11);
        animWalkDown = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 0, 5);
        animWalkLeft = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 12, 17);
        animWalkRight = new AnimationChannel(walk_image, 6, width, height,
                Duration.seconds(0.8), 18, 23);

        // walk_attack
        animWalkAttackUp = new AnimationChannel(walk_attack_image, 6, width, height,
                Duration.seconds(0.54), 6, 11);
        animWalkAttackDown = new AnimationChannel(walk_attack_image, 6, width, height,
                Duration.seconds(0.54), 0, 4);
        animWalkAttackLeft = new AnimationChannel(walk_attack_image, 6, width, height,
                Duration.seconds(0.54), 12, 17);
        animWalkAttackRight = new AnimationChannel(walk_attack_image, 6, width, height,
                Duration.seconds(0.54), 18, 23);

        // Attack
        animAttackUp = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 8, 15);
        animAttackDown = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 0, 7);
        animAttackLeft = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 16, 23);
        animAttackRight = new AnimationChannel(attack_image, 8, width, height,
                Duration.seconds(0.64), 24, 31);

        // Run
        animRunUp = new AnimationChannel(run_image, 8, width, height,
                Duration.seconds(0.84), 8, 15);
        animRunDown = new AnimationChannel(run_image, 8, width, height,
                Duration.seconds(0.84), 0, 7);
        animRunLeft = new AnimationChannel(run_image, 8, width, height,
                Duration.seconds(0.84), 16, 23);
        animRunRight = new AnimationChannel(run_image, 8, width, height,
                Duration.seconds(0.84), 24, 31);

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

        if (physics.isMoving()) {
            // attack = false;
            if (run) {
                if (left && texture.getAnimationChannel() != animRunLeft)
                    texture.loopAnimationChannel(animRunLeft);
                else if (right && texture.getAnimationChannel() != animRunRight)
                    texture.loopAnimationChannel(animRunRight);
                else if (up && texture.getAnimationChannel() != animRunUp)
                    texture.loopAnimationChannel(animRunUp);
                else if (down && texture.getAnimationChannel() != animRunDown)
                    texture.loopAnimationChannel(animRunDown);
            } else if (attack && !run) {
                texture.setOnCycleFinished(() -> attack = false);

                if (left && texture.getAnimationChannel() != animWalkAttackLeft)
                    texture.loopAnimationChannel(animWalkAttackLeft);
                else if (right && texture.getAnimationChannel() != animWalkAttackRight)
                    texture.loopAnimationChannel(animWalkAttackRight);
                else if (up && texture.getAnimationChannel() != animWalkAttackUp)
                    texture.loopAnimationChannel(animWalkAttackUp);
                else if (down && texture.getAnimationChannel() != animWalkAttackDown)
                    texture.loopAnimationChannel(animWalkAttackDown);
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
        } else if (attack && !physics.isMoving()) {
            texture.setOnCycleFinished(() -> attack = false);

            if (left && texture.getAnimationChannel() != animAttackLeft)
                texture.loopAnimationChannel(animAttackLeft);
            else if (right && texture.getAnimationChannel() != animAttackRight)
                texture.loopAnimationChannel(animAttackRight);
            else if (up && texture.getAnimationChannel() != animAttackUp)
                texture.loopAnimationChannel(animAttackUp);
            else if (down && texture.getAnimationChannel() != animAttackDown)
                texture.loopAnimationChannel(animAttackDown);
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

    public boolean getAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void left() {
        left = true;
        right = up = down = false;
        if (run)
            physics.setLinearVelocity(-speed * 2, 0);
        else
            physics.setLinearVelocity(-speed, 0);
    }

    public void right() {
        right = true;
        left = up = down = false;
        if (run)
            physics.setLinearVelocity(speed * 2, 0);
        else
            physics.setLinearVelocity(speed, 0);
    }

    public void up() {
        up = true;
        right = left = down = false;
        if (run)
            physics.setLinearVelocity(0, -speed * 2);
        else
            physics.setLinearVelocity(0, -speed);
    }

    public void down() {
        down = true;
        right = left = up = false;

        if (run)
            physics.setLinearVelocity(0, speed * 2);
        else
            physics.setLinearVelocity(0, speed);
    }

    public void stop() {
        physics.setLinearVelocity(0, 0);
    }

    /*
     *
     * For debugging purposes only
     * Don't try this at your own risk and just ignore it
     *
     */
    public String getAction() {
        return "\nDirection-Boolean \n Up: " + up + "\t Down: " + down
                + "\t Left: " + left + "\t Right: " + right + "\n Action Attack: " + attack;
    }

    public String getAnimationStatus() {
        return "Current Animation: " + texture.getAnimationChannel().getImage().getUrl();
    }

    public String getSpeed() {
        if (run)
            return "Current Speed: " + (speed * 2);
        else
            return "Current Speed: " + speed;
    }
}
