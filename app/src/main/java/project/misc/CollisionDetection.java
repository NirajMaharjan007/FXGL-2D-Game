package project.misc;

import javafx.geometry.Rectangle2D;
import project.entities.*;

public class CollisionDetection {
    Player player;
    Enemy enemy;

    private CollisionDetection() {
    }

    // public CollisionDetection(Enemy enemy, Player player) {
    // this.enemy = enemy;
    // this.player = player;
    // }

    // public CollisionDetection(Player player, Enemy enemy) {
    // this.player = player;
    // this.enemy = enemy;
    // }

    public static boolean isCollision(Player player, Enemy enemy) {
        Rectangle2D player_rect = new Rectangle2D(player.getEntity().getX(), player.getEntity().getY(),
                16, 16);

        Rectangle2D enemy_rect = new Rectangle2D(enemy.getEntity().getX(), enemy.getEntity().getY(),
                24, 24);

        return player_rect.intersects(enemy_rect);
    }
}
