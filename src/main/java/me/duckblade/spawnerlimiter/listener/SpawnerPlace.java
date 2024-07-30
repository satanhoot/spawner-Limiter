package me.duckblade.spawnerlimiter.listener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import me.duckblade.spawnerlimiter.utils.Logger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpawnerPlace implements Listener {

    private Cache<UUID, Integer> pmCooldown = CacheBuilder.newBuilder().expireAfterWrite(20, TimeUnit.SECONDS).build();

    @EventHandler
    public void onPlaceSpawner(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) return;

        Player player = event.getPlayer();
        int currentCount = PlayerSpawnerManager.getSpawnerCount(player.getUniqueId());
        int maxSpawner = PlayerSpawnerManager.getMaxSpawner(player);

        if (currentCount >= maxSpawner) {
            event.setCancelled(true);
            messageWithCooldown(player);
        } else {
            PlayerSpawnerManager.addSpawner(player.getUniqueId(), event.getBlock().getLocation());
            Logger.info("Player " + player.getName() + " placed a spawner. Total: " + (currentCount + 1), true);

            CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
            PersistentDataContainer container = spawner.getPersistentDataContainer();
            container.set(PlayerSpawnerManager.placeBy, PersistentDataType.STRING, player.getUniqueId().toString());
            spawner.update();
            Logger.info("Player " + player.getName() + " placed a spawner. with key : " + container.getOrDefault(PlayerSpawnerManager.placeBy, PersistentDataType.STRING, "null") + "Total: " + (currentCount + 1), true);

        }
    }
    private void messageWithCooldown(Player player) {
        if (!pmCooldown.asMap().containsKey(player.getUniqueId())){
            pmCooldown.put(player.getUniqueId(), 1);
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You have reached the maximum number of spawners you can place."));
            Logger.info("Player " + player.getName() + " tried to place a spawner but reached the limit.", true);
        }
    }
}