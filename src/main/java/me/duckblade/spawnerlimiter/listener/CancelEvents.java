package me.duckblade.spawnerlimiter.listener;

import me.duckblade.spawnerlimiter.manager.WorldManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CancelEvents implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (WorldManager.isWorldDisabled(event.getLocation().getWorld().getName())) return;
        event.blockList().removeIf(this::isSpawner);
    }

    @EventHandler
    public void onBurnBlock(BlockBurnEvent event) {
        if (WorldManager.isWorldDisabled(event.getBlock().getWorld().getName())) return;
        if (isSpawner(event.getBlock())) { event.setCancelled(true); }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (WorldManager.isWorldDisabled(event.getEntity().getWorld().getName())) return;
        if (event.getEntityType() != EntityType.WITHER) return; // this event check Wither, if break spawner
        if (!(event instanceof EntityDamageByEntityEvent damageEvent)) return;

        if (!(damageEvent.getEntity() instanceof Block block)) return;
        if (isSpawner(block)) { event.setCancelled(true); }
    }

    private boolean isSpawner(Block block) {
        return block.getType() == Material.SPAWNER;
    }
}
