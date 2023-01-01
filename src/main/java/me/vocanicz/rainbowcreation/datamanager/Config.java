package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

public class Config {
    private final Rainbowcreation plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String file;

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder()+File.separator+file);
        }
        Console.info("config = " + configFile.getPath());
        if (!configFile.exists()) {
            try {
                Path file2 = configFile.toPath();
                if (!Files.exists(file2)) {
                    Files.createDirectories(file2.getParent());
                    Files.copy(Objects.requireNonNull(plugin.getResource(file.replace("\\", "/"))), file2);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Config(Rainbowcreation plugin, String file) {
        Console.info("");
        this.plugin = plugin;
        this.file = file;
        saveDefaultConfig();
    }

    public void reloadConfig() throws InterruptedException {
        if (configFile == null)
            configFile = new File(plugin.getDataFolder(), file);
        plugin.locked.lock();
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource(file);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
        plugin.locked.unlock();
    }
    public FileConfiguration getConfig() throws InterruptedException {
        if (dataConfig == null)
            reloadConfig();
        return dataConfig;
    }

    public void saveConfig() {
        try {
            if (dataConfig == null || configFile == null)
                return;
            plugin.locked.lock();
            getConfig().save(configFile);
        } catch (Exception ignored) {}
        plugin.locked.unlock();
    }

    public Object get(UUID uuid, String key) throws InterruptedException {
        return getConfig().get("player." + uuid + "." + key);
    }

    public void set(UUID uuid, String key, String value) throws InterruptedException {
        getConfig().set("player." + uuid + "." + key, value);
    }

    public boolean hasData(UUID uuid, String key) throws InterruptedException {
        return getConfig().contains("player." + uuid + "." + key);
    }
}
