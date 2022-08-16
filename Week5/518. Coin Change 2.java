class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}
/**
 * 如果刨去最后一个coin, 我能知道前面所有的coins能凑成从0到amount的每一个值的种类个数, 那么带上
 * 最后一个coin可以凑成amount的种类个数是:
 * 1. 不带最后一个coin, 前面的coins凑成amount的种类个数
 * 2. 带最后一个coin, 所有coins凑成amount - 最后一个coin的种类个数.
 * 即既依赖上一行dp中正上方的值, 也依赖本行dp左侧的某个值.
 * 
 * 如果是请帮我凑到多少后, 我带上自己就是答案类型的题, 那么dp[0]就等于1. 也就是帮我的人不出任何力(0 element),
 * 能帮我凑出0(也就是这一种情况).
 */