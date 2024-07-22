package me.duckblade.spawnerlimiter.manager;

import me.duckblade.spawnerlimiter.SpawnerLimiter;
import me.duckblade.spawnerlimiter.utils.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File file;
    private static YamlConfiguration config;
    private static SpawnerLimiter plugin = SpawnerLimiter.getPlugin();

    public static void setup() {
        file = new File(plugin.getDataFolder(), "saves.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("saves.yml", false);
            Logger.info("file created", true);
        }
        config = YamlConfiguration.loadConfiguration(file);

    }

    public static void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public static void saveConfig() {
        try {
            config.save(file);
            Logger.info("file saved", true);
        } catch (IOException e) {
            Logger.warning(e.getMessage());
            Logger.warning("file not saved");
        }
    }


}
