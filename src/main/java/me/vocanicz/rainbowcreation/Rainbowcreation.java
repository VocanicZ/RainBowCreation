package me.vocanicz.rainbowcreation;

import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.AtomicBool;
import me.vocanicz.rainbowcreation.datamanager.Config;
import me.vocanicz.rainbowcreation.datamanager.MySql;
import me.vocanicz.rainbowcreation.datamanager.Redis;
import me.vocanicz.rainbowcreation.thread.ServerThread;
import me.vocanicz.rainbowcreation.utils.Services;
import me.vocanicz.rainbowcreation.utils.Tester;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;

public final class Rainbowcreation extends JavaPlugin implements Listener {
    private static Rainbowcreation instance;

    public static final String BUILD_NUMBER = "86";

    public static Rainbowcreation getInstance() {return instance;}
    public FileConfiguration defaultConfig;
    public String defaultSql = "jdbc:mysql://vocanicz.thddns.net:2995/rainbowcreation";
    public Redis redis;
    public MySql sql;

    public Config account;
    public List<List<Config>> gui;
    public Config itemdata;
    public Config playerdata;
    public Config serverdata;

    public Services services;
    public AtomicBool locked = new AtomicBool();

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        defaultConfig = getConfig();
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
        if (services.getEnables().contains("mySQL")) {
            sql.close();
        }
    }
}
