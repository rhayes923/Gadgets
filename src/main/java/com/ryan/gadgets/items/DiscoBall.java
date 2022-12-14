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

public class DiscoBall extends ItemStack implements Gadget {

    static final NamespacedKey KEY = new NamespacedKey(Gadgets.getInstance(), "disco-ball");
    ItemStack disco;

    public DiscoBall() {
        disco = new ItemStack(Material.WHITE_STAINED_GLASS);
        ItemMeta itemMeta = disco.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(ChatColor.AQUA + "Disco Ball");
            itemMeta.setLore(Collections.singletonList(ChatColor.BLUE + "" + ChatColor.ITALIC + "Vibe"));
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, "Disco Ball");
            disco.setItemMeta(itemMeta);
        }
    }

    @Override
    public ItemStack getItem() {
        return disco;
    }

    @Override
    public String getName() {
        return "Disco Ball";
    }

    @Override
    public int getId() {
        return 11;
    }

    public static NamespacedKey getKey() {
        return KEY;
    }
}
