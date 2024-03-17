import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiantiao
 * @date 2024/3/17
 * MyScoreBoard
 */
public class Main {
    public static void main(String[] args) {
        String playerIn = "hi,%playera%";
        List<String> playerInList = matchBetweenPercentages(playerIn);
        System.out.println("playerIn: " + playerInList);

        List<String> permitted = new ArrayList<>();
        permitted.add("%player%");
        permitted.add("%name%");
        System.out.println("permitted: " + permitted);

        System.out.println("通过:"+containsOnlyElements(permitted,playerInList));
    }

    public static List<String> matchBetweenPercentages(String text) {
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
