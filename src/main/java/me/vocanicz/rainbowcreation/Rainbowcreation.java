package me.vocanicz.rainbowcreation;

import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.Redis;
import me.vocanicz.rainbowcreation.thread.ServerThread;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rainbowcreation extends JavaPlugin implements Listener {
    private static Rainbowcreation instance;

    public static final String BUILD_NUMBER = "21";

    public static Rainbowcreation getInstance() {return instance;}
    public Redis redis;
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        if (getConfig().getBoolean("auto-update")) {
            Console.info("auto-update: enable");
        } else {
            Console.info("auto-update: disable");
        }
        if (getConfig().getBoolean("redis.enable")) {
            redis = new Redis();
            Console.info("redis -> " + redis.ping());
            if (!redis.locked()) {
                redis.Lock();
                if (redis.set("locked", "0")) {
                    Console.info("ERROR ATOMIC DOES NOT LOCK REDIS");
                }
                redis.Unlock();
            }
        }
        new ServerThread().run();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
}
