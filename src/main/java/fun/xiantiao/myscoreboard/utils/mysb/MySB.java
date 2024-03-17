package fun.xiantiao.myscoreboard.utils.mysb;

import java.util.List;

/**
 * @author xiantiao
 * @date 2024/3/17
 * MyScoreBoard
 */
public interface MySB {
    List<String> list(); //获取SB文字列表

    void add(String s); //追加文字

    void add(List<String> s); //追加文字

    void add(String s,int line); //在指定行上面添加文字

    void set(String s,int line); //在修改行上面的文字

    void remove(int line); //移除某一行的文字

    void title(String title); //设置SB标题

    String title(); //获取SB标题

    void resetting(); //重置计分板
}
