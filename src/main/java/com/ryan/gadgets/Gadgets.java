package com.ryan.gadgets;

import com.ryan.gadgets.commands.DiscoBallCommand;
import com.ryan.gadgets.commands.GadgetsMenuCommand;
import com.ryan.gadgets.commands.GrapplingHookCommand;
import com.ryan.gadgets.commands.TeleportStickCommand;
import com.ryan.gadgets.listeners.DiscoBallListener;
import com.ryan.gadgets.listeners.GadgetsMenuListener;
import com.ryan.gadgets.listeners.GrapplingHookListener;
import com.ryan.gadgets.listeners.TeleportStickListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Gadgets extends JavaPlugin {

    final FileConfiguration config = getConfig();
    static Gadgets instance;

    @Override
    public void onEnable() {
        instance = this;
        config.addDefault("enableTeleportStick", true);
        config.addDefault("teleportStickDistance", 20);
        config.addDefault("enableDiscoBall", true);
        config.addDefault("enableGrapplingHook", true);
        config.options().copyDefaults(true);
        saveConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new GadgetsMenuListener(), this);
        pm.registerEvents(new TeleportStickListener(), this);
        pm.registerEvents(new DiscoBallListener(), this);
        pm.registerEvents(new GrapplingHookListener(), this);
        getCommand("gadgets").setExecutor(new GadgetsMenuCommand());
        getCommand("teleportstick").setExecutor(new TeleportStickCommand());
        getCommand("discoball").setExecutor(new DiscoBallCommand());
        getCommand("grapplinghook").setExecutor(new GrapplingHookCommand());
    }

    @Override
    public void onDisable() {}

    public static Gadgets getInstance() {
        return instance;
    }
}
