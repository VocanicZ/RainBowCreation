package me.vocanicz.rainbowcreation;

import org.bukkit.plugin.java.JavaPlugin;


public final class Rainbowcreation extends JavaPlugin {
    public static final String BUILD_NUMBER = "2";

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
