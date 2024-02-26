package org.info_0.advancedspawners.utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.info_0.advancedspawners.AdvancedSpawners;

import java.util.UUID;

public class DataUtil {

    public static void createSpawnerData(CreatureSpawner creatureSpawner, int level, UUID id){
        if(id == null)  id = UUID.randomUUID();
        EntityType type = creatureSpawner.getSpawnedType();
        NamespacedKey uuidKey = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        NamespacedKey typeKey = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY_TYPE");
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL");

        creatureSpawner.getPersistentDataContainer().set(uuidKey, PersistentDataType.STRING, id.toString());
        creatureSpawner.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, type.name());
        creatureSpawner.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, level);

        creatureSpawner.update();
    }

    public static UUID getSpawnerUUID(CreatureSpawner creatureSpawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        if(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING) == null) return null;
        String uuid = creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return UUID.fromString(uuid);
    }

    public static int getSpawnerLevel(CreatureSpawner creatureSpawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL");
        if(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == null) return 0;
        return creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static EntityType getSpawnerType(CreatureSpawner creatureSpawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY_TYPE");
        if(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING) == null) return null;
        return EntityType.valueOf(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING));
    }

    public static void setHologramID(Entity entity, UUID id){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        if(!entity.getType().equals(EntityType.ARMOR_STAND)) return;
        entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, id.toString());
    }

    public static UUID getHologramID(Entity entity){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        if(!entity.getType().equals(EntityType.ARMOR_STAND)) return null;
        if(!entity.getPersistentDataContainer().has(key)) return null;
        return UUID.fromString(entity.getPersistentDataContainer().get(key, PersistentDataType.STRING));
    }

    public static void setEntityStack(Entity entity, int stack){
        NamespacedKey stackKey = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY-STACK");
        entity.getPersistentDataContainer().set(stackKey, PersistentDataType.INTEGER, stack);
    }

    public static int getEntityStack(Entity entity){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY-STACK");
        if(!(entity instanceof LivingEntity)) return 0;
        if(entity.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == null) return 0;
        return entity.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static boolean hasEntityData(Entity entity){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY-STACK");
        return entity.getPersistentDataContainer().has(key);
    }

    public static boolean hasSpawnerData(CreatureSpawner creatureSpawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(), "UUID");
        return creatureSpawner.getPersistentDataContainer().has(key);
    }



}
