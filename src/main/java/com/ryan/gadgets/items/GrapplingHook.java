package com.ryan.gadgets.items;

import com.ryan.gadgets.Gadgets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public class GrapplingHook extends ItemStack implements Gadget {

    final NamespacedKey KEY = new NamespacedKey(Gadgets.getInstance(), "grappling-hook");
    ItemStack grapplingHook;

    public GrapplingHook() {
        grapplingHook = new ItemStack(Material.FISHING_ROD);
        ItemMeta itemMeta = grapplingHook.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(ChatColor.GREEN + "Grappling Hook");
            itemMeta.setLore(Collections.singletonList(ChatColor.DARK_GREEN + "Travel where you can reach"));
            itemMeta.addEnchant(Enchantment.LURE, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, "Grappling Hook");
            grapplingHook.setItemMeta(itemMeta);
        }
    }

    @Override
    public ItemStack getItem() {
        return grapplingHook;
    }

    @Override
    public String getName() {
        return "Grappling Hook";
    }

    @Override
    public NamespacedKey getKey() {
        return KEY;
    }
}
