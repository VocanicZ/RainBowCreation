package me.vocanicz.rainbowcreation;

import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.thread.Locker;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rainbowcreation extends JavaPlugin implements Listener {
    private static Rainbowcreation instance;

    public static final String BUILD_NUMBER = "6";

    public static Rainbowcreation getInstance() {return instance;}
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        Console.info("Setting multipaper Config");
        saveDefaultConfig();
        if (getConfig().getBoolean("auto-update")) {
            Console.info("auto-update: enable");
        } else {
            Console.info("auto-update: disable");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
}
