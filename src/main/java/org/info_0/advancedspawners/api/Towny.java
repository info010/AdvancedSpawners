package org.info_0.advancedspawners.api;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Towny {
    public static boolean hasDestroyPermission(Block block,Player player){
        return PlayerCacheUtil.getCachePermission(player,block.getLocation(),block.getType(), TownyPermission.ActionType.DESTROY);
    }

    public static boolean hasBuildPermission(Block block,Player player){
        return PlayerCacheUtil.getCachePermission(player,block.getLocation(),block.getType(), TownyPermission.ActionType.BUILD);
    }

    public static boolean hasItemUsePermission(Block block,Player player){
        return PlayerCacheUtil.getCachePermission(player,block.getLocation(),block.getType(),TownyPermission.ActionType.ITEM_USE);
    }

}
