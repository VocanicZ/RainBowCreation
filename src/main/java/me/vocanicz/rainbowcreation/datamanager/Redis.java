package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import redis.clients.jedis.Jedis;

import java.io.StringReader;
import java.util.List;

public class Redis {
    private Jedis jedis;

    public Redis() {
        // Connect to the Redis server running on localhost
        String host = Rainbowcreation.getInstance().getConfig().getString("redis.server-ip");
        if (host.equals("default")) {
            host = "localhost";
        }
        Console.info("Create New Redis Connection to -> " + host);
        jedis = new Jedis(host);
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

    public boolean set(String key, String value) {
        // Set the value of the specified key
        Console.info(key + " = " + value);
        if (locked()) {
            Console.info("{ERROR} cannot set() because Atomic was locked");
            return false;
        }
        Lock();
        jedis.set(key, value);
        Console.info("complete!");
        Unlock();
        return true;
    }

    public boolean set(String key, String value, int expireAfter) {
        boolean bool = set(key, value);
        Console.info("expireAfter " + expireAfter + " second");
        jedis.expire(key, expireAfter);
        return bool;
    }

    public String ping() {
        Console.info("");
        return jedis.ping();
    }

    public List<String> cfgGet(String key) {
        Console.info(key);
        List<String> result = jedis.configGet (key);
        Console.info("return " + result);
        return result;
    }

    public boolean auth(String password) {
        String result = jedis.auth(password);
        Console.info(result);
        return result.equals("OK");
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


    public boolean Lock() {
        Console.info("logging..");
        if (!locked()) {
            jedis.set("locked", "1");
            Console.info("locked!");
            return true;
        }
        Console.info("{ERROR} cannot lock() Atomic because Atomic was locked");
        return false;
    }

    public boolean Unlock() {
        Console.info("unlocking..");
        if (locked()) {
            jedis.set("locked", "0");
            Console.info("unlocked!");
            return true;
        }
        Console.info("{WARN} cannot unlock() Atomic because Atomic was already unlocked");
        return false;
    }

}
