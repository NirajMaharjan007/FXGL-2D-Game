package project.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Enemy extends Component {
    public static final int width = 64, height = 64, speed = 128;

    private final AnimatedTexture texture;

    // idle
    private final AnimationChannel animIdleUp, animIdleLeft, animIdleRight, animIdleDown;
    //walk
//    private final AnimationChannel animWalkDown, animWalkUp, animWalkLeft, animWalkRight;
    //attack
//    private final AnimationChannel animAttackUp, animAttackDown, animAttackLeft, animAttackRight;
    private boolean up = true, down = false, left = false, right = false, attack = false, run = false;
    private PhysicsComponent physics;

    public Enemy() {
        super();

        Image idle_image = new Image("assets/textures/enemy/idle.png");

        animIdleUp = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 4, 7);
        animIdleDown = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 0, 3);
        animIdleLeft = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 8, 11);
        animIdleRight = new AnimationChannel(idle_image,
                4, width, height, Duration.seconds(0.75), 12, 15);
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
            if (texture.getAnimationChannel() != animIdleDown)
                texture.loopAnimationChannel(animIdleDown);
        }
    }
}
