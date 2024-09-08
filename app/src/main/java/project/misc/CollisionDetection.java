package project.misc;

import javafx.geometry.Rectangle2D;
import project.entities.Enemy;
import project.entities.Player;

public class CollisionDetection {
    public static boolean isTouch(Player player, Enemy enemy) {
        int size = 64;
        Rectangle2D player_rect = new Rectangle2D(0, 0, 0, 0);
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

        return player_rect.intersects(enemy_rect);
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

        // Calculate distance to the target
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Move the follower towards the target at the specified speed
        if (distance > 35) {
            // double moveX = (deltaX / distance) * 2048 * tpf;
            // double moveY = (deltaY / distance) * 2048 * tpf;

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                // Move in the X direction (horizontal)
                if (deltaX != 0) {
                    moveX = Math.signum(deltaX) * 4096 * tpf;
                    moveY = 0;
                }
            } else {
                if (deltaY != 0) {
                    moveY = Math.signum(deltaY) * 4096 * tpf;
                    moveX = 0;
                }
            }

            enemy.move(moveX, moveY);
        } else if (distance <= 35) {
            enemy.stop();
        }
    }
}
