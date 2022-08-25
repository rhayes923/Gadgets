package com.ryan.gadgets.listeners;

import com.ryan.gadgets.items.DiscoBall;
import com.ryan.gadgets.items.TeleportStick;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GadgetsMenuListener extends GadgetListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("Gadgets")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                cooldown.putIfAbsent(player.getUniqueId(), 0L);
                if (calculateTime(player) > 3000) {
                    if (player.getInventory().firstEmpty() != -1) {
                        switch(e.getCurrentItem().getType()) {
                            case STICK:
                                if (config.getBoolean("enableTeleportStick")) {
                                    TeleportStick tpStick = new TeleportStick();
                                    player.getInventory().addItem(tpStick.getItem());
                                    player.sendMessage(ChatColor.RED + "[Gadgets]" + ChatColor.LIGHT_PURPLE + " Given a Teleport Stick");
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2F);
                                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                } else {
                                    player.sendMessage(ChatColor.RED + "[Gadgets] This item is disabled!");
                                }
                                break;
                            case WHITE_STAINED_GLASS:
                                if (config.getBoolean("enableDiscoBall")) {
                                    DiscoBall disco = new DiscoBall();
                                    player.getInventory().addItem(disco.getItem());
                                    player.sendMessage(ChatColor.RED + "[Gadgets]" + ChatColor.LIGHT_PURPLE + " Given a Disco Ball");
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2F);
                                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                } else {
                                    player.sendMessage(ChatColor.RED + "[Gadgets] This item is disabled!");
                                }
                                break;
                        }
                        //More gadgets here
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
}
