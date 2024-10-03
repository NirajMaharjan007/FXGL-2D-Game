package project.misc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.*;
import javafx.geometry.Rectangle2D;
import project.entities.*;

public class CollisionDetection {
    public static boolean isTouch(Player player, Enemy enemy) {
        int size = 64;
        Rectangle2D player_rect = new Rectangle2D(0, 0, size, size);
        Rectangle2D enemy_rect = new Rectangle2D(enemy.getEntity().getX(), enemy.getEntity().getY(),
                size, size);

        if (player.left)
            player_rect = new Rectangle2D(player.getEntity().getX() - size, player.getEntity().getY(),
                    size, size);

        if (player.right)
            player_rect = new Rectangle2D(player.getEntity().getX() + size, player.getEntity().getY(),
                    size, size);

        if (player.up)
            player_rect = new Rectangle2D(player.getEntity().getX(), player.getEntity().getY() - size,
                    size, size);

        if (player.down)
            player_rect = new Rectangle2D(player.getEntity().getX(), player.getEntity().getY() + size,
                    size, size);

        return player_rect.intersects(enemy_rect) && !enemy.isDead();
    }

    public static void follow(Player player, Enemy enemy, double tpf) {
        double moveX = 0, moveY = 0;

        // Get current position of the entity
        double currentX = enemy.getEntity().getX();
        double currentY = enemy.getEntity().getY();

        // Get target position
        double targetX = player.getEntity().getX();
        double targetY = player.getEntity().getY();

        // Calculate direction vector from current entity to the target
        double deltaX = targetX - currentX;
        double deltaY = targetY - currentY;

        // Calculate distance to the target Pythagorean Theorem
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Move the follower towards the target at the specified speed
        if (distance > 35 && !enemy.isDead()) {
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX != 0)
                    moveX = Math.signum(deltaX) * 8192 * tpf;

            } else {
                if (deltaY != 0)
                    moveY = Math.signum(deltaY) * 8192 * tpf;
            }
            enemy.move(moveX, moveY);
            System.out.println("CollisionDetection.follow():\n\tX: " + moveX + " Y: " + moveY);
        } else if (enemy.isDead()) {
            enemy.stop();
        } else if (distance <= 35) {
            enemy.setAttack(!enemy.isDead());
            enemy.stop();
        }
    }

    public static ComponentListener follow(Entity entity) {
        return new ComponentListener() {
            @Override
            public void onAdded(Component component) {
                Player player = component.getEntity().getComponent(Player.class);
                while (!player.isDead()) {
                    Enemy enemy = component.getEntity().getComponent(Enemy.class);
                    follow(player, enemy, 1.0 / 60.0);
                }

                System.out.println("CollisionDetection.follow(...).new ComponentListener() {...}.onAdded()");
            }

            @Override
            public void onRemoved(Component component) {
            }
        };
    }
}