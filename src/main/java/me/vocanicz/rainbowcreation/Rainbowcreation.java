package me.vocanicz.rainbowcreation;

import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.Redis;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rainbowcreation extends JavaPlugin implements Listener {
    private static Rainbowcreation instance;

    public static final String BUILD_NUMBER = "16";

    public static Rainbowcreation getInstance() {return instance;}
    public Redis redis;
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        Console.info("Setting multipaper Config");
        saveDefaultConfig();
        if (getConfig().getBoolean("auto-update")) {
            Console.info("auto-update: enable");
        } else {
            Console.info("auto-update: disable");
        }
        if (getConfig().getBoolean("redis.enable")) {
            Console.info("connecting to redis.");
            redis = new Redis();
            Console.info("ping -> redis");
            Console.info("redis -> " + redis.ping());
            Console.info("testing atomicbool..");
            if (!redis.locked()) {
                redis.Lock();
                if (redis.set("locked", "0")) {
                    Console.info("ERROR ATOMIC DOES NOT LOCK REDIS");
                } else {
                    Console.info("passed!");
                }
                redis.Unlock();
            }
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
}
