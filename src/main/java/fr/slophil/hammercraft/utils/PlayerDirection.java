package fr.slophil.hammercraft.utils;

import org.bukkit.entity.Player;

public enum PlayerDirection {
    NORTH, EAST, SOUTH, WEST, UP, DOWN;

    public static PlayerDirection getPlayerDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360;
        }
        if (player.getLocation().getPitch() < -45) {
            return UP;
        } else if (player.getLocation().getPitch() > 45) {
            return DOWN;
        } if (rotation >= 225 && rotation < 315) {
            return SOUTH;
        } else if (rotation >= 315 || rotation < 45) {
            return WEST;
        } else if (rotation >= 45 && rotation < 135) {
            return NORTH;
        } else if (rotation >= 135) {
            return EAST;
        } else if (player.getLocation().getPitch() < -45) {
            return UP;
        } else if (player.getLocation().getPitch() > 45) {
            return DOWN;
        } else {
            return null;
        }
    }

}
