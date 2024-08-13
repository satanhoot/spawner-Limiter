package me.duckblade.spawnerlimiter.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceHolderApiHook extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "SpawnerLimiter";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DuckBlade";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

        if (player == null || !player.isOnline()) return null;
        Player pl = player.getPlayer();
        if (params.equalsIgnoreCase("used_spawner")) {
            return PlayerSpawnerManager.getSpawnerCount(pl.getUniqueId()) + "";

        }
        if (params.equalsIgnoreCase("max_spawner")) {
            return PlayerSpawnerManager.getMaxSpawner(pl) + "";
        }

        return null;
    }

    public static void registerHook() {
        new PlaceHolderApiHook().register();
    }

}
