class Solution {
    public int divide(int dividend, int divisor) {
        boolean positive = (dividend > 0) == (divisor > 0) ? true : false;
        long dividendLong = Math.abs((long) dividend), divisorLong = Math.abs((long) divisor), ans = 0;
        while (dividendLong >= divisorLong) {
            long currDivisor = divisorLong;
            long currQuoient = 1;
            while (dividendLong >= currDivisor) {
                ans += currQuoient;
                dividendLong -= currDivisor;
                currDivisor <<= 1;
                currQuoient <<= 1;
            }
        }
        ans = positive ? ans : -ans;
        return ans > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) ans;

    }
}
/**
 * 和第50题Pow(x, n)是一个模板. 我们先看一次循环要干什么事情.
 * 先从divisor开始减, 如果dividend有, 那么继续减2 * divisor, 如果可以再减...一直到不行.
 * 每次, 我们记录减了多少个divisor并把它存到ans中去. currQuoient记录的是现在有多少个divisor,
 * currDivisor表示n * divisor的值.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 */