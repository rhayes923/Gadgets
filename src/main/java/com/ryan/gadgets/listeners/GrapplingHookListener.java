package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class GrapplingHookListener extends GadgetListener implements Listener {

    NamespacedKey key = new NamespacedKey(Gadgets.getInstance(), "grappling-hook");
    HashMap<UUID, Boolean> usingGrapplingHook = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            usingGrapplingHook.putIfAbsent(e.getPlayer().getUniqueId(), false);
            usingGrapplingHook.put(e.getPlayer().getUniqueId(), checkGadget(e.getPlayer(), key, Material.FISHING_ROD));
            if (!config.getBoolean("enableGrapplingHook")) {
                e.getPlayer().sendMessage(ChatColor.RED + "This item is disabled!");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        if (!config.getBoolean("enableGrapplingHook")) {
            e.setCancelled(true);
            return;
        }

        if (usingGrapplingHook.get(e.getPlayer().getUniqueId())) {
            if (e.getState() != PlayerFishEvent.State.FISHING && e.getState() != PlayerFishEvent.State.REEL_IN) {
                e.getHook().remove();
                e.setCancelled(true);
            }

            if (e.getState() == PlayerFishEvent.State.REEL_IN ||
                    e.getState() == PlayerFishEvent.State.IN_GROUND ||
                    e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
                Player player = e.getPlayer();
                Vector playerVector = player.getLocation().toVector();
                Vector hookVector = e.getHook().getLocation().toVector();
                Vector velocity = hookVector.subtract(playerVector).normalize().multiply(1.5F);
                player.setVelocity(velocity);
            }
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        if (usingGrapplingHook.get(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
