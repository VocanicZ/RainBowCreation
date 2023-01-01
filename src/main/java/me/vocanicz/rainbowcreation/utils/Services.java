package me.vocanicz.rainbowcreation.utils;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.Config;
import me.vocanicz.rainbowcreation.datamanager.Redis;
import org.checkerframework.checker.units.qual.C;

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
        boolean boo = plugin.getConfig().getBoolean(serviceName + ".enable");
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
        if (serviceName.equals("redis")) {
            plugin.redis = new Redis();
            plugin.redis.auth(plugin.getConfig().getString("redis.password"));
        } else if (serviceName.equals("yml-data")){
            plugin.account = new Config(plugin, "account.yml");
            plugin.itemdata = new Config(plugin, "/data\\itemData.yml");
            plugin.playerdata = new Config(plugin, "/data\\playerData.yml");
            plugin.serverdata = new Config(plugin, "/data\\serverData.yml");
            plugin.link = new Config(plugin, "link.yml");
        }
    }

    public void enableAll() {
        for (String each: toEnables) {
            enable(each);
        }
    }
}
