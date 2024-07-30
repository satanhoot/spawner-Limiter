package me.duckblade.spawnerlimiter.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationToString {

    public static String locationToString(Location location) {
        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        return worldName + ":" + x + ":" + y + ":" + z;
    }

    public static Location StringToLocation(String string) {
        String[] split = string.split(":");
        if (split.length != 4) {
            return null;
        }

        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        return new Location(world, x, y, z);
    }

}
