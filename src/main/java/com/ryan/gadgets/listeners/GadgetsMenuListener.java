package com.ryan.gadgets.listeners;

import com.ryan.gadgets.items.DiscoBall;
import com.ryan.gadgets.items.Gadget;
import com.ryan.gadgets.items.GrapplingHook;
import com.ryan.gadgets.items.TeleportStick;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class GadgetsMenuListener extends GadgetListener implements Listener {

    final Gadget[] gadgets = { new TeleportStick(), new DiscoBall(), new GrapplingHook()};
    final HashMap<String, String> configValues = new HashMap<String, String>() {
        {
            for (Gadget gadget : gadgets) {
                put(gadget.getName(), "enable" + gadget.getClass().getSimpleName());
            }
        }
    };

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("Gadgets")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE) {
                cooldown.putIfAbsent(player.getUniqueId(), 0L);
                if (calculateTime(player) > 3000) {
                    if (player.getInventory().firstEmpty() != -1) {
                        String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                        if (config.getBoolean(configValues.get(name))) {
                            for (Gadget gadget : gadgets) {
                                if (gadget.getName().equalsIgnoreCase(name)) {
                                    giveGadget(player, gadget);
                                    return;
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "[Gadgets] This item is disabled!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "[Gadgets] No space in inventory!");
                    }
                } else {
                    int time = 3 - (int) calculateTime(player) / 1000;
                    player.sendMessage(ChatColor.RED + "You must wait " + time + (time > 1 ? " seconds!" : " second!"));
                }
            }
        }
    }

    public void giveGadget(Player player, Gadget gadget) {
        player.getInventory().addItem(gadget.getItem());
        player.sendMessage(ChatColor.RED + "[Gadgets]" + ChatColor.LIGHT_PURPLE + " Given a " + gadget.getName());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2F);
        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
