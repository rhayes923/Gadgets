package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GadgetListener {

    static final FileConfiguration config = Gadgets.getInstance().getConfig();
    final Map<UUID, Integer> cooldowns = new HashMap<>();

    public boolean checkGadget(Player player, NamespacedKey KEY, Material material) {
        if (player.getInventory().getItemInMainHand().getType() == material) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                return itemMeta.getPersistentDataContainer().has(KEY, PersistentDataType.STRING);
            }
        }
        return false;
    }
}
