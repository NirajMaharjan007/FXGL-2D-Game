package project.entities;

import static com.almasb.fxgl.dsl.FXGL.*;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import java.util.*;

public class Trees extends Component {
    private Texture texture;

    protected static final int WIDTH = 64, HEIGHT = 64;

    private List<String> tree_list = new ArrayList<String>();

    public Trees() {
        super();

        tree_list.add("tree1.png");

        texture = getAssetLoader().loadTexture("vegetation/Trees_custom/Tree_resize_1.png");
    }

    @Override
    public void onAdded() {
        super.onAdded();

        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }
}