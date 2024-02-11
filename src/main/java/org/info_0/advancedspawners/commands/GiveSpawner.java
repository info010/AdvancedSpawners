package org.info_0.advancedspawners.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;
import static org.info_0.advancedspawners.items.PoenaSagaSpawner.peonasagaSpawner;

public class GiveSpawner implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 2){
            commandSender.sendMessage(ChatColor.YELLOW+"Kullanımı /givespawner <oyuncu> <spawner-tipi>");
            return false;
        }
        Player player = (Player) commandSender;
        if(Bukkit.getPlayer(strings[0]) == null){
            player.sendMessage("Oyuncu bulunamadı.");
            return false;
        }
        try {
            Bukkit.getPlayer(strings[0]).getInventory().addItem(peonasagaSpawner(EntityType.valueOf(strings[1]),1));
            getServer().getLogger().severe(String.format("%s ,bir %s spawner aldı.",strings[0],strings[1]));
            return true;
        }catch (RuntimeException e){
            player.sendMessage("Yaratık tipi geçerli değil. https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
            throw new RuntimeException();
        }
    }
}
