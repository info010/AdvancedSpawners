package org.info_0.advancedspawners.metadata;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.info_0.advancedspawners.AdvancedSpawners;
import org.info_0.advancedspawners.api.Towny;
import org.info_0.advancedspawners.features.LevelHolograms;

import static org.info_0.advancedspawners.items.PoenaSagaSpawner.peonasagaSpawner;


public class BreakSpawner implements Listener {
    private boolean hasEmptySlot(Player player) {
        for(int i = 0;i<=35;i++){
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && !(item.getType().isAir())) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    public void breakSpawner(BlockBreakEvent event){
        if(event.getBlock().getMetadata("PLACED").isEmpty() || !event.getBlock().getType().equals(Material.SPAWNER)) return;
        Block spawner = event.getBlock();
        int level = spawner.getMetadata("LEVEL").get(0).asInt();
        Player player = event.getPlayer();
        if(AdvancedSpawners.townyApi()){
            if(!Towny.hasDestroyPermission(spawner,event.getPlayer())){
                event.getPlayer().sendMessage("Bu arazide bu işlemi yapamazsınız.");
                event.setCancelled(true);
                return;
            }
        }
        if(event.isCancelled()) {
            event.getPlayer().sendMessage("Bu arazide bu işlemi yapamazsınız.");
            return;
        }
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        if((event.getPlayer().getInventory().getItemInMainHand().getType().isAir()
                || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)
                || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_PICKAXE)
                || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_PICKAXE)
                || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)
                || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE)
                || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE))
                && !event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
        {
            spawner.setMetadata("LEVEL",new FixedMetadataValue(AdvancedSpawners.getInstance(),level-1));
            LevelHolograms.updateLevelName(spawner,spawner.getMetadata("LEVEL").get(0).asInt());
            creatureSpawner.update();
            if(level-1 >= 1){
                event.setCancelled(true);
            }
        }else{
            if(!hasEmptySlot(player)){
                player.sendMessage("Envanterinde boş yer yok, lütfen boş olduğunda emin ol.");
                event.setCancelled(true);
                return;
            }
            if(player.isSneaking()) {
                player.getInventory().addItem(peonasagaSpawner(creatureSpawner.getSpawnedType(),level));
                LevelHolograms.delSpawnerName(spawner);
            }
            else {
                player.getInventory().addItem(peonasagaSpawner(creatureSpawner.getSpawnedType(),1));
                spawner.setMetadata("LEVEL",new FixedMetadataValue(AdvancedSpawners.getInstance(),level-1));
                LevelHolograms.updateLevelName(spawner,spawner.getMetadata("LEVEL").get(0).asInt());
                creatureSpawner.update();
                if(level-1 >= 1){
                    event.setCancelled(true);
                }
            }
            player.sendMessage("Spawner başarıyla kırıldı.");
        }

    }
}
