package com.ryan.gadgets.listeners;

import com.ryan.gadgets.Gadgets;
import com.ryan.gadgets.utils.Cooldown;
import com.ryan.gadgets.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;
import java.util.Random;

public class DiscoBallListener extends GadgetListener implements Listener {

    final Material[] COLORS = {Material.RED_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS,
            Material.GREEN_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS};

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getHand() == EquipmentSlot.HAND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (checkGadget(player, Utils.getKey("DiscoBall"), Material.WHITE_STAINED_GLASS)) {
                if (config.getBoolean("enableDiscoBall")) {
                    e.setCancelled(true);
                    if (!cooldowns.containsKey(player.getUniqueId())) {
                        Location location = player.getLocation().add(0, 3.5, 0);
                        if (location.getBlock().getType() == Material.AIR) {
                            Optional.ofNullable(location.getWorld()).ifPresent(loc -> loc.playSound(location, Sound.MUSIC_DISC_CHIRP, 3F, 1F));

                            ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(location.clone().subtract(0, 1.75, 0), EntityType.ARMOR_STAND);
                            as.setGravity(false);
                            as.setBasePlate(false);
                            as.setVisible(false);

                            new BukkitRunnable() {
                                int total = 0;
                                final Random random = new Random();
                                double x = 0;
                                double y = 0;
                                double z = 0;
                                double theta = 0;
                                final Location[] rotations = new Location[4];

                                @Override
                                public void run() {
                                    if (total % 10 == 0 && as.getEquipment() != null) {
                                        as.getEquipment().setHelmet(new ItemStack(COLORS[random.nextInt(6)]));
                                    }

                                    theta += Math.PI / 16;
                                    as.setHeadPose(as.getHeadPose().add(0, 0.1, 0));
                                    if (total % 2 == 0) {
                                        as.teleport(as.getLocation().clone().add(0, Math.sin(theta) * 0.025, 0));
                                    }

                                    rotations[0] = location.clone().add(Math.cos(theta), Math.sin(theta), Math.sin(theta));
                                    rotations[1] = location.clone().add(Math.cos(theta + Math.PI), Math.sin(theta), Math.sin(theta + Math.PI));

                                    rotations[2] = location.clone().add(Math.cos(theta), 0, Math.sin(theta));
                                    rotations[3] = location.clone().add(-Math.cos(theta), 0, -Math.sin(theta));

                                    for (Location rotationLoc : rotations) {
                                        location.getWorld().spawnParticle(Particle.REDSTONE, rotationLoc, 1, new Particle.DustOptions(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)), 0.75F));
                                    }

                                    if (total % 10 == 0) {
                                        for (int i = 0; i < 10; i++) {
                                            x = (random.nextDouble() * 2) - 1;
                                            y = (random.nextDouble() * 2) - 1;
                                            z = (random.nextDouble() * 2) - 1;
                                            for (int j = 1; j < 10; j++) {
                                                location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 0, j * x, j * y, j * z);
                                            }
                                        }
                                    }

                                    if (total >= 400) {
                                        this.cancel();
                                        as.remove();
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            player.stopSound(Sound.MUSIC_DISC_CHIRP);
                                        }
                                    }
                                    total++;
                                }
                            }.runTaskTimer(Gadgets.getInstance(), 0, 1);

                            new Cooldown(player, cooldowns, 30).runTaskTimer(Gadgets.getInstance(), 0, 20);
                            cooldowns.put(player.getUniqueId(), 30);
                        } else {
                            player.sendMessage(ChatColor.RED + "[Gadgets] Cannot use that here!");
                        }
                    } else {
                        int time = cooldowns.get(player.getUniqueId());
                        player.sendMessage(ChatColor.RED + "You must wait " + time + (time > 1 ? " seconds!" : " second!"));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "This item is disabled!");
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemMeta itemMeta = e.getItemInHand().getItemMeta();
        if (itemMeta != null && itemMeta.getPersistentDataContainer().has(Utils.getKey("DiscoBall"), PersistentDataType.STRING)) {
            e.setCancelled(true);
        }
    }
}