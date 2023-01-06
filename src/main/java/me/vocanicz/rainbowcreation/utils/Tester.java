package me.vocanicz.rainbowcreation.utils;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;

import java.util.List;

public class Tester {

    private final Rainbowcreation plugin = Rainbowcreation.getInstance();

    public boolean run() {
        boolean result = true;

        List<String> keys = plugin.services.getEnables();
        for (String service : keys) {
            Console.info("Testing service: " + service);
            switch (service) {
                case ("yml-data"):
                    //do
                    break;
                case ("mySQL"):
                    Console.info("connection test");
                    Console.info("this server first seen at " + plugin.sql.get("server_info", "start_time", "id", 1));
                    break;
                case ("redis"):
                    Console.info("ping test");
                    if (plugin.redis.ping().equals("PONG")) {
                        if (plugin.redis.cfgGet("requirepass").get(1).isEmpty()) {
                            Console.info("{WARN} Please setup your redis password and update config.yml");
                            result = false;
                        }
                    } else {
                        Console.info("Redis server not found make sure your redis server running");
                        result = false;
                    }
                    Console.info("expire_test");
                    int exp = plugin.redis.cashing_expire;
                    plugin.redis.set("exp_test", "expire in " + exp + " second(s).", exp);
                    Console.info("waiting " + exp);
                    try {
                        Thread.sleep(exp * 1000L / 2);
                        if (plugin.redis.get("exp_test").equals("expire in " + exp + " second(s).")) {
                            Thread.sleep(exp * 1000L / 2);
                            if (plugin.redis.get("exp_test") != null) {
                                result = false;
                            } else {
                                Console.info("pass");
                            }
                        } else {
                            result = false;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
        return result;
    }
}
