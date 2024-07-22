package me.duckblade.spawnerlimiter.listener;

import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import me.duckblade.spawnerlimiter.utils.Logger;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerPlace implements Listener {
    @EventHandler
    public void onPlaceSpawner(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) {
            return;
        }
        Player player = event.getPlayer();
        int currentCount = PlayerSpawnerManager.getSpawnerCount(player.getUniqueId());
        int maxSpawner = PlayerSpawnerManager.getMaxSpawner(player.getUniqueId());

        if (currentCount >= maxSpawner) {
            event.setCancelled(true);
            player.sendMessage("You have reached the maximum number of spawners you can place.");
            Logger.info("Player " + player.getName() + " tried to place a spawner but reached the limit.", true);
        } else {
            PlayerSpawnerManager.addSpawner(player.getUniqueId());
            Logger.info("Player " + player.getName() + " placed a spawner. Total: " + (currentCount + 1), true);

            CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
            PersistentDataContainer container = spawner.getPersistentDataContainer();
            container.set(PlayerSpawnerManager.placeBy, PersistentDataType.STRING, player.getUniqueId().toString());
            spawner.update();
            Logger.info("Player " + player.getName() + " placed a spawner. with key : " + container.getOrDefault(PlayerSpawnerManager.placeBy, PersistentDataType.STRING, "null") + "Total: " + (currentCount + 1), true);

        }
    }
}