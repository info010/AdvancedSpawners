package org.info_0.advancedspawners.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.info_0.advancedspawners.AdvancedSpawners;

public class PoenaSagaSpawner {

    private static String name = AdvancedSpawners.getInstance().getConfig().getString("inventory-name-format").replace("&","§");

    public static ItemStack peonasagaSpawner(EntityType type, int level){
        ItemStack spawnerItem = new ItemStack(Material.SPAWNER,1);
        ItemMeta spawnerMeta = spawnerItem.getItemMeta();
        assert spawnerMeta != null;
        spawnerMeta.setDisplayName(ChatColor.GREEN + String.format(name,type.name(),level));
        spawnerMeta.getPersistentDataContainer().set(new NamespacedKey(AdvancedSpawners.getInstance(), "LEVEL"), PersistentDataType.INTEGER, level);
        spawnerMeta.getPersistentDataContainer().set(new NamespacedKey(AdvancedSpawners.getInstance(), "SPAWNER_ENTITY_TYPE"), PersistentDataType.STRING, type.name());
        spawnerItem.setItemMeta(spawnerMeta);
        return spawnerItem;
    }

}
