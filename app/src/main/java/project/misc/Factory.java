package project.misc;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.dynamics.*;

import static project.entities.Vegetation.*;
import project.entities.*;

import javafx.geometry.Point2D;
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

    @Spawns("trees")
    public Entity getTrees(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);

        return entityBuilder(data)
                .type(EntityType.TREE)
                .with(physics)
                .bbox(new HitBox("BOTTOM", new Point2D(0, 24), BoundingShape.circle(10)))
                .with(new Trees())
                .collidable()
                .zIndex(1)
                .buildAndAttach();
    }

    @Spawns("rocks")
    public Entity getRocks(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);

        return entityBuilder(data)
                .type(EntityType.ROCK)
                .with(physics)
                .bbox(new HitBox("BOTTOM", new Point2D(0, 24), BoundingShape.circle(10)))
                .with(new Rocks())
                .collidable()
                .zIndex(1)
                .buildAndAttach();
    }
}