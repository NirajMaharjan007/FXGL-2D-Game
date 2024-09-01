package project.misc;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.*;
import javafx.geometry.Point2D;
import project.entities.Player;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Factory implements EntityFactory {
    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder()
                .type(EntityType.PLAYER)
                .from(data)
                .bbox(new HitBox(new Point2D(10, 25), BoundingShape.box(10, 17)))
                .with(new CollidableComponent(true))
                .with(new Player())
                .build();
    }
}
