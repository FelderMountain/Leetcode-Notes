class Solution {
    public int numDecodings(String s) {
        Integer[] memo = new Integer[s.length()];
        return helper(s, 0, memo);
    }

    private int helper(String s, int pos, Integer[] memo) {
        if (pos == s.length()) {
            return 1;
        }
        if (memo[pos] != null) {
            return memo[pos];
        }
        if (s.charAt(pos) == '0') {
            return 0;
        }
        int singleDigitWays = helper(s, pos + 1, memo);
        int doubleDigitWays = 0;
        if (pos < s.length() - 1) {
            int currDigit = 10 * (s.charAt(pos) - '0') + (s.charAt(pos + 1) - '0');
            if (currDigit <= 26) {
                doubleDigitWays = helper(s, pos + 2, memo);
            }
        }
        memo[pos] = singleDigitWays + doubleDigitWays;
        return memo[pos];
    }
}
/**
 * Recursion with memoization.
 * 思路肯定先从recursion出发. 从第0个字符开始, 如果该字符不是0, 那么我们需要知道从第一个字符到最后一个字符组成的string
 * 能被decode为多少种, 这就又变成了给一个string, 问有多少种decode方式, 很自然就想到了递归. 同样地我们也需要考虑两个字符组成
 * 一个数字的情况, 但是必须是这两个字符组成的数字是valid的(大于等于1小于等于26).
 * 于是思路就是这样, 首先取s的第0个字符, 如果为0, 直接返回0, 因为这个string组成不了valid的字母. 如果不是, 那么调用
 * helper(s.substring(1)), 获得之后string的解码方式的个数. 再取前两个字符, 首先判断是否为valid的, 不是的话就这种情况
 * 跳过, 是的话则调用helper(s.substring(2)). 后面的以此类推. 我们可以用一个变量来存我们走到了哪里.
 * 也就是要看的substring从哪里开始, 这样就不用调用substring函数了, 因为这是个O(n)的函数.
 * 
 * 之后我们来到memoization的情况. 有些情况是会重复计算的, 比如11111. 我们取1, 再取1, 剩下的111我们需要计算有多少种解码方式.
 * 类似地, 我们直接取11, 我们也还要看剩下的111有多少种解码方式. 于是我们把计算过的存到一个array中.
 * memo[pos]表示从pos(inclusive)到结尾构成的substring有多少种解码方式.
 * 
 * 时间复杂度: O(n) memo每个位置最多计算一次, 因此为O(n)
 * 空间复杂度: O(n) memo的长度
 */
