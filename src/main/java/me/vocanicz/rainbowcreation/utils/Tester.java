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
                    break;
                case ("mySQL"):
                    break;
            }
        }
        return result;
    }
}
