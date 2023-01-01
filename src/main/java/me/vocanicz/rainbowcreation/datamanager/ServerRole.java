package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.chat.Console;

public class ServerRole {

    private static String Role;

    public static String genRole() {
        Console.info("Generate new role..");
        //generate new server role by sending RainBowCreation API request
        String role = "default";
        set(role);
        return role;
    }

    public static String get() {
        return Role;
    }

    public static void set(String role) {
        Console.info("role set to " + role);
        Role = role;
    }
}