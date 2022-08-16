class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> charNum = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            charNum.put(currentChar, charNum.getOrDefault(currentChar, 0) + 1);
        }

        for (int i = 0; i < t.length(); i++) {
            char currentChar = t.charAt(i);
            if (charNum.containsKey(currentChar)) {
                charNum.put(currentChar, charNum.get(currentChar) - 1);
                if (charNum.get(currentChar) == 0) {
                    charNum.remove(currentChar);
                }
            } else {
                return false;
            }
        }
        return true;
    }
}

/**
 * 思路很简单. 就是记录s中每个字母出现的频率, 然后再遍历t去挨个抵消, 看看最后是否全部抵消完.
 * 我用的是hashmap去做, 也就是给一个字母我能快速知道它的频率是多少.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public boolean isAnagram(String s, String t) {
        int[] alphaFrequency = new int[26];
        for (int i = 0; i < s.length(); i++) {
            alphaFrequency[s.charAt(i) - 'a'] += 1;
        }

        for (int i = 0; i < t.length(); i++) {
            alphaFrequency[t.charAt(i) - 'a'] -= 1;
        }

        for (int i = 0; i < alphaFrequency.length; i++) {
            if (alphaFrequency[i] != 0) {
                return false;
            }
        }
        return true;
    }
}

/**
 * 我把问题想复杂了, 为什么要用hashmap呢? 这里的string都是小写字母, 那么我们可以创建一个长度为26的数组, 每个元素代表对应字母
 * 出现的频率, 那么如何定位到某个字母在数组中的位置呢? 就减去‘a’即可.
 * 
 * 时间复杂度和空间复杂度没有变.
 */
