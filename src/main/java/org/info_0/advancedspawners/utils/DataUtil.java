package org.info_0.advancedspawners.utils;

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

    public static void createSpawnerData(Block spawner, EntityType type, int level){
        if(hasSpawnerData(spawner)) return;
        UUID uuid = UUID.randomUUID();
        NamespacedKey uuidKey = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        NamespacedKey typeKey = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY_TYPE");
        if(!(spawner.getState() instanceof CreatureSpawner)) return;
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        creatureSpawner.getPersistentDataContainer().set(uuidKey, PersistentDataType.STRING, uuid.toString());
        creatureSpawner.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, type.name());
        setSpawnerLevel(spawner,level);
        creatureSpawner.update();
    }

    public static void setSpawnerLevel(Block spawner, int level){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL");
        if(!(spawner.getState() instanceof CreatureSpawner)) return;
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        creatureSpawner.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, level);
        creatureSpawner.update();
    }

    public static UUID getSpawnerUUID(Block spawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        if(!(spawner.getState() instanceof CreatureSpawner)) return null;
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        if(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING) == null) return null;
        String uuid = creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        assert uuid != null;
        return UUID.fromString(uuid);
    }

    public static int getSpawnerLevel(Block spawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"LEVEL");
        if(!(spawner.getState() instanceof CreatureSpawner)) return 0;
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        if(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == null) return 0;
        return creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static EntityType getSpawnerType(Block spawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"ENTITY_TYPE");
        if(!(spawner.getState() instanceof CreatureSpawner)) return null;
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        if(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING) == null) return null;
        return EntityType.valueOf(creatureSpawner.getPersistentDataContainer().get(key, PersistentDataType.STRING));
    }

    public static void setHologramID(Entity entity, UUID id){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        if(!entity.getType().equals(EntityType.ARMOR_STAND)) return;
        ArmorStand hologram = (ArmorStand) entity;
        hologram.getPersistentDataContainer().set(key, PersistentDataType.STRING, id.toString());
    }

    public static UUID getHologramID(Entity entity){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(),"UUID");
        if(!entity.getType().equals(EntityType.ARMOR_STAND)) return null;
        ArmorStand hologram = (ArmorStand) entity;
        if(hologram.getPersistentDataContainer().has(key)) return null;
        return UUID.fromString(hologram.getPersistentDataContainer().get(key, PersistentDataType.STRING));
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

    public static boolean hasSpawnerData(Block spawner){
        NamespacedKey key = new NamespacedKey(AdvancedSpawners.getInstance(), "UUID");
        if(!(spawner.getState() instanceof CreatureSpawner)) return false;
        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        return creatureSpawner.getPersistentDataContainer().has(key);
    }



}
