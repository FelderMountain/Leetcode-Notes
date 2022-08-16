class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        int y = x, num = 0;
        while (y != 0) {
            num = num * 10 + y % 10;
            y /= 10;
        }
        return x == num;
    }
}
/**
 * 这个解法是正宗解法. 就是如果是palindrome, 那么以最右边为most significant bit
 * 去构建这个数和以最左边为most significant bit应该是相同的.
 * 
 * 时间复杂度: O(n) n是数字的长度.
 * 空间复杂度: O(1)
 */