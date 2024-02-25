package org.info_0.advancedspawners;

import org.bukkit.plugin.java.JavaPlugin;
import org.info_0.advancedspawners.Entities.StackEntities;
import org.info_0.advancedspawners.api.NaturalSpawnerRemover;
import org.info_0.advancedspawners.commands.GiveSpawner;
import org.info_0.advancedspawners.listeners.BreakSpawner;
import org.info_0.advancedspawners.listeners.PlaceSpawner;
import org.info_0.advancedspawners.features.SetLevel;

public final class AdvancedSpawners extends JavaPlugin {

    private static AdvancedSpawners instance;

    public static boolean townyApi(){
        if (AdvancedSpawners.getInstance().getServer().getPluginManager().getPlugin("Towny") != null) return true;
        else return false;
    }

    public static AdvancedSpawners getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerEvents();
        registerCommands();
    }

    private void registerEvents(){
        instance.getServer().getPluginManager().registerEvents(new BreakSpawner(),instance);
        instance.getServer().getPluginManager().registerEvents(new SetLevel(),instance);
        instance.getServer().getPluginManager().registerEvents(new PlaceSpawner(),instance);
        instance.getServer().getPluginManager().registerEvents(new NaturalSpawnerRemover(),instance);
        instance.getServer().getPluginManager().registerEvents(new StackEntities(),instance);
    }

    private void registerCommands(){
        instance.getCommand("givespawner").setExecutor(new GiveSpawner());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
