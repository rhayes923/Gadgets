package com.ryan.gadgets.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public interface Gadget {
    
    ItemStack getItem();
    String getName();
    NamespacedKey getKey();
}
