import java.util.Map;
import java.util.HashMap;

class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length())
            return "";
        Map<Character, Integer> charCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            charCount.put(t.charAt(i), charCount.getOrDefault(t.charAt(i), 0) + 1);
        }
        int left = 0, right = 0, count = 0, minLeft = 0, minRight = 0, min = Integer.MAX_VALUE;
        while (right < s.length()) {
            if (charCount.containsKey(s.charAt(right))) {
                charCount.put(s.charAt(right), charCount.get(s.charAt(right)) - 1);
                if (charCount.get(s.charAt(right)) >= 0) {
                    count += 1;
                }
            }

            while (count == t.length() && left <= right) {
                if (right - left + 1 < min) {
                    minLeft = left;
                    minRight = right + 1;
                    min = right - left + 1;
                }
                if (charCount.containsKey(s.charAt(left))) {
                    charCount.put(s.charAt(left), charCount.get(s.charAt(left)) + 1);
                    if (charCount.get(s.charAt(left)) > 0) {
                        count -= 1;
                    }
                }
                left += 1;
            }
            right += 1;
        }
        return min != Integer.MAX_VALUE ? s.substring(minLeft, minRight) : "";
    }
}

/**
 * 这道题也太难了, 但这个题解写的真漂亮.
 * 
 * 思路就是sliding window.
 * 
 * 这道题的一个启发就是不要一步到位. 我当时是想让left遇到不在t里面的字符就一直往右走, 直到
 * 遇到了这么一个在t里面的字符, 然后就停下. 我发现这样写的话, 要考虑的东西就很多. 比如left
 * 不能超过right是一个, 于是当这个循环停下来的时候, 我们要判断是为什么停下来了. 是因为超过right了,
 * 还是遇到在t中的字符了. 如果是遇到t中的字符了, 我们要计算一下当前的长度和min比谁小. 然后left再
 * 移动一格, 之后去移动right. 如果是超过right了, 那么right要移动一格然后继续.
 * 
 * 这个count的使用和spiral matrix那道题的count使用很像, 也可以借鉴.
 * 
 * 上面这个题解写的很neat和elegant. 需要我好好去想一想.
 * 
 * 时间复杂度: O(m + n)
 * 空间复杂度: O(1) 因为一共就52种可能的字符.
 */

class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length())
            return "";
        Map<Character, Integer> charCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            charCount.put(t.charAt(i), charCount.getOrDefault(t.charAt(i), 0) + 1);
        }
        int left = 0, right = 0, count = 0, minLeft = 0, minRight = 0, min = Integer.MAX_VALUE;

        while (right < s.length()) {
            // Move right pointer.
            while (right < s.length()) {
                if (charCount.containsKey(s.charAt(right))) {
                    charCount.put(s.charAt(right), charCount.get(s.charAt(right)) - 1);
                    if (charCount.get(s.charAt(right)) >= 0) {
                        count += 1;
                    }
                }
                if (count == t.length())
                    break;
                right += 1;
            }

            // Test if out of bound.
            if (right == s.length())
                break;

            // Move left pointer until not all chars in t are included in the window.
            // left <= right可要可不要.
            while (count == t.length() && left <= right) {
                if (charCount.containsKey(s.charAt(left))) {
                    charCount.put(s.charAt(left), charCount.get(s.charAt(left)) + 1);
                    if (charCount.get(s.charAt(left)) > 0) {
                        count -= 1;
                    }
                }
                left += 1;
            }

            // Check the length.
            if (right - (left - 1) + 1 < min) {
                minLeft = left - 1;
                minRight = right + 1;
                min = right - (left - 1) + 1;
            }

            right += 1;
        }
        return min != Integer.MAX_VALUE ? s.substring(minLeft, minRight) : "";
    }
}
/**
 * 这一版是比较符合我的想法的. 先移动right pointer, 再移动left pointer, 看看在right pointer所在位置的
 * 情况下, left pointer能逼得多近. 当移动完left pointer后, 我们计算此时的值.
 * 
 * 我们需要循环的就是right往右去找, left往右剔除. 循环何时停止? 直到right遍历完所有的字符.
 * 
 * right pointer是用来找char入伙的, 而left是踢出char的. right pointer走过的地方都被拉了进来.
 * left则是在count能够保证的前提下踢出去char.
 * 
 * right就是到一个位置就拉一个入伙. left则是到一个位置就踢一个出去, 直到不能踢为止或者超过了right.
 * 那么最小的window就是left停止的上一个位置, 因为在上一个位置还能踢, 此时的位置踢不了, 那么上一个位置
 * 就是极限了. 于是才有的right - (left - 1) + 1来计算最小的window size.
 * 
 * 后来想到第90行的left <= right可要可不要. 因为如果left == right时还是满足count == t.length(),
 * 那么left往左移动一位就会变成left > right, 此时count不可能等于t.length(), 也就是count == t.length()
 * 包含了left <= right的条件. 如果满足count == t.length(), 那么left <= right一定会满足.
 * 
 * 时间复杂度和空间复杂度不变.
 */