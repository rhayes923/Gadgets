package com.ryan.gadgets.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class Cooldown extends BukkitRunnable {

    final Player player;
    final Map<UUID, Integer> cooldowns;
    int time;

    public Cooldown(Player player, Map<UUID, Integer> cooldowns, int time) {
        this.player = player;
        this.cooldowns = cooldowns;
        this.time = time;
    }

    @Override
    public void run() {
        if (time <= 0) {
            cooldowns.remove(player.getUniqueId());
            cancel();
        } else {
            cooldowns.put(player.getUniqueId(), time--);
        }
    }
}
