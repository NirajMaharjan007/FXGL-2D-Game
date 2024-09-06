package project.misc;

import javafx.geometry.Rectangle2D;
import project.entities.Enemy;
import project.entities.Player;

public class CollisionDetection {
    public static boolean isTouch(Player player, Enemy enemy) {
        Rectangle2D player_rect = new Rectangle2D(player.getEntity().getX(), player.getEntity().getY(),
                16, 16);

        Rectangle2D enemy_rect = new Rectangle2D(enemy.getEntity().getX(), enemy.getEntity().getY(),
                64, 64);

        return player_rect.intersects(enemy_rect);
    }
}
