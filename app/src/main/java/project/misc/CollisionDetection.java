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
}
