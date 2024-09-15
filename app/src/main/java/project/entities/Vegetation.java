package project.entities;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class Vegetation {

    // private static final int WIDTH = 64, HEIGHT = 64;

    public enum Types {
    }

    public static class Rocks extends Component {
        protected Texture texture;

        public Rocks() {
            super();
            texture = getAssetLoader().loadTexture("vegetation/Rocks_custom/Rock_resize_1.png");
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

    public static class Trees extends Component {
        protected Texture texture;

        public Trees() {
            super();
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
}
