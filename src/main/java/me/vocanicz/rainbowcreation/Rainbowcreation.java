package me.vocanicz.rainbowcreation;

import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.Config;
import me.vocanicz.rainbowcreation.datamanager.Redis;
import me.vocanicz.rainbowcreation.thread.ServerThread;
import me.vocanicz.rainbowcreation.utils.Services;
import me.vocanicz.rainbowcreation.utils.Tester;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rainbowcreation extends JavaPlugin implements Listener {
    private static Rainbowcreation instance;

    public static final String BUILD_NUMBER = "58";

    public static Rainbowcreation getInstance() {return instance;}
    public Redis redis;

    public Config account;
    public Config link;
    public Config itemdata;
    public Config playerdata;
    public Config serverdata;

    public Services services;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        Console.info("current build" + BUILD_NUMBER);
        services = new Services();
        services.readAll();
        services.enableAll();
        Tester tester = new Tester();
        Console.info("Test result -> " + tester.run());
        new ServerThread().run();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
}
