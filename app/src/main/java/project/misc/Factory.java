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
        physics.setFixtureDef(new FixtureDef().friction(1.28f));

        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .with(physics)
                .bbox(new HitBox(BoundingShape.circle(16)))
                .collidable()
                .with(new Player())
                .buildAndAttach();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        physics.setFixtureDef(new FixtureDef().friction(1.28f));

        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .with(physics)
                .bbox(new HitBox(BoundingShape.circle(16)))
                .collidable()
                .with(new Enemy())
                .buildAndAttach();
    }

}