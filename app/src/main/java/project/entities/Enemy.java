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
    //walk
//    private final AnimationChannel animWalkDown, animWalkUp, animWalkLeft, animWalkRight;
    //attack
//    private final AnimationChannel animAttackUp, animAttackDown, animAttackLeft, animAttackRight;
    private boolean up = true, down = false,
            left = false, right = false, attack = false,
            run = false, hurt = false;

    private PhysicsComponent physics;

    public Enemy() {
        super();

        Image idle_image = new Image("assets/textures/enemy/idle.png");
        Image hurt_image = new Image("assets/textures/enemy/hurt.png");

        //Idle
        animIdleUp = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 4, 7);
        animIdleDown = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 0, 3);
        animIdleLeft = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 8, 11);
        animIdleRight = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 12, 15);

        //Hurt
        animHurtDown = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.74), 0, 5);

        animHurtUp = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.74), 6, 11);

        animHurtLeft = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.74), 12, 17);

        animHurtRight = new AnimationChannel(hurt_image, 6, width, height,
                Duration.seconds(0.74), 18, 23);


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
        if (!physics.isMoving()) {
            if (hurt) {
                texture.setOnCycleFinished(() -> hurt = false);
                if (texture.getAnimationChannel() != animHurtDown)
                    texture.loopAnimationChannel(animHurtDown);
            } else {
                if (texture.getAnimationChannel() != animIdleDown)
                    texture.loopAnimationChannel(animIdleDown);
            }
        }
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
