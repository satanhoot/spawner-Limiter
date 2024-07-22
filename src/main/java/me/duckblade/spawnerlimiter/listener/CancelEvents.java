package me.duckblade.spawnerlimiter.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class CancelEvents implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        EntityType entityType = event.getEntityType();
        if (entityType == EntityType.PRIMED_TNT || entityType == EntityType.MINECART_TNT || entityType == EntityType.CREEPER || entityType == EntityType.ENDER_CRYSTAL
                || entityType == EntityType.ENDER_DRAGON || entityType == EntityType.WITHER || entityType == EntityType.WITHER_SKULL) {
            List<Block> blocks = event.blockList();
            for (Block block : blocks) {
                if (isSpawner(block)) {
                    event.blockList().remove(block);
                }
            }
        }
    }

    @EventHandler
    public void onBurnBlock(BlockBurnEvent event) {
        if (isSpawner(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.WITHER) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent damageevent = (EntityDamageByEntityEvent) event;
                if (damageevent.getEntity() instanceof Block) {
                    Block block = (Block) damageevent.getEntity();
                    if (isSpawner(block)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isSpawner(Block block) {
        return block.getType() == Material.SPAWNER;
    }
}
