package fun.xiantiao.myscoreboard;

import fun.xiantiao.myscoreboard.utils.mysb.MySB;
import fun.xiantiao.myscoreboard.utils.mysb.MySBIm;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiantiao
 * @date 2024/3/17
 * MyScoreBoard
 */
public class Send {
    public static void send(Player player) {
        MySB mySb = new MySBIm(player);
        FileConfiguration config = MyScoreBoard.getPlugin().getConfig();

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

                List<String> permitted = config.getStringList("PermittedPapi");
                List<String> playerInList = matchBetweenPercentages(s);
                if (containsOnlyElements(permitted,playerInList)) {
                    s = PlaceholderAPI.setPlaceholders(player, s);
                } else {
                    s = "你使用了不支持的变量";
                }
            }
            Score score = obj.getScore(ChatColor.translateAlternateColorCodes('&', s));
            --i;
            score.setScore(i+1);
        }
        player.setScoreboard(scoreboard);
    }


     private static List<String> matchBetweenPercentages(String text) {
        List<String> matches = new ArrayList<>();

        Pattern pattern = Pattern.compile("%(.*?)%");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }

    public static <T> boolean containsOnlyElements(List<T> permitted, List<T> playerInList) {
        if (permitted.size() == 0) {return false;}
        if (playerInList.size() == 0) {return true;}
        for (T element : playerInList) {
            if (!permitted.contains(element)) {return false;}
        }
        return true;
    }
}
