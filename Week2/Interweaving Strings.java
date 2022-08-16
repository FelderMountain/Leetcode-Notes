import java.util.*;

class Program {
    public static boolean interweavingStrings(String one, String two, String three) {
        StringBuilder currentString = new StringBuilder();
        return helper(currentString, one, two, three, 0, 0);
    }

    public static boolean helper(StringBuilder current, String one, String two, String three,
            int posOne, int posTwo) {
        if (posOne == one.length()) {
            String result = current.toString() + two.substring(posTwo, two.length());
            return result.equals(three);
        }
        if (posTwo == two.length()) {
            String result = current.toString() + one.substring(posOne, one.length());
            return result.equals(three);
        }
        current.append(one.charAt(posOne));
        boolean selectStringOne = helper(current, one, two, three, posOne + 1, posTwo);
        current.deleteCharAt(current.length() - 1);
        if (selectStringOne)
            return true;

        current.append(two.charAt(posTwo));
        boolean selectStringTwo = helper(current, one, two, three, posOne, posTwo + 1);
        current.deleteCharAt(current.length() - 1);
        return selectStringTwo;
    }
}
/**
 * 这是我写的一个递归函数, 是走DFS. 这个是个很低效的方法. 更好的做法是走到一个位置看看three这个位置是什么字符,
 * 此时看看one和two现在的位置有没有这个字符, 如果有的话可以继续走, 否则没有再往下走的必要了. 我是属于不管有没有
 * 都往下走.
 * 比如one:algoexpert two:your-dream-job three:your-algorithm-expert-job
 * 此时three第0个是y, one和two有吗? two第0个是y. one的ptr不动, two的ptr往右走一位. three也是往右走一位.
 * 此时three第1个字符是o, 此时one.charAt(0)或者two.charAt(1)是o吗? two那个是, 然后更新three的ptr,
 * two的ptr, one的ptr不动. 以此类推. 这样就比我的那个好很多了.
 */
