package org.info_0.advancedspawners.Entities;

import org.bukkit.ChatColor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.info_0.advancedspawners.AdvancedSpawners;
import org.info_0.advancedspawners.utils.DataUtil;

import java.util.Collection;

import static org.bukkit.Bukkit.getServer;


public class StackEntities implements Listener{

    private final int maxStack = AdvancedSpawners.getInstance().getConfig().getInt("max-entity-stack");
    private final String entityName = AdvancedSpawners.getInstance().getConfig().getString("entity-name-format").replace("&","§");

    @EventHandler
    public void spawnSpawnerEntity(SpawnerSpawnEvent event){
        if(!DataUtil.hasSpawnerData(event.getSpawner()) || event.getSpawner() == null) return;
        CreatureSpawner spawner = event.getSpawner();
        Collection<Entity> entities = spawner.getWorld().getNearbyEntities(spawner.getLocation(),16,16,16);
        int level = DataUtil.getSpawnerLevel(event.getSpawner());
        for(Entity entity : entities){
            if(!DataUtil.hasEntityData(entity)) continue;
            if(!entity.getType().equals(spawner.getSpawnedType())) continue;
            int stackOfEntity = DataUtil.getEntityStack(entity);
            updateSpawnerEntity(entity,stackOfEntity+level);
            event.setCancelled(true);
            return;
        }
        createSpawnerEntity(event.getEntity(),level);
    }

    @EventHandler
    public void killSpawnerEntity(EntityDeathEvent event){
        if(!DataUtil.hasEntityData(event.getEntity())) return;
        if(DataUtil.getEntityStack(event.getEntity()) == 1) return;
        int stackOfEntity = DataUtil.getEntityStack(event.getEntity());
        LivingEntity livingEntity = (LivingEntity) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(),event.getEntityType());
        createSpawnerEntity(livingEntity,stackOfEntity-1);
        event.getEntity().remove();
    }

    private void createSpawnerEntity(Entity entity, int stack){
        if(entity.getVehicle() != null) entity.getVehicle().remove();
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setCanPickupItems(false);
        livingEntity.setCustomNameVisible(true);
        livingEntity.setSwimming(false);
        livingEntity.setSilent(true);
        String name = String.format(entityName,entity.getType().name(),stack);
        livingEntity.setCustomName(ChatColor.GREEN + name);
        if(stack > maxStack)
            DataUtil.setEntityStack(entity, maxStack);
        else
            DataUtil.setEntityStack(entity, stack);
    }

    private void updateSpawnerEntity(Entity entity,int stack){
        String name = String.format(entityName,entity.getType().name(),stack);
        entity.setCustomName(ChatColor.GREEN + name);
        if(stack > maxStack)
            DataUtil.setEntityStack(entity, maxStack);
        else
            DataUtil.setEntityStack(entity, stack);
    }
}
