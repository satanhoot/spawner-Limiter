package me.duckblade.spawnerlimiter.manager;

import me.duckblade.spawnerlimiter.SpawnerLimiter;
import me.duckblade.spawnerlimiter.commands.MainCommand;
import me.duckblade.spawnerlimiter.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PlayerSpawnerManager {
    public static final NamespacedKey PLACE_BY = new NamespacedKey(SpawnerLimiter.getPlugin(), "player_uuid");
    public static int maxSpawner;

    public static void addSpawner(UUID playerId, Location loc) {

        int count = getSpawnerCount(playerId);
        if (count == 0) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerId);
            ConfigManager.getConfig().set("players." + playerId + ".name", player.getName());
        }
        ConfigManager.getConfig().set("players." + playerId + ".used-spawner", count + 1);
        String location = LocationUtils.locationToString(loc);
        List<String> spawnerList = ConfigManager.getConfig().getStringList("players." + playerId + ".spawner-locations");
        spawnerList.add(location);
        ConfigManager.getConfig().set("players." + playerId + ".spawner-locations", spawnerList);
        ConfigManager.saveConfig();
    }

    public static void removeSpawner(UUID playerId, Location loc) {
        int count = getSpawnerCount(playerId);
        if (count > 0) {
            ConfigManager.getConfig().set("players." + playerId + ".used-spawner", count - 1);
            List<String> spawnerList = ConfigManager.getConfig().getStringList("players." + playerId + ".spawner-locations");
            spawnerList.remove(LocationUtils.locationToString(loc));
            ConfigManager.getConfig().set("players." + playerId + ".spawner-locations", spawnerList);
            if (count - 1 == 0) {
                ConfigManager.getConfig().set("players." + playerId, null);
            }
            ConfigManager.saveConfig();
        }
    }

    public static int getSpawnerCount(UUID playerId) {
        return ConfigManager.getConfig().getInt("players." + playerId + ".used-spawner", 0);
    }

    public static int getMaxSpawner(Player player) {
        int defaultMaxSpawner = PlayerSpawnerManager.maxSpawner;

        Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
        if (perms.isEmpty()) return defaultMaxSpawner;

        Set<String> permissionNumberStrings = new HashSet<>();
        for (PermissionAttachmentInfo perm : perms) {
            if (!perm.getValue()) continue;

            String permName = perm.getPermission();
            if (permName.startsWith("spawner.max.")) {
                String maxPart = permName.substring("spawner.max.".length());
                permissionNumberStrings.add(maxPart);
            }
        }
        if (permissionNumberStrings.isEmpty()) return defaultMaxSpawner;

        int most = 0;
        boolean found = false;
        for (String perm : permissionNumberStrings) {

            Integer value = MainCommand.parseInteger(perm);
            if (value != null) {
                most = Math.max(most, value);
                found = true;
            }
        }

        return (found ? most : defaultMaxSpawner);

    }
}