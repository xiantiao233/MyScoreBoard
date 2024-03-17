package fun.xiantiao.myscoreboard;

import fun.xiantiao.myscoreboard.utils.mysb.MySB;
import fun.xiantiao.myscoreboard.utils.mysb.MySBIm;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiantiao
 * @date 2024/3/17
 * MyScoreBoard
 */
public class Command implements CommandExecutor, TabCompleter {
    MyScoreBoard plugin = MyScoreBoard.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {return true;}

        if (!(player.isOp() || player.hasPermission("myscoreboard.use"))) {
            sender.sendMessage("你没有权限！");
        }

        if (args.length == 0) {
            sender.sendMessage("=========MySb=========");
            sender.sendMessage("/mysb set");
            sender.sendMessage("/mysb reload");
            sender.sendMessage("======================");
        }

        if (args.length > 0) {

            if ("reload".equalsIgnoreCase(args[0])) {
                if (player.isOp() || player.hasPermission("myscoreboard.reload")) {
                    plugin.reloadConfig();
                    return true;
                }
            }

            if ("look".equalsIgnoreCase(args[0])) {
                MySB mySb = new MySBIm(player);
                sender.sendMessage("======================");
                for (String s1 : mySb.list()) {
                    sender.sendMessage(s1);
                }
                sender.sendMessage("======================");
                return true;
            }

            if ("set".equalsIgnoreCase(args[0])) {

                if (args.length > 1) {
                    MySB mySb = new MySBIm(player);

                    if ("title".equalsIgnoreCase(args[1])) {
                        if (args.length > 2) {
                            mySb.title(args[2]);
                            sender.sendMessage("成功！");
                        } else {
                            sender.sendMessage("你应该要输入参数");
                        }
                        return true;
                    }

                    if ("add".equalsIgnoreCase(args[1])) {

                        if (args.length >= 3 && mySb.list().contains(args[2])) {
                            sender.sendMessage("计分板不能有重复的内容！");
                            return true;
                        }

                        if (args.length >= 4) {
                            mySb.add(args[2], Integer.parseInt(args[3]));
                            sender.sendMessage("成功！");
                            return true;
                        }

                        if (args.length > 2) {
                            mySb.add(args[2]);
                            sender.sendMessage("成功！");
                        } else {
                            sender.sendMessage("你应该要输入参数");
                        }
                        return true;
                    }

                    if ("set".equalsIgnoreCase(args[1])) {
                        if (args.length >= 3 && mySb.list().contains(args[2])) {
                            sender.sendMessage("计分板不能有重复的内容！");
                            return true;
                        }

                        if (args.length >= 4) {
                            mySb.set(args[2], Integer.parseInt(args[3]));
                            sender.sendMessage("成功！");
                        } else {
                            sender.sendMessage("你应该要输入参数");
                        }
                        return true;
                    }

                    if ("resetting".equalsIgnoreCase(args[1])) {
                        mySb.resetting();
                        sender.sendMessage("重置成功");
                        return true;
                    }

                    if ("remove".equalsIgnoreCase(args[1])) {
                        if (args.length == 3) {
                            mySb.remove(Integer.parseInt(args[2]));
                        }
                        return true;
                    }
                    return true;
                } else {
                    sender.sendMessage("你要设置什么？");
                }

                return true;
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        List<String> stringList = new ArrayList<>();

        if (!(sender.isOp() || sender.hasPermission("myscoreboard.use"))) {
            return null;
        }

        if (args.length == 1) {
            stringList.add("set");

            if (sender.isOp() || sender.hasPermission("myscoreboard.reload")) {
                stringList.add("reload");
            }
        }

        if (args.length == 2 && "set".equalsIgnoreCase(args[0])) {
            stringList.add("resetting");
            stringList.add("remove");
            stringList.add("add");
            stringList.add("set");
            stringList.add("look");
        }

        if (args.length == 3 && "remove".equalsIgnoreCase(args[1])) {
            stringList.add("要移除的行");
        }

        if (args.length == 3 && "add".equalsIgnoreCase(args[1])) {
            stringList.add("文字");
        }
        if (args.length == 4 && "add".equalsIgnoreCase(args[1])) {
            stringList.add("要添加的行");
        }

        if (args.length == 3 && "set".equalsIgnoreCase(args[1])) {
            stringList.add("文字");
        }
        if (args.length == 4 && "set".equalsIgnoreCase(args[1])) {
            stringList.add("要修改的行");
        }

        return stringList;
    }
}
