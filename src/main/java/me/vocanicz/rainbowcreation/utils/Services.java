package me.vocanicz.rainbowcreation.utils;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.Config;
import me.vocanicz.rainbowcreation.datamanager.MySql;
import me.vocanicz.rainbowcreation.datamanager.Redis;

import java.util.*;

public class Services {
    private final Rainbowcreation plugin = Rainbowcreation.getInstance();
    private final Map<String, Boolean> service;
    private final List<String> toEnables = new ArrayList<>();

    public Services() {
        service = new HashMap<>();
    }
    private final List<String> allServices = new ArrayList<>(Arrays.asList("auto-update.pluginJarfile",
            "yml-data",
            "mySQL",
            "redis",
            "redis.acl-user",
            "misc.rollback"));

    public boolean get(String key) {
        return service.get(key);
    }

    public List<String> getEnables() {
        return toEnables;
    }

    public List<String> getKeys() {
        return allServices;
    }

    public void read(String serviceName) {
        boolean boo = plugin.defaultConfig.getBoolean(serviceName + ".enable");
        Console.info(serviceName + " " + boo);
        if (boo)
            toEnables.add(serviceName);
        service.put(serviceName, boo);
    }
    public void readAll() {
        for (String each: allServices) {
            read(each);
        }
    }

    public void enable(String serviceName) {
        switch (serviceName) {
            case ("redis"):
                plugin.redis = new Redis();
                break;
            case ("yml-data"):
                String prefix = plugin.defaultConfig.getString(serviceName + ".directory");
                String suffix = ".yml";
                plugin.itemdata = new Config(plugin, prefix + "\\playerData" + suffix);
                plugin.playerdata = new Config(plugin, prefix + "\\playerData" + suffix);
                plugin.serverdata = new Config(plugin, prefix + "\\serverData" + suffix);
                prefix = "gui";
                //do gui nested yml

                plugin.account = new Config(plugin, "password\\account" + suffix);
                break;
            case ("mySQL"):
                plugin.sql = new MySql();
                break;
        }
    }

    public void enableAll() {
        for (String each: toEnables) {
            enable(each);
        }
    }
}
