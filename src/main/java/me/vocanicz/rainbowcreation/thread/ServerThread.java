package me.vocanicz.rainbowcreation.thread;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.datamanager.ServerRole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ServerThread extends BukkitRunnable {
    private final Rainbowcreation plugin = Rainbowcreation.getInstance();

    private int d = 0;
    private int p = 20;

    public ServerThread() {
    }

    private void thread() {
        plugin.itemdata.saveConfig();
        plugin.playerdata.saveConfig();
        plugin.serverdata.saveConfig();
        switch (ServerRole.get()){
            case ("time_server"):
                break ;
            case ("sql_server"):
                break;
            case ("market_server"):
                break;
            case ("overworld_server"):
                break;
            case ("log_server"):
                break;
            case ("gui_server"):
                break;
            case ("adv_server"):
                break;
        }
    }

    private void query() {
        List<Player> onlineplayer = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        for (Player player : onlineplayer) {
            //do player update
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
                thread();
                query();
            }
        }.runTaskTimer(plugin, d, p);
    }
}
