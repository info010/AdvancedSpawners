package org.info_0.advancedspawners.features;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.info_0.advancedspawners.AdvancedSpawners;
import org.info_0.advancedspawners.api.Towny;

public class SetBooster implements Listener {

    @EventHandler
    public void setSpawnerBoster(PlayerInteractEvent event){
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!event.getClickedBlock().getType().equals(Material.SPAWNER)) return;
        if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(AdvancedSpawners.getInstance(),"BOOSTER-LEVEL"), PersistentDataType.INTEGER)) return;
        if(AdvancedSpawners.townyApi()){
            if(!Towny.hasItemUsePermission(event.getClickedBlock(),event.getPlayer())){
                event.getPlayer().sendMessage("Bu arazide bu işlemi yapamazsınız.");
                event.setCancelled(true);
                return;
            }
        }
        ItemStack boosterItem = event.getPlayer().getInventory().getItemInMainHand();
        Block spawner = event.getClickedBlock();
        if(spawner.getMetadata("PLACED").isEmpty()){
            event.getPlayer().sendMessage("Bu işlem yalnızca siteden satın alınan Spawnerlar için geçerlidir.");
            return;
        }
        if(spawner.getMetadata("BOOSTED").get(0).asBoolean()){
            event.getPlayer().sendMessage("Bu spawner zaten hızlandırılmış.");
            return;
        }
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"BOOSTER-LEVEL");
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        int boosterLevel = boosterItem.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER).intValue();
        creatureSpawner.setMinSpawnDelay(creatureSpawner.getMinSpawnDelay()-creatureSpawner.getMinSpawnDelay()*boosterLevel/100);
        creatureSpawner.setMaxSpawnDelay(creatureSpawner.getMaxSpawnDelay()-creatureSpawner.getMaxSpawnDelay()*boosterLevel/100);

    }


}
