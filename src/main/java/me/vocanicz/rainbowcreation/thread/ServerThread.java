package me.vocanicz.rainbowcreation.thread;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;
import me.vocanicz.rainbowcreation.datamanager.Redis;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ServerThread extends BukkitRunnable {
    private final Rainbowcreation plugin = Rainbowcreation.getInstance();
    private static final Redis redis = new Redis();
    private final String server_name = plugin.getConfig().get("server-name").toString();

    private int d = 0;
    private int p = 20;
    private void thread() {
        switch (server_name){
            case ("time_server"):
                redis.set("current_time", String.valueOf(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis()));
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
            Console.info(server_name + " : " + player.getName());
        }
    }

    @Override
    public void run() {
        new BukkitRunnable(){
            public void run(){
                thread();
                query();
            }
        }.runTaskTimer(plugin, d, p);
    }
}
