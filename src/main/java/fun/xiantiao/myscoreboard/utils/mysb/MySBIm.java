package fun.xiantiao.myscoreboard.utils.mysb;

import fun.xiantiao.myscoreboard.MyScoreBoard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiantiao
 * @date 2024/3/17
 * MyScoreBoard
 */
public class MySBIm implements MySB{
    static FileConfiguration config = MyScoreBoard.getPlugin().getConfig();
    Player player;

    public MySBIm(Player player) {
        this.player = player;

        if (title() == null) {
            title(config.getString("template.title"));
            add(config.getStringList("template.text"));
        }
    }

    @Override
    public List<String> list() {
        return config.getStringList(getDataPath()+"text");
    }

    @Override
    public void add(String s) {
        List<String> list = new ArrayList<>(config.getStringList(getDataPath() + "text"));
        list.add(s);

        config.set(getDataPath()+"text",list);
    }

    @Override
    public void add(List<String> s) {
        for (String s1 : s) {
            add(s1);
        }
    }

    @Override
    public void add(String s, int line) {
        if (line < 0) {return;}
        List<String> list = new ArrayList<>(config.getStringList(getDataPath() + "text"));
        if (list.size() >= line) {
            list.add(line-1,s);
        }

        config.set(getDataPath()+"text",list);
    }

    @Override
    public void set(String s, int line) {
        if (line < 0) {return;}
        List<String> list = new ArrayList<>(config.getStringList(getDataPath() + "text"));
        if (list.size() >= line) {
            list.set(line-1,s);
        }
        config.set(getDataPath()+"text",list);
    }

    @Override
    public void remove(int line) {
        if (line < 0) {return;}
        List<String> list = new ArrayList<>(config.getStringList(getDataPath() + "text"));
        list.remove(line-1);

        config.set(getDataPath()+"text",list);
    }

    @Override
    public void title(String title) {
        config.set(getDataPath()+"title",title);
    }

    @Override
    public String title() {
        return config.getString(getDataPath()+"title");
    }

    @Override
    public void resetting() {
        title(config.getString("template.title"));
        config.set(getDataPath()+"text",config.getString("template.text"));
    }

    String getDataPath() {
        return "data."+player.getUniqueId()+".";
    }
}
