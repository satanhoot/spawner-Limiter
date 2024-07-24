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
        List<EntityType> entityTypeList = List.of(EntityType.PRIMED_TNT, EntityType.MINECART_TNT, EntityType.CREEPER, EntityType.ENDER_CRYSTAL, EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.WITHER_SKULL);
        if (!(entityTypeList.contains(event.getEntityType()))) return;

        List<Block> blocks = event.blockList();

        for (Block block : blocks) {
            if (isSpawner(block)) {
                event.blockList().remove(block);
            }
        }

    }

    @EventHandler
    public void onBurnBlock(BlockBurnEvent event) {
        event.setCancelled(isSpawner(event.getBlock()));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.WITHER) return; // this event check Wither, if break spawner
        if (!(event instanceof EntityDamageByEntityEvent damageEvent)) return;

        if (!(damageEvent.getEntity() instanceof Block block)) return;
        event.setCancelled(isSpawner(block));
    }

    private boolean isSpawner(Block block) {
        return block.getType() == Material.SPAWNER;
    }
}
