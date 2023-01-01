package me.vocanicz.rainbowcreation.thread;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.ServerRole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ServerThread extends BukkitRunnable {
    private final Rainbowcreation plugin = Rainbowcreation.getInstance();

    private int delay = 0;
    private int cycle = 20;

    public ServerThread() {
    }

    private void thread() throws InterruptedException {
        plugin.itemdata.saveConfig();
        plugin.playerdata.saveConfig();
        plugin.serverdata.saveConfig();
        switch (ServerRole.get()){
            case ("time_server"):
                break ;
            case ("sql_server"):
                delay = 18000;
                cycle = 18000;
                break;
            case ("market_server"):
                delay = 200;
                cycle = 1200;
                break;
            case ("overworld_server"):
                break;
            case ("log_server"):
                cycle = 72000;
                break;
            case ("gui_server"):
                delay = 20;
                break;
            case ("adv_server"):
                cycle = 1200;
                break;
        }
    }

    private void query() throws InterruptedException {
        List<Player> onlinePlayer = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : onlinePlayer) {
            //do player update
            Console.info(player.getName());
        }
        plugin.itemdata.saveConfig();
        plugin.playerdata.saveConfig();
        plugin.serverdata.saveConfig();
        plugin.itemdata.reloadConfig();
        plugin.playerdata.reloadConfig();
        plugin.serverdata.reloadConfig();
    }

    @Override
    public void run() {
        new BukkitRunnable(){
            public void run(){
                ServerRole.genRole();
                try {
                    thread();
                    query();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimer(plugin, delay, cycle);
    }
}
