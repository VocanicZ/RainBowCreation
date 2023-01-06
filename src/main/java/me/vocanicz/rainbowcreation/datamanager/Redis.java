package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Objects;

public class Redis {
    private final Rainbowcreation plugin = Rainbowcreation.getInstance();
    private final Jedis jedis;
    public int cashing_expire;

    public Redis() {
        // Connect to the Redis server running on localhost
        String host = plugin.defaultConfig.getString("redis.server-ip");
        Console.info("Create New Redis Connection to -> " + host);
        jedis = new Jedis(host);
        auth(plugin.defaultConfig.getString("redis.password"));
        cashing_expire = Integer.parseInt(Objects.requireNonNull(plugin.defaultConfig.getString("redis.cashing-expire")));
    }

    public String get(String key) {
        // Get the value of the specified key
        Console.info(key);
        String value = jedis.get(key);
        Console.info("return " + value);
        return value;
    }

    public String get(String key, int expireAfter) {
        Console.info("expireAfter " + expireAfter + " second");
        jedis.expire(key, expireAfter);
        return get(key);
    }

    public void set(String key, String value) {
        // Set the value of the specified key
        Console.info(key + " = " + value);
        if (locked()) {
            Console.info("{ERROR} cannot set() because Atomic was locked");
            return;
        }
        Lock();
        jedis.set(key, value);
        Console.info("complete!");
        Unlock();
    }

    public void set(String key, String value, int expireAfter) {
        set(key, value);
        Console.info("expireAfter " + expireAfter + " second");
        jedis.expire(key, expireAfter);
    }

    public String ping() {
        Console.info("");
        return jedis.ping();
    }

    public List<String> cfgGet(String key) {
        Console.info(key);
        return jedis.configGet (key);
    }

    public void auth(String password) {
        String result = jedis.auth(password);
        Console.info(result);
    }

    public boolean locked() {
        String locked = get("locked");
        Console.info("= " + locked);
        if (locked == null) {
           jedis.set("locked", "0");
           Console.info("{WARN} set Atomic to unlocked state because state is null");
           Console.info("{WARN} ignore this warnning if it is first time running after enable Redis at config.yml");
           return false;
        } else return !locked.equals("0");
    }


    public void Lock() {
        Console.info("logging..");
        if (!locked()) {
            jedis.set("locked", "1");
            Console.info("locked!");
            return;
        }
        Console.info("{ERROR} cannot lock() Atomic because Atomic was locked");
    }

    public void Unlock() {
        Console.info("unlocking..");
        if (locked()) {
            jedis.set("locked", "0");
            Console.info("unlocked!");
            return;
        }
        Console.info("{WARN} cannot unlock() Atomic because Atomic was already unlocked");
    }

}
