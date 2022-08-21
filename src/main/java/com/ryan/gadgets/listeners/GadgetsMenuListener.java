package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.items.TeleportStick;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.UUID;

public class GadgetsMenuListener implements Listener {

    FileConfiguration config = Gadgets.getInstance().getConfig();
    final HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("Gadgets")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                cooldown.putIfAbsent(player.getUniqueId(), 0L);
                if (calculateTime(player) > 3000) {
                    if (e.getCurrentItem().getType() == Material.STICK) {
                        if (config.getBoolean("enableTeleportStick")) {
                            if (player.getInventory().firstEmpty() != -1) {
                                TeleportStick tpStick = new TeleportStick();
                                player.getInventory().addItem(tpStick.getItem());
                                player.sendMessage(ChatColor.RED + "[TeleportStick]" + ChatColor.LIGHT_PURPLE + " Given a Teleport Stick");
                                cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                            } else {
                                player.sendMessage(ChatColor.RED + "[TeleportStick] No space in inventory!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "This item is disabled!");
                        }
                        //More gadgets here
                    }
                } else {
                    int time = 3 - (int) calculateTime(player) / 1000;
                    player.sendMessage(ChatColor.RED + "You must wait " + time + (time > 1 ? " seconds!" : " second!"));
                }
            }
        }
    }

    public long calculateTime(Player player) {
        return System.currentTimeMillis() - cooldown.get(player.getUniqueId());
    }
}
