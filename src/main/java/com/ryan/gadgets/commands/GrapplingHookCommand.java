package com.ryan.gadgets.commands;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.items.GrapplingHook;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GrapplingHookCommand implements CommandExecutor {

    FileConfiguration config = Gadgets.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (config.getBoolean("enableGrapplingHook")) {
                if (player.getInventory().firstEmpty() != -1) {
                    GrapplingHook grapplingHook = new GrapplingHook();
                    player.getInventory().addItem(grapplingHook.getItem());
                    player.sendMessage(ChatColor.RED + "[Gadgets]" + ChatColor.LIGHT_PURPLE + " Given a Grappling Hook");
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
