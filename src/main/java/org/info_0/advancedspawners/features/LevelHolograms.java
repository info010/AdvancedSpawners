package org.info_0.advancedspawners.features;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.info_0.advancedspawners.AdvancedSpawners;

import java.util.ArrayList;
import java.util.List;

public class LevelHolograms {

    private static final String spawnerName = AdvancedSpawners.getInstance().getConfig().getString("spawner-name-format").replace("&","§");

    private static ChatColor spawnerColor(int level){
        if(level >= 0 && level < 10) return ChatColor.YELLOW;
        if(level >= 10 && level < 20) return ChatColor.GOLD;
        if(level >= 20 && level < 30) return ChatColor.GREEN;
        if(level >= 30 && level < 40) return ChatColor.DARK_GREEN;
        if(level >= 40 && level < 50) return ChatColor.AQUA;
        if(level >= 50 && level < 60) return ChatColor.DARK_AQUA;
        if(level >= 60 && level < 70) return ChatColor.LIGHT_PURPLE;
        if(level >= 70 && level < 80) return ChatColor.DARK_PURPLE;
        if(level >= 80 && level < 90) return ChatColor.RED;
        if(level >= 90 && level < 100) return ChatColor.DARK_RED;
        return ChatColor.DARK_BLUE;
    }

    public static void createSpawnerName(Block block, int level){
        ArmorStand spawnerNameHologram = (ArmorStand) block.getWorld().spawnEntity(block.getLocation().add(0.5,0,0.5), EntityType.ARMOR_STAND);
        spawnerNameHologram.setMetadata("SPAWNER-ID",new FixedMetadataValue(AdvancedSpawners.getInstance(),block.getLocation().toString()));
        CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
        spawnerNameHologram.setCollidable(false);
        spawnerNameHologram.setVisible(false);
        spawnerNameHologram.setBasePlate(false);
        spawnerNameHologram.setSmall(true);
        spawnerNameHologram.setCustomNameVisible(true);
        spawnerNameHologram.setGravity(false);
        spawnerNameHologram.setCustomName(spawnerColor(level) + String.format(spawnerName,creatureSpawner.getSpawnedType().name(),level));
    }

    public static void delSpawnerName(Block block){
        ArrayList<Entity> entities = (ArrayList<Entity>) block.getWorld().getNearbyEntities(block.getLocation(),1,1,1);
        String spawnerID = block.getLocation().toString();
        for(Entity entity: entities){
            if(!(entity instanceof ArmorStand)) continue;
            ArmorStand stand = (ArmorStand) entity;
            List<MetadataValue> metadata = stand.getMetadata("SPAWNER-ID");
            if (metadata.isEmpty() || !metadata.get(0).asString().equals(spawnerID)) continue;
            stand.remove();
            break;
        }
    }

    public static void updateLevelName(Block block, int newlevel){
        delSpawnerName(block);
        if(newlevel >= 1){
            createSpawnerName(block,newlevel);
        }
    }
}
