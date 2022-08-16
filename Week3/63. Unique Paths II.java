class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        for (int i = 0; i < dp[0].length; i++) {
            if (obstacleGrid[0][i] == 1)
                break;
            dp[0][i] = 1;
        }
        for (int i = 0; i < dp.length; i++) {
            if (obstacleGrid[i][0] == 1)
                break;
            dp[i][0] = 1;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (obstacleGrid[i][j] != 1) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }
}

/**
 * 正常的2D DP.
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m * n)
 */

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[] dp = new int[obstacleGrid[0].length];
        dp[0] = 1;
        for (int[] row : obstacleGrid) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (row[j] == 1)
                    dp[j] = 0;
                else if (j > 0)
                    dp[j] += dp[j - 1];
            }
        }
        return dp[dp.length - 1];
    }
}
/**
 * 这个解法很巧妙. 我们需要的是到某个格子上方和左边格子path的个数, 加在一起就行了.
 * 我们用一个1D array去存到第0行每个格子的path. 从第0行开始. 记得初始化
 * dp[0]为1. 如果遇到格子是1, 那么让dp中对应的index为0. 如果遇到格子是0,
 * 那么就是到左边格子和上边格子path的和. 到上边格子的path就是dp[j - 1], 上边
 * 格子的path是0, 所以就先不用管. 因此遍历完一遍后, 到第0行每个格子的path的走法
 * 我们记录在了dp数组中. 接下来我们遍历第1行. 对于第0个格子, 我们左边没格子, 因此它
 * 的值就是到它上面格子的path数目, 也就是此时的dp[0]. 然后到第一个格子, dp[1]
 * 此时存的是到第0行(上面一行)第一个格子的走法, dp[0]存的是第1行第0个格子的走法,
 * 刚好是我们想要的到左侧格子和上面格子的走法, 于是我们让dp[1]更新为dp[1] + dp[0]即可. 以此类推.
 * 
 * 总的来说:
 * 在开始遍历一行之前, dp存的是到该行上面一行所有格子的走法. 那么当我们遍历到该行
 * 第i个格子的时候, dp[i - 1]已经在之前被更新为到该行第i - 1个格子的走法, 而dp[i]
 * 还仍是到上一行第i个格子的走法. 此时我们把dp[i]更新为dp[i] + dp[i - 1]即可.
 * 由于i = 0时, 没有左侧格子默认为0, 因此此时如果该位置没有obstacl则不用更新dp[0],
 * 因为此时该位置dp的值和上一行在第0个位置的dp值一样. 如果有obstacle需要把该位置设置为
 * 0. 类似地在遍历其他的格子的时候需要先看是否那里有obstacle, 有的话直接把dp在该位置的
 * 值设置为0. 没有的话再去看是否是在开头, 如果是在开头不需要更新dp在开头的值, 如果不是
 * 那么就更新为dp[i] + dp[i - 1].
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m)
 */