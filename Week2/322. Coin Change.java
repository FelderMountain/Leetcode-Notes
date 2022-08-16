class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}

/**
 * 这道题典型的DP. 对于某个amount, 以及给定的coins, 如何到达这个amount? 肯定有这么一枚coin,
 * 加上它之后, 就到达这个amount. 那么到达这个amount的最后一步可以是加上coins里面的任意一个coin.
 * 于是我们想知道dp[amount], 就得知道dp[amount - coin1], dp[amount - coin12],
 * dp[amount - coin3]... dp[amount - coinN]中哪个最小, 最小的那个 + 1(即加上最后一个coin
 * 到达dp[amount])就是答案.
 * 
 * 我们一开始把dp里面所有的元素都初始为amount + 1, 因为这个数字是不可能的, 即使最小的硬币是1, 最少的
 * 钱数也是amount. 因此用amount + 1表示凑不到. dp[0]初始为0表示0个coin就可以凑到, 这也是符合常识的,
 * 有时候dp初始几个元素的赋值是不符合常识的, 而是需要谨慎选择的. 在进行inner循环的时候, 如何coin的值大于
 * 当前的amount, 那就说明无法在使用当前的coin的前提下凑到目标amount.
 * 
 * 如何保证dp[i - coin]是我们遍历过的. 首先 coin <= i保证
 * dp[i - coin]不出界. 其次由于coin > 0, i - coin
 * 肯定小于i, 因此在当前位置的左侧. 既然在左侧, 那么我们一定
 * check过. 所以就不用担心dp[i - coin]没有被遍历过了.
 * 
 * 时间复杂度: O(S * N) S是amount, N是coin的数量.
 * 空间复杂度: O(S) 因为我们用一个数组去存到每一个amount最小的coin的个数.
 */

class Solution {
    public int coinChange(int[] coins, int amount) {
        return helper(coins, amount, new int[amount + 1]);
    }

    private int helper(int[] coins, int amount, int[] cache) {
        if (amount < 0)
            return -1;
        if (amount == 0)
            return 0;
        if (cache[amount] != 0)
            return cache[amount];
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int ans = helper(coins, amount - coin, cache);
            if (ans >= 0 && ans < min)
                min = ans + 1;
        }
        cache[amount] = min == Integer.MAX_VALUE ? -1 : min;
        return cache[amount];
    }
}

/**
 * 这个是DFS. 递归函数的功能就是给一个amount, 就能告诉我们最少数目的coins是多少, 如果凑不到返回-1.
 * 我们使用了一个cache来去装我们遇到过的答案, 这样大大减少递归的调用.
 * 
 * 时间复杂度: O(S*N)
 * 空间复杂度: O(S) 假设有个coin是1, 那么最深就要S个函数frames.
 */

class Solution {
    // Recursion with memoization
    public int coinChange(int[] coins, int amount) {
        int[] visited = new int[amount + 1];
        Arrays.fill(visited, amount + 1);
        visited[0] = 0;
        helper(coins, amount, visited);
        return visited[amount] < amount + 1 ? visited[amount] : -1;
    }

    // Tell us what is the fewest number of coins needed to get amount
    private int helper(int[] coins, int amount, int[] visited) {
        if (visited[amount] < amount + 1)
            return visited[amount];
        for (int coin : coins) {
            if (coin <= amount) {
                visited[amount] = Math.min(visited[amount], helper(coins, amount - coin, visited) + 1);
            }
        }
        return visited[amount];
    }
}
/**
 * 比较简洁的写法.
 */