package project.core;

import static com.almasb.fxgl.dsl.FXGL.animationBuilder;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;

import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MainUI implements UIController {
    @FXML
    private Label label;

    @FXML
    private Label counter;

    public Label getCounter() {
        return counter;
    }

    public Label getLabel() {
        return label;
    }

    protected MainUI() {

    }

    @Override
    public void init() {
        counter.setFont(getUIFactoryService().newFont(24));
        label.setFont(getUIFactoryService().newFont(16));

        counter.textProperty().addListener((observable, oldValue, newValue) -> {
            animateLabel(counter);
        });
    }

    private void animateLabel(Label label) {
        animationBuilder()
                .duration(Duration.seconds(0.322))
                .fadeIn(label)
                .buildAndPlay();
    }

}
