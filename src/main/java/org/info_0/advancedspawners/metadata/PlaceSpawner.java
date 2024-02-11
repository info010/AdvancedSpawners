package org.info_0.advancedspawners.metadata;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.info_0.advancedspawners.AdvancedSpawners;
import org.info_0.advancedspawners.api.Towny;
import org.info_0.advancedspawners.features.LevelHolograms;

public class PlaceSpawner implements Listener {

    private Towny towny = new Towny();

    @EventHandler
    public void placeSpawner(BlockPlaceEvent event){
        if(!event.getItemInHand().getItemMeta()
                .getPersistentDataContainer().has(new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL"), PersistentDataType.INTEGER)) return;
        if(!event.getBlockPlaced().getType().equals(Material.SPAWNER)) return;
        Block spawner = event.getBlockPlaced();
        if(AdvancedSpawners.townyApi()){
            if(!towny.hasBuildPermission(spawner,event.getPlayer())){
                event.getPlayer().sendMessage("Bu arazide bu işlemi yapamazsınız.");
                event.setCancelled(true);
                return;
            }
        }
        spawner.setMetadata("PLACED",new FixedMetadataValue(AdvancedSpawners.getInstance(),1));
        ItemStack spawnerItem = event.getItemInHand();
        Integer spawnerLevel = spawnerItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL"),PersistentDataType.INTEGER);
        spawner.setMetadata("LEVEL",new FixedMetadataValue(AdvancedSpawners.getInstance(),spawnerLevel));
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        EntityType entityType = EntityType.valueOf(spawnerItem.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(AdvancedSpawners.getInstance(),"SPAWNER_ENTITY_TYPE"),PersistentDataType.STRING));
        creatureSpawner.setSpawnedType(entityType);
        creatureSpawner.update();
        LevelHolograms.createSpawnerName(spawner,spawnerLevel);
        event.getPlayer().sendMessage(String.format("%d Spawner başarıyla yerleştirildi.",spawnerLevel));
    }
}
