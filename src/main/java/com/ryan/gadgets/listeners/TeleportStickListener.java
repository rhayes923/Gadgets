package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class TeleportStickListener implements Listener {

    FileConfiguration config = Gadgets.getInstance().getConfig();
    HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    final NamespacedKey KEY = new NamespacedKey(Gadgets.getInstance(), "teleport-stick");

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getHand() == EquipmentSlot.HAND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (config.getBoolean("enableTeleportStick")) {
                if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                    if (container.has(KEY, PersistentDataType.STRING)) {
                        cooldown.putIfAbsent(player.getUniqueId(), 0L);
                        if (System.currentTimeMillis() - cooldown.get(player.getUniqueId()) > 1000) {
                            Block block = player.getTargetBlockExact(config.getInt("teleportStickDistance"), FluidCollisionMode.NEVER);
                            Location location = player.getLocation();
                            if (block != null && block.getType() != Material.AIR) {
                                location = block.getLocation();
                                location.add(0.5, 1, 0.5);
                                location.setYaw(player.getLocation().getYaw());
                                location.setPitch(player.getLocation().getPitch());
                            } else {
                                Vector direction = location.getDirection();
                                direction.normalize();
                                direction.multiply(config.getInt("teleportStickDistance"));
                                location.add(direction);
                            }
                            Location finalLocation = location;

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.teleport(finalLocation);
                                }
                            }.runTask(Gadgets.getInstance());

                            player.sendMessage(ChatColor.RED + "[TeleportStick]" + ChatColor.LIGHT_PURPLE + " Teleporting...");
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3.0f, 0.5f);

                            new BukkitRunnable() {
                                int total = 0;

                                @Override
                                public void run() {
                                    player.spawnParticle(Particle.SPELL_MOB, player.getLocation(), 0, 200 / 255D, 50 / 255D, 200 / 255D, 1);
                                    if (total >= 15) {
                                        this.cancel();
                                    }
                                    total++;
                                }
                            }.runTaskTimer(Gadgets.getInstance(), 0, 0);

                            cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                        } else {
                            player.sendMessage(ChatColor.RED + "You must wait 1 second!");
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "This item is disabled!");
            }
        }
    }
}
