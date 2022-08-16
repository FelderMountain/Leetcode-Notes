class Solution {
    public int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            int currentDigit = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && currentDigit > 7)) {
                return 0;
            }
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && currentDigit == -9)) {
                return 0;
            }
            ans = ans * 10 + currentDigit;
            x /= 10;
        }
        return ans;
    }
}
/**
 * 这道题精髓在于如何判断是否超出Integer.MAX_VALUE和Integer.MIN_VALUE.
 * 
 * 时间复杂度: O(1) 因为32bit integer长度固定, 我们需要遍历每一个bit, 因此最大就是32bits遍历完.
 * 空间复杂度: O(1) 没有使用额外空间或者栈.
 */