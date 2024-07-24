package me.duckblade.spawnerlimiter;

import me.duckblade.spawnerlimiter.commands.MainCommand;
import me.duckblade.spawnerlimiter.listener.CancelEvents;
import me.duckblade.spawnerlimiter.listener.SpawnerBreak;
import me.duckblade.spawnerlimiter.listener.SpawnerPlace;
import me.duckblade.spawnerlimiter.manager.ConfigManager;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import me.duckblade.spawnerlimiter.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class SpawnerLimiter extends JavaPlugin {

    private static SpawnerLimiter plugin;

    public static SpawnerLimiter getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        plugin = this;
        saveConfig();
        Logger.info("plugin enabled!", false);
        // register events
        List<Listener> listerners = List.of(new SpawnerBreak(), new SpawnerPlace(), new CancelEvents());
        listerners.forEach(listerner -> getServer().getPluginManager().registerEvents(listerner, this));
        // register commands
        getCommand("spawnerlimiter").setExecutor(new MainCommand());

    }


    public void saveConfig() {
        saveDefaultConfig();
        ConfigManager.setup(plugin);
        Logger.enebledDebug = getConfig().getBoolean("debug");
        Logger.warning("logger enabled!", true);
        PlayerSpawnerManager.maxSpawner = getConfig().getInt("max-default-spawner");
    }
}






