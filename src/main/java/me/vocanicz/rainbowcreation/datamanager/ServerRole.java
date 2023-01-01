package me.vocanicz.rainbowcreation.datamanager;

public class ServerRole {

    private static String Role;

    public static String genRole() {
        //generate new server role by sending RainBowCreation API request
        String role = "default";
        set(role);
        return role;
    }

    public static String get() {
        return Role;
    }

    public static void set(String role) {
        Role = role;
    }
}