package me.duckblade.spawnerlimiter;

import me.duckblade.spawnerlimiter.commands.MainCommand;
import me.duckblade.spawnerlimiter.listener.CancelEvents;
import me.duckblade.spawnerlimiter.listener.SpawnerBreak;
import me.duckblade.spawnerlimiter.listener.SpawnerPlace;
import me.duckblade.spawnerlimiter.manager.ConfigManager;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import me.duckblade.spawnerlimiter.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpawnerLimiter extends JavaPlugin {

    private static SpawnerLimiter plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveConfig();
        Logger.info("plugin enabled!");
        getServer().getPluginManager().registerEvents(new SpawnerBreak(), this);
        getServer().getPluginManager().registerEvents(new SpawnerPlace(), this);
        getServer().getPluginManager().registerEvents(new CancelEvents(), this);
        Logger.info("test");

        getCommand("spawnerlimiter").setExecutor(new MainCommand());

    }


    public static SpawnerLimiter getPlugin() {
        return plugin;
    }

    public void saveConfig() {
        saveDefaultConfig();
        ConfigManager.setup();
        Logger.enebledDebug = getConfig().getBoolean("debug");
        Logger.warning("logger enabled!", true);
        PlayerSpawnerManager.maxSpawner = getConfig().getInt("max-default-spawner");
    }
}






