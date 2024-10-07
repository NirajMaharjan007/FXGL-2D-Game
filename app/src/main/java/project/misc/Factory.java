package project.misc;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.collision.shapes.MassData;
import com.almasb.fxgl.physics.box2d.dynamics.*;
import project.entities.*;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Factory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        physics.setOnPhysicsInitialized(() -> {
            MassData massData = new MassData();
            massData.mass = 1.00f;

            physics.setFixtureDef(new FixtureDef().friction(0.54f).density(0.24f));
            physics.getBody().setMassData(massData);
        });

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
        physics.setBodyType(BodyType.DYNAMIC);

        physics.setOnPhysicsInitialized(() -> {
            MassData massData = new MassData();
            massData.mass = 2048f;

            // physics.setFixtureDef(new FixtureDef().friction(0.8f));
            physics.setFixtureDef(new FixtureDef().friction(0).density(0.2f));
            physics.getBody().setMassData(massData);
        });

        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .with(physics)
                .bbox(new HitBox(BoundingShape.circle(16)))
                .collidable()
                .with(new Enemy())
                .buildAndAttach();
    }
}