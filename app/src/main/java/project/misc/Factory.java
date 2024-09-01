package project.misc;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.dynamics.*;
import project.entities.Player;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Factory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));


        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .with(physics)
                .bbox(new HitBox(BoundingShape.box(10, 16)))
                .collidable()
                .with(new Player())
                .build();
    }
}
