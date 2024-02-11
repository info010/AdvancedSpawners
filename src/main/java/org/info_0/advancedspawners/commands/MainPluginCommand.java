package org.info_0.advancedspawners.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPluginCommand implements TabCompleter , CommandExecutor {

    private final List<String> commands = Arrays.asList("reload","spawner");

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1){
            return commands;
        }
        if(args.length == 2 && commands.contains(args[0])){
            return playerNames();
        }
        if(args.length == 3 && commands.contains(args[0]) && playerNames().contains(args[1])){
            if (args[0].equalsIgnoreCase("spawner")) return null;
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;
        if(args.length < 1) return false;
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("spawner")){

            }
        }
        return true;
    }

    private List<String> playerNames(){
        List<String> names = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.isEmpty()) continue;
            names.add(p.getName());
        }
        return names;
    }
}
