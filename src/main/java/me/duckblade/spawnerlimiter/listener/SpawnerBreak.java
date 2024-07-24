package me.duckblade.spawnerlimiter.listener;

import me.duckblade.spawnerlimiter.SpawnerLimiter;
import me.duckblade.spawnerlimiter.manager.ConfigManager;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import me.duckblade.spawnerlimiter.utils.Logger;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SpawnerBreak implements Listener {

    @EventHandler
    public void onBreakSpawner(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) return;

        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        PersistentDataContainer container = spawner.getPersistentDataContainer();

        if (container.has(PlayerSpawnerManager.placeBy, PersistentDataType.STRING)) {
            String uuidString = container.get(PlayerSpawnerManager.placeBy, PersistentDataType.STRING);
            UUID spawnerOwnerUuid = UUID.fromString(uuidString);
            OfflinePlayer spawnerOwner = SpawnerLimiter.getPlugin().getServer().getOfflinePlayer(spawnerOwnerUuid);

            if (ConfigManager.getConfig().contains("players." + uuidString)) {
                PlayerSpawnerManager.removeSpawner(spawnerOwnerUuid);
                Logger.info(event.getPlayer().getName() + " broke a spawner. spawner owner was:" + spawnerOwner.getName() + " now . Total: " + PlayerSpawnerManager.getSpawnerCount(spawnerOwnerUuid), true);
            }
        }
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool.hasItemMeta() && tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack spawnerForDrop = new ItemStack(Material.SPAWNER);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), spawnerForDrop);

                }
            }.runTaskLater(SpawnerLimiter.getPlugin(), 5L);

        }

    }
}