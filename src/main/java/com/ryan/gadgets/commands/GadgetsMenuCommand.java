package com.ryan.gadgets.commands;

import com.ryan.gadgets.items.Gadget;
import com.ryan.gadgets.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GadgetsMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory gadgetsMenu = Bukkit.createInventory(player, 27, "Gadgets");

            ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta itemMeta = glass.getItemMeta();
            itemMeta.setDisplayName(ChatColor.DARK_GRAY + "");
            glass.setItemMeta(itemMeta);
            ItemStack[] emptyContents = new ItemStack[gadgetsMenu.getSize()];
            Arrays.fill(emptyContents, glass);
            gadgetsMenu.setContents(emptyContents);

            int i = 10;
            for (Gadget gadget : Utils.getGadgets()) {
                gadgetsMenu.setItem(i++, gadget.getItem());
            }

            player.openInventory(gadgetsMenu);
        }
        return true;
    }
}
