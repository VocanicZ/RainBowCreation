package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.utils.Services;
import org.bukkit.configuration.file.FileConfiguration;

public class DataManager {
    private final Rainbowcreation plugin = Rainbowcreation.getInstance();
    private final Services service = plugin.services;
    private final Boolean yml = service.get("yml-data");
    private final Boolean sql = service.get("mySQL");
    private final Boolean redis = service.get("redis");

    public Object get(FileConfiguration config, String keys) {
        Object result = null;
        if (redis) {
            int redis_expire = Integer.parseInt(plugin.redis.get("redis.cashing_expire"));
            result = plugin.redis.get(config + "." + keys, redis_expire);
            if (result == null) {
                if (yml) {
                    if (config.contains(keys)) {
                        result = config.get(keys);
                        assert result != null;
                        plugin.redis.set(keys, result.toString(), redis_expire);
                        return result;
                    } else if (sql) {
                        String[] keyset = keys.split("\\.");
                        plugin.sql.get(config.getName(), keyset[0], keyset[1], keyset[2]);
                    }
                } else if (sql) {
                    //shit
                }
            }
        } else if (yml) {
            if (config.contains(keys)) {
                result = config.get(keys);
            }
        }
        return result;
    }
}
