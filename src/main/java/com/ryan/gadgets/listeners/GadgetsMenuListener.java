package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.items.Gadget;
import com.ryan.gadgets.utils.Cooldown;
import com.ryan.gadgets.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class GadgetsMenuListener extends GadgetListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("Gadgets")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE) {
                if (!cooldowns.containsKey(player.getUniqueId())) {
                    if (player.getInventory().firstEmpty() != -1) {
                        String name = ChatColor.stripColor(Optional.ofNullable(e.getCurrentItem().getItemMeta())
                                .map(ItemMeta::getDisplayName).orElse("None"));
                        for (Gadget gadget : Utils.getGadgets()) {
                            if (gadget.getName().equalsIgnoreCase(name)) {
                                if (config.getBoolean("enable" + name.replaceAll("\\s", ""))) {
                                    giveGadget(player, gadget);
                                    return;
                                } else {
                                    player.sendMessage(ChatColor.RED + "[Gadgets] This item is disabled!");
                                }
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "[Gadgets] No space in inventory!");
                    }
                } else {
                    int time = cooldowns.get(player.getUniqueId());
                    player.sendMessage(ChatColor.RED + "You must wait " + time + (time > 1 ? " seconds!" : " second!"));
                }
            }
        }
    }

    public void giveGadget(Player player, Gadget gadget) {
        player.getInventory().addItem(gadget.getItem());
        player.sendMessage(ChatColor.RED + "[Gadgets]" + ChatColor.LIGHT_PURPLE + " Given a " + gadget.getName());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2F);
        new Cooldown(player, cooldowns, 3).runTaskTimer(Gadgets.getInstance(), 0, 20);
        cooldowns.put(player.getUniqueId(), 3);
    }
}
