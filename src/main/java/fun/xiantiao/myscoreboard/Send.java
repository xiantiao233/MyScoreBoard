package fun.xiantiao.myscoreboard;

import fun.xiantiao.myscoreboard.utils.mysb.MySB;
import fun.xiantiao.myscoreboard.utils.mysb.MySBIm;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collections;
import java.util.List;

/**
 * @author xiantiao
 * @date 2024/3/17
 * MyScoreBoard
 */
public class Send {
    public static void send(Player player) {
        MySB mySb = new MySBIm(player);

        Scoreboard scoreboard = MyScoreBoard.getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective(String.valueOf(player.getUniqueId()),"dummy",mySb.title());
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> stringList = mySb.list();
        Collections.reverse(stringList);

        int i = mySb.list().size();
        for (String s : stringList) {
            s = s.replaceAll("!:"," ");
            s = s.replaceAll("%player%",player.getName());

            if (MyScoreBoard.placeholderAPI) {
                s = PlaceholderAPI.setPlaceholders(player, s);
            }

            Score score = obj.getScore(ChatColor.translateAlternateColorCodes('&', s));
            --i;
            score.setScore(i+1);
        }
        player.setScoreboard(scoreboard);
    }
}
