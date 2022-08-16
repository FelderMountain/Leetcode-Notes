class Solution {
    public boolean isOneEditDistance(String s, String t) {
        if (Math.abs(s.length() - t.length()) > 1) {
            return false;
        }
        int ptrOne = 0, ptrTwo = 0;
        while (ptrOne < s.length() && ptrTwo < t.length() && s.charAt(ptrOne) == t.charAt(ptrTwo)) {
            ptrOne += 1;
            ptrTwo += 1;
        }

        // Two strings are identical
        if (ptrOne == s.length() && ptrTwo == t.length()) {
            return false;
        }

        if (s.length() < t.length()) {
            return s.substring(ptrOne).equals(t.substring(ptrTwo + 1));
        } else if (s.length() > t.length()) {
            return s.substring(ptrOne + 1).equals(t.substring(ptrTwo));
        } else {
            return s.substring(ptrOne + 1).equals(t.substring(ptrTwo + 1));
        }
    }
}
/**
 * 首先判断两个字符串长度是否相差超过2, 如果是就直接返回false.
 * 其次再同时遍历两个字符串, 直到在某位不相等或者至少一方出界.
 * 如果不相等, 那么分情况, 也就是短的一方insert, 长的一方delete或者
 * 二者相等replace. 如果同时出界, 那么返回false, 因为二者一样, 不可能
 * 一步改成二者又相等. 如果一方出界, 说明另一方还有一位(因为二者长度最多相差1
 * 且不是同时出界, 那说明一个长一个短), 这样也能套用上面三种情况的做法.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) substring
 */