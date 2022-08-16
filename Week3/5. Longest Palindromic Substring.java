class Solution {
    public String longestPalindrome(String s) {
        int start = 0, end = 0;
        for (int i = 1; i < s.length(); i++) {
            int lengthOne = expandAroundCenter(s, i, i);
            int lengthTwo = expandAroundCenter(s, i - 1, i);
            int maxLength = Math.max(lengthOne, lengthTwo);
            if (maxLength > end - start + 1) {
                start = i - maxLength / 2;
                end = i + (maxLength - 1) / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left -= 1;
            right += 1;
        }
        return (right - left - 2) + 1; // 减2意思是退回到上一次满足while条件的状态. 然后获这时候的长度
    }
}
/**
 * 我们遍历每一个位置, 它自己作为对称轴或者它和前面一个element中间缝隙作为对称轴.
 * start和end记录最长substring的起始和终止位置, 都是inclusive的.
 * 第9第10行是精华. 如果从以某个位置为对称轴, 此时maxLength是奇数. 那么它的左侧最远到达是i - maxLength / 2. 
 * 右侧最远到达是i + maxLength / 2.
 * 如果以i和i - 1之间的缝隙作为对称轴, 此时maxLength是偶数. 那么左侧最远是i - maxLength / 2.
 * 右侧最远到达是i + (maxLength / 2 - 1).
 * 
 * 为了统一两种情况计算右侧最远到达, 我们在算end的时候直接让maxLength - 1, 这样会向下取从而变为maxLength / 2 - 1.
 * 这个方法很巧.
 * 
 * expandAroundCenter在left和right终止的位置之间的部分就是palindrom(不包含left和right所在的位置). 因为如果left和right
 * 都在string的range内并且相等, 那么它们会继续移动, 直到一方出界或双方同时出界或者双方不等, 每一种情况都是标志着取palindrome的结束.
 * 但是它们走过的长度刨去它们俩目前所在的位置就是此时构成的palindrom的长度.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(1)
 */