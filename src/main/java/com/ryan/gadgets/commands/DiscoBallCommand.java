package com.ryan.gadgets.commands;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.items.DiscoBall;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DiscoBallCommand implements CommandExecutor {

    final FileConfiguration config = Gadgets.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (config.getBoolean("enableDiscoBall")) {
                if (player.getInventory().firstEmpty() != -1) {
                    DiscoBall disco = new DiscoBall();
                    player.getInventory().addItem(disco.getItem());
                    player.sendMessage(ChatColor.RED + "[Gadgets]" + ChatColor.LIGHT_PURPLE + " Given a Disco Ball");
                } else {
                    player.sendMessage(ChatColor.RED + "[Gadgets] No space in inventory!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "[Gadgets] This item is disabled!");
            }
        }
        return true;
    }
}
