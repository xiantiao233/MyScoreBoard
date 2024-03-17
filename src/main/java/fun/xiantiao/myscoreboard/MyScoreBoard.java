package fun.xiantiao.myscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
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

    @Override
    public void onDisable() {
        plugin = null;
        saveConfig();
    }
    public static MyScoreBoard getPlugin() {
        return plugin;
    }
}
