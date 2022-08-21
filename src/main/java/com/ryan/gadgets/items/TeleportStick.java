package com.ryan.gadgets.items;

import com.ryan.gadgets.Gadgets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class TeleportStick extends ItemStack {

    final NamespacedKey KEY = new NamespacedKey(Gadgets.getInstance(), "teleport-stick");
    ItemStack tpStick;

    public TeleportStick() {
        tpStick = new ItemStack(Material.STICK);
        ItemMeta itemMeta = tpStick.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport Stick");
        itemMeta.setLore(Arrays.asList("A Teleport Stick"));
        itemMeta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, "Teleport Stick");
        tpStick.setItemMeta(itemMeta);
    }

    public ItemStack getItem() {
        return tpStick;
    }
}
