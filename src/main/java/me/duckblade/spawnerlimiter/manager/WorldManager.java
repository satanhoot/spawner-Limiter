package me.duckblade.spawnerlimiter.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;

public class WorldManager {

    private static HashSet<String> disabledWorlds = new HashSet<>();


    public static void setup(FileConfiguration config) {
        for (World world : Bukkit.getWorlds()) {
            if (config.getStringList("disabled-worlds").contains(world.getName())) {
                disabledWorlds.add(world.getName());
            }
        }
    }


    public static boolean isWorldDisabled(String worldName) {
        return disabledWorlds.contains(worldName);
    }

}
