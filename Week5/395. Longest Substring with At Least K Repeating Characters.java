class Solution {
    public int longestSubstring(String s, int k) {
        return helper(s, 0, s.length() - 1, k);
    }

    private int helper(String s, int left, int right, int k) {
        // 这一行第一个条件left > right其实可以不要, 因为如果left > right, 那么
        // right - left + 1肯定小于k.
        if (left > right || right - left + 1 < k) {
            return 0;
        }
        int[] charCount = new int[26];
        for (int i = left; i <= right; i++) {
            charCount[s.charAt(i) - 'a'] += 1;
        }
        for (int i = left; i <= right; i++) {
            if (charCount[s.charAt(i) - 'a'] >= k) {
                continue;
            }
            int midNext = i + 1;
            while (midNext <= right && charCount[s.charAt(midNext) - 'a'] < k) {
                midNext += 1;
            }
            return Math.max(helper(s, left, i - 1, k), helper(s, midNext, right, k));
        }
        return right - left + 1;
    }
}
/**
 * 遍历一遍数组, 找到哪些char的freq是小于k的, 那么这些char就是split点. 我们再把split后的substring继续找.
 * 需要注意的一点是第19行, 当我们找到某个char是split点的时候, 先不要着急split, 可能后面跟着的也是split点,
 * 我们把连续的split点跳过, 直到遇到某个点不是split点的时候, 我们从这里开始取substring. 这算是一点点优化.
 * 优化效果还是很明显的.
 * 
 * 时间复杂度: O(n^2) 为啥是n^2?
 * 空间复杂度: O(n) 用栈. 比如k非常大, 那么我们在每个点都split. 但是如果有优化, 不可能在每个点都split.
 */