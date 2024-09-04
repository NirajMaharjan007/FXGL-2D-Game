package project.misc;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.dynamics.*;
import project.entities.*;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Factory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0 f));


        return entityBuilder(data)
            .type(EntityType.PLAYER)
            .with(physics)
            .bbox(new HitBox(BoundingShape.box(24, 32)))
            .collidable()
            .with(new Player())
            .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0 f));

        return entityBuilder(data)
            .type(EntityType.ENEMY)
            .with(physics)
            .bbox(new HitBox(BoundingShape.box(18, 20)))
            .collidable()
            .with(new Enemy())
            .build();
    }

    @Spawns("wall") {
        return entityBuilder(data)
            .type(EntityType.WALL)
            .with(physics)
            .bbox(new HitBox(BoundingShape.box(18, 20)))
            .collidable()
            .with(new Enemy())
            .build();
    }
}