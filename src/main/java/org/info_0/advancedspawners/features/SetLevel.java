package org.info_0.advancedspawners.features;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.info_0.advancedspawners.AdvancedSpawners;
import org.info_0.advancedspawners.api.Towny;
import org.info_0.advancedspawners.utils.DataType;
import org.info_0.advancedspawners.utils.DataUtil;

import javax.xml.crypto.Data;
import java.util.UUID;

public class SetLevel implements Listener {

    private FileConfiguration config = AdvancedSpawners.getInstance().getConfig();

    @EventHandler
    public void setSpawnerLevel(PlayerInteractEvent event){
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if(!event.getClickedBlock().getType().equals(Material.SPAWNER)) return;
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SPAWNER)) return;
        if(AdvancedSpawners.townyApi()){
            if(!Towny.hasItemUsePermission(event.getClickedBlock(),event.getPlayer())){
                event.getPlayer().sendMessage("Bu arazide bu işlemi yapamazsınız.");
                event.setCancelled(true);
                return;
            }
        }
        ItemStack spawnerItem = event.getPlayer().getInventory().getItemInMainHand();
        Block spawner = event.getClickedBlock();
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        if(!DataUtil.hasSpawnerData(creatureSpawner) || !spawnerItem.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL"), PersistentDataType.INTEGER)){
            event.getPlayer().sendMessage("Bu işlem yalnızca siteden satın alınan Spawnerlar için geçerlidir.");
            return;
        }
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"SPAWNER_ENTITY_TYPE");

        if(!spawnerItem.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING).equals(creatureSpawner.getSpawnedType().name())) {
            event.getPlayer().sendMessage("Elindeki spawner ile tıkladığın spawner aynı türden değil.");
            return;
        }
        int spawnerItemLevel = spawnerItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(AdvancedSpawners.getInstance(), "LEVEL"), PersistentDataType.INTEGER);
        int newSpawnerLevel =  DataUtil.getSpawnerLevel(creatureSpawner)+spawnerItemLevel;
        if (newSpawnerLevel > 100){
            event.getPlayer().sendMessage("Birleştirmek istediğiniz spawnerlar seviye sınırını aşıyor.");
            event.setCancelled(true);
            return;
        }
        UUID uuid = DataUtil.getSpawnerUUID(creatureSpawner);
        DataUtil.createSpawnerData(creatureSpawner, newSpawnerLevel, uuid);
        LevelHolograms.updateLevelName(spawner,newSpawnerLevel);
        if(config.getBoolean("level-up-particle.enable")) spawner.getWorld().spawnParticle(Particle.valueOf(config.getString("level-up-particle.particle")),spawner.getLocation(),25,0.5,0,0.5);
        int newAmount = event.getPlayer().getInventory().getItemInMainHand().getAmount()-1;
        event.getPlayer().getInventory().getItemInMainHand().setAmount(newAmount);
        event.setCancelled(true);
    }
}
