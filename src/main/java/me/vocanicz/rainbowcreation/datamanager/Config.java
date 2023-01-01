package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.FileHandler;

public class Config {
    private final Rainbowcreation plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String file;

    public void saveDefaultConfig() {
        Console.info("");
        if (configFile == null) {
            Console.info("Config null");
            configFile = new File(plugin.getDataFolder()+File.separator+file.replace("/", ""));
        }
        Console.info("config = " + configFile.getPath());
        if (!configFile.exists()) {
            Console.info("File not exits");
            plugin.saveResource(file.substring(file.lastIndexOf("\\") + 1), false);
        }
    }
    public Config(Rainbowcreation plugin, String file) {
        Console.info("");
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
