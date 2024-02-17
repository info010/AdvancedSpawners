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

import java.util.Collection;

import static org.bukkit.Bukkit.getServer;


public class StackEntities implements Listener{

    private final int maxStack = AdvancedSpawners.getInstance().getConfig().getInt("max-entity-stack");
    private final String entityName = AdvancedSpawners.getInstance().getConfig().getString("entity-name-format").replace("&","§");

    @EventHandler
    public void spawnSpawnerEntity(SpawnerSpawnEvent event){
        if(event.getSpawner().getBlock().getMetadata("PLACED").isEmpty()) return;
        CreatureSpawner spawner = event.getSpawner();
        Collection<Entity> entities = spawner.getWorld().getNearbyEntities(spawner.getLocation(),16,16,16);
        int level = spawner.getBlock().getMetadata("LEVEL").get(0).asInt();
        for(Entity entity : entities){
            if(entity.getMetadata("SPAWNER-ENTITY").isEmpty()) continue;
            if(!entity.getType().equals(spawner.getSpawnedType())) continue;
            int stackOfEntity = entity.getMetadata("ENTITY-STACK").get(0).asInt();
            updateSpawnerEntity(entity,stackOfEntity+level);
            event.setCancelled(true);
            return;
        }
        createSpawnerEntity(event.getEntity(),level);
    }

    @EventHandler
    public void killSpawnerEntity(EntityDeathEvent event){
        if(!event.getEntity().hasMetadata("SPAWNER-ENTITY")) return;
        if(event.getEntity().getMetadata("ENTITY-STACK").get(0).asInt() == 1) return;
        int stackOfEntity = event.getEntity().getMetadata("ENTITY-STACK").get(0).asInt();
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
        livingEntity.setMetadata("SPAWNER-ENTITY",new FixedMetadataValue(AdvancedSpawners.getInstance(),true));
        if(stack > maxStack)
            livingEntity.setMetadata("ENTITY-STACK",new FixedMetadataValue(AdvancedSpawners.getInstance(),maxStack));
        else
            livingEntity.setMetadata("ENTITY-STACK",new FixedMetadataValue(AdvancedSpawners.getInstance(),stack));
    }

    private void updateSpawnerEntity(Entity entity,int stack){
        String name = String.format(entityName,stack,entity.getType().name());
        entity.setCustomName(ChatColor.GREEN + name);
        if(stack > maxStack)
            entity.setMetadata("ENTITY-STACK",new FixedMetadataValue(AdvancedSpawners.getInstance(),maxStack));
        else
            entity.setMetadata("ENTITY-STACK",new FixedMetadataValue(AdvancedSpawners.getInstance(),stack));
    }
}
