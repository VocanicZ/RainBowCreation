package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class Config {
    private final Rainbowcreation plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String file;

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), file);
        }
        if (!configFile.exists()) {
            plugin.saveResource(file, false);
        }
    }
    public Config(Rainbowcreation plugin, String file) {
        this.plugin = plugin;
        this.file = file;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), file);
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource(file);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }
    public FileConfiguration getConfig() {
        if (dataConfig == null)
            reloadConfig();
        return dataConfig;
    }

    public void saveConfig() {
        try {
            if (dataConfig == null || configFile == null)
                return;
            getConfig().save(configFile);
        } catch (Exception ignored) {}
    }

    public Object get(UUID uuid, String key) {
        return getConfig().get("player." + uuid + "." + key);
    }

    public void set(UUID uuid, String key, String value) {
        getConfig().set("player." + uuid + "." + key, value);
    }

    public boolean hasData(UUID uuid, String key) {
        return getConfig().contains("player." + uuid + "." + key);
    }
}
