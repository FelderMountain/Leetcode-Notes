class Solution {
    private class Data {
        int lastSoldPrice;
        int lastBuyPrice;
        int total;

        Data(int lastSoldPrice, int lastBuyPrice, int total) {
            this.lastSoldPrice = lastSoldPrice;
            this.lastBuyPrice = lastBuyPrice;
            this.total = total;
        }
    }

    public int maxProfit(int[] prices) {
        Data[] dp = new Data[prices.length];
        dp[0] = new Data(prices[0], prices[0], 0);
        for (int i = 1; i < dp.length; i++) {
            if (prices[i] >= dp[i - 1].lastSoldPrice) {
                int currTotal = dp[i - 1].total + prices[i] - dp[i - 1].lastSoldPrice;
                dp[i] = new Data(prices[i], dp[i - 1].lastBuyPrice, currTotal);
            } else {
                dp[i] = new Data(prices[i], prices[i], dp[i - 1].total);
            }
        }
        return dp[dp.length - 1].total;
    }
}
/**
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */