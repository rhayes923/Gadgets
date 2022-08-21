package com.ryan.gadgets.commands;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.items.TeleportStick;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TeleportStickCommand implements CommandExecutor {

    FileConfiguration config = Gadgets.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (config.getBoolean("enableTeleportStick")) {
                if (player.getInventory().firstEmpty() != -1) {
                    TeleportStick tpStick = new TeleportStick();
                    player.getInventory().addItem(tpStick.getItem());
                    player.sendMessage(ChatColor.RED + "[TeleportStick]" + ChatColor.LIGHT_PURPLE + " Given a Teleport Stick");
                } else {
                    player.sendMessage(ChatColor.RED + "[TeleportStick] No space in inventory!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "This item is disabled!");
            }
        }
        return true;
    }
}