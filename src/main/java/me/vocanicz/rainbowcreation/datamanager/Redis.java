package me.vocanicz.rainbowcreation.datamanager;


import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import redis.clients.jedis.Jedis;

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

    public String ping() {
        Console.info("");
        return jedis.ping();
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
