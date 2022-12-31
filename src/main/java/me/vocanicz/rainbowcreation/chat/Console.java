package me.vocanicz.rainbowcreation.chat;

import me.vocanicz.rainbowcreation.Rainbowcreation;

public class Console {
    public static void info(String string) {
        Rainbowcreation.getInstance().getLogger().info(string);
    }
}
