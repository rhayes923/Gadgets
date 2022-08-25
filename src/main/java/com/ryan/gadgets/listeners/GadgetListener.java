package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public abstract class GadgetListener {

    static FileConfiguration config = Gadgets.getInstance().getConfig();
    HashMap<UUID, Long> cooldown = new HashMap<>();

    public boolean checkGadget(PlayerInteractEvent e, Player player, String enabled, NamespacedKey KEY, Material material) {
        if (e.getHand() == EquipmentSlot.HAND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (config.getBoolean(enabled)) {
                if (player.getInventory().getItemInMainHand().getType() == material) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                    if (container.has(KEY, PersistentDataType.STRING)) {
                        cooldown.putIfAbsent(player.getUniqueId(), 0L);
                        return true;
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "This item is disabled!");
            }
        }
        return false;
    }

    public long calculateTime(Player player) {
        return System.currentTimeMillis() - cooldown.get(player.getUniqueId());
    }
}
