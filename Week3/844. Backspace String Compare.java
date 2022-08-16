class Solution {
    public boolean backspaceCompare(String s, String t) {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '#' && s1.length() > 0) {
                s1.deleteCharAt(s1.length() - 1);
            } else if (s.charAt(i) != '#') {
                s1.append(s.charAt(i));
            }
        }
        for (int i = 0; i < t.length(); i++) {
            if (t.charAt(i) == '#' && s2.length() > 0) {
                s2.deleteCharAt(s2.length() - 1);
            } else if (t.charAt(i) != '#') {
                s2.append(t.charAt(i));
            }
        }
        return s1.toString().equals(s2.toString());
    }
}

/**
 * 这是我的写法, 很直接.
 * 
 * 时间复杂度: O(s + t)
 * 空间复杂度: O(s + t)
 * 
 * s和t分别是两个string的长度.
 */

class Solution {
    public boolean backspaceCompare(String s, String t) {
        int ptrOne = s.length() - 1, ptrTwo = t.length() - 1;
        int sCount = 0, tCount = 0;
        while (ptrOne >= 0 || ptrTwo >= 0) {
            while (ptrOne >= 0) {
                if (s.charAt(ptrOne) == '#') {
                    sCount += 1;
                } else if (sCount > 0) {
                    sCount -= 1;
                } else {
                    break;
                }
                ptrOne -= 1;
            }
            char ansOne = ptrOne < 0 ? '#' : s.charAt(ptrOne);

            while (ptrTwo >= 0) {
                if (t.charAt(ptrTwo) == '#') {
                    tCount += 1;
                } else if (tCount > 0) {
                    tCount -= 1;
                } else {
                    break;
                }
                ptrTwo -= 1;
            }
            char ansTwo = ptrTwo < 0 ? '#' : t.charAt(ptrTwo);

            if (ansOne != ansTwo)
                return false;
            ptrOne -= 1;
            ptrTwo -= 1;
        }
        return true;
    }
}
/**
 * 这个是用two pointers去解决. 都是从后往前, 我们用两个变量sCount和tCount来记录遇到的#的数量.
 * 这些可以抵消其他字符. 直到抵消不了, 我们让ptr停止.
 * 
 * 时间复杂度: O(m + n) m和n分别是两个string的长度.
 * 空间复杂度: O(1)
 */