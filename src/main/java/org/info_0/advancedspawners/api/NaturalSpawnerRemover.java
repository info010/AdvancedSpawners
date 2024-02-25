package org.info_0.advancedspawners.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.info_0.advancedspawners.AdvancedSpawners;
import org.info_0.advancedspawners.utils.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class NaturalSpawnerRemover implements Listener {

    private final FileConfiguration config = AdvancedSpawners.getInstance().getConfig();

    private List<World> worldList(){
        List<World> worlds = new ArrayList<>();
        for(String string : config.getStringList("natural-spawner-remover.worlds")){
            if(!Bukkit.getWorlds().contains(Bukkit.getWorld(string))) continue;
            worlds.add(Bukkit.getWorld(string));
        }
        return worlds;
    }

    @EventHandler
    public void naturalSpawnerLoad(ChunkPopulateEvent event){
        if(!worldList().contains(event.getWorld())) return;
        BlockState[] blockStates = event.getChunk().getTileEntities();
        for(BlockState blockState : blockStates){
            if(blockState.getBlock().getType().equals(Material.SPAWNER)) continue;
            if(DataUtil.hasSpawnerData(blockState.getBlock())) continue;
            blockState.getBlock().setType(Material.AIR);
            Location location = blockState.getLocation();
            String loc = String.format("[%s, %s, %s, %s]",location.getWorld().getName(),location.getX(),location.getY(),location.getZ());
            Bukkit.getLogger().warning(String.format("%s konumundaki doğal spawner başarıyla kaldırldı.",loc));
        }
    }

}
