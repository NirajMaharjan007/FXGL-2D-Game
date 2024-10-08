package project.core;

import com.almasb.fxgl.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainUI implements UIController {
    @FXML
    private Label label, counter, label1, counter1;

    protected MainUI() {
    }

    public Label getCounter() {
        return counter;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public void init() {
        counter.setFont(getUIFactoryService().newFont(20));
        label.setFont(getUIFactoryService().newFont(16));

        counter.textProperty().addListener((observable, oldValue, newValue) -> {
            animateLabel(counter);
        });

    }

    private void animateLabel(Label label) {
        animationBuilder()
                .duration(Duration.seconds(0.32))
                .fadeIn(label)
                .buildAndPlay();
    }

}
