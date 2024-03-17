package fun.xiantiao.myscoreboard;

import fun.xiantiao.myscoreboard.utils.mysb.MySB;
import fun.xiantiao.myscoreboard.utils.mysb.MySBIm;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fun.xiantiao.myscoreboard.Send.send;

/**
 * @author 40482
 */
public final class MyScoreBoard extends JavaPlugin implements Listener {
    private static MyScoreBoard plugin;
    public static boolean placeholderAPI = false;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        reloadConfig();


        if (!getConfig().isSet("var")) {
            List<String> stringList = new ArrayList<>();
            stringList.add("%player_name%");
            stringList.add("%player_uuid%");
            getConfig().set("PermittedPapi",stringList);
            getConfig().set("var",2);
        }


        getServer().getPluginManager().registerEvents(this,this);

        PluginCommand mySb = getCommand("mysb");
        if (mySb != null) {
            mySb.setExecutor(new Command());
            mySb.setTabCompleter(new Command());
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderAPI = true;
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            getLogger().warning("没有找到PlaceholderAPI！ 你将无法使用变量");
        }


        new Thread(() -> {
            while (plugin != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    BukkitScheduler scheduler = getServer().getScheduler();
                    scheduler.runTask(this, () -> send(player));
                }

                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException var2) {
                    throw new RuntimeException(var2);
                }
            }
        }).start();
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if (getConfig().isSet("data."+event.getPlayer().getUniqueId()+".text")) {
            send(event.getPlayer());
        }
    }

    @Override
    public void onDisable() {
        plugin = null;
        saveConfig();
    }
    public static MyScoreBoard getPlugin() {
        return plugin;
    }
}
