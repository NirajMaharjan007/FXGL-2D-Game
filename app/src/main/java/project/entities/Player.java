package project.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;


public class Player extends Component {
    public static final int width = 64, height = 64;
    char direction = 'u';
    private AnimatedTexture texture;
    private AnimationChannel animIdleUp, animWalkUp;
    private PhysicsComponent physics;

    public Player() {
        super();

        Image image = new Image("assets/textures/player/walk.png");

        animIdleUp = new AnimationChannel(image, 3, 32, 32, Duration.seconds(0.66), 0, 2);
        animWalkUp = new AnimationChannel(image, 9, width, height, Duration.seconds(0.77), 0, 8);
        texture = new AnimatedTexture(animWalkUp);
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
        if (texture.getAnimationChannel() != animWalkUp) {
            texture.loopAnimationChannel(animWalkUp);
        }


        /*switch (Character.toLowerCase(direction)) {
            case 'u':
                texture.loopAnimationChannel(animWalkUp);
                break;
            case 'd':
                break;
            case 'l':
                break;
            case 'r':
                break;
        }*/
    }
}
