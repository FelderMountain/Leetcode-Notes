class Solution {
    public int countSubstrings(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            ans += palindromeCountAroundCenter(s, i, i);
            ans += palindromeCountAroundCenter(s, i, i + 1);
        }
        return ans;
    }

    private int palindromeCountAroundCenter(String s, int left, int right) {
        int ans = 0;
        while (left >= 0 && right < s.length()) {
            if (s.charAt(left) != s.charAt(right)) {
                break;
            }
            left -= 1;
            right += 1;
            ans += 1;
        }
        return ans;
    }
}
/**
 * 这道题的精髓就是这个priavte method. 如何从一个center扩张. 如果left == right, 那么就是从一个字母为中心
 * 往两边扩; 如果left + 1 == right, 那么就是以left和right之间的缝隙为center往两侧扩.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(1)
 */