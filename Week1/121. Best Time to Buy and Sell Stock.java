class Solution {
    public int maxProfit(int[] prices) {
        int profit = 0;
        int buyinPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            buyinPrice = Math.min(buyinPrice, prices[i]);
            profit = Math.max(profit, prices[i] - buyinPrice);
        }
        return profit;
    }
}
/**
 * 这道题在于怎么想出来这个方法. 买入的时候不一定是最小值, 卖出的时候也不一定是最大值. 因为有可能最小值出现在最大值之后.
 * 于是我们走一步记一步目前的最小值. 遇到有更小的, 我们要更新最小值, 保证我们买入的价格是目前遇到的最低的. 这时我想到
 * 那万一前面遇到的买入值虽然稍微偏高但是出现了一个股票价格很高的时候然后因此得到的profit也很多. 现在遇到更小的买入价直接更新买入价格的最小值
 * 是不是就默认了之后会遇到更多的profit? 不是这样的. 因为如果现在遇到了更低的买入价, 那么之后如果遇到一个高价格, profit肯定
 * 要以这个低买入价来计算, 而非之前的那个买入价, 只有这样, profit才能最大. 但至于说更新后得到的profit是否比更新前遇到的一些profit
 * 大, 这个不一定, 因此我们专门有个变量记录我们目前得到的最大的profit.
 * 
 * 总的来说就是记录当前遇到的最小买入价, 然后每天的价格都和这个买入价做差得到profit.
 * 同时记录当前的最大profit. 边走边做差, 边走边更新最小值, 同时也更新最大profit. 如果遇到更低的买入价, 那么
 * 更新买入价, 因为之后遇到的价格以这个新买入价做差可以计算出更高的profit. (这样可能会有超过当前最大profit的情况出现).
 */

// 时间复杂度: O(n)
// 空间复杂度: O(1)