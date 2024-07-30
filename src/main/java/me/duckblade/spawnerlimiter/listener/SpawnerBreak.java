package me.duckblade.spawnerlimiter.listener;

import me.duckblade.spawnerlimiter.SpawnerLimiter;
import me.duckblade.spawnerlimiter.manager.ConfigManager;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import me.duckblade.spawnerlimiter.utils.Logger;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
                PlayerSpawnerManager.removeSpawner(spawnerOwnerUuid, event.getBlock().getLocation());
                Logger.info(event.getPlayer().getName() + " broke a spawner. spawner owner was:" + spawnerOwner.getName() + " now . Total: " + PlayerSpawnerManager.getSpawnerCount(spawnerOwnerUuid), true);
            }
        }

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool.hasItemMeta() && tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
            Block block = event.getBlock();
            player.getWorld().dropItemNaturally(block.getLocation(), getSpawnerItemStack(block));

        }

    }


    public ItemStack getSpawnerItemStack(Block block) {
        if (block.getType() != Material.SPAWNER) return null;

        CreatureSpawner blockState = (CreatureSpawner) block.getState();
        if (blockState.getSpawnedType() == EntityType.PIG) return new ItemStack(Material.SPAWNER);

        ItemStack spawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();

        CreatureSpawner spawnerState = (CreatureSpawner) spawnerMeta.getBlockState();
        spawnerState.setSpawnedType(blockState.getSpawnedType());
        spawnerState.update();
        spawnerMeta.setBlockState(spawnerState);
        spawner.setItemMeta(spawnerMeta);

        return spawner;
    }
}