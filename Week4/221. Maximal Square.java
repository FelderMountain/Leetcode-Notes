class Solution {
    public int maximalSquare(char[][] matrix) {
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        int ans = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        return ans * ans;
    }
}

/**
 * dp. 我是怎么也想不出来.
 * 
 * 来看思路吧. 遇到一个格子是1. 那么我们就要看它能和自己的左, 上和左上能否构成正方形.
 * dp[i][j]存的是右下角为i, j的square中边长最长的side length. 如果一个格子要和左, 上和左上构成
 * 正方形, 那必须左, 上, 左上都要是1. 如果满足的话, 至少可以构成length是2的正方形了. 那能不能再接着往上
 * 构造呢?
 * 
 * 我们就要看从当前格子往左能延伸多少, 往上能延伸多少. 于是自然我们会看我们上方格子的左边格子的dp值.
 * 我们要取两个格子中小的那个dp值. 因为我们要保证两侧都能延伸相同的距离. 于是现在我们
 * 要考虑延伸过后构成的矩形内部的格子都能保证是1吗? 看我在word的图. 我们发现延伸过后, 框起来的区域只有一个地方不确定,
 * 那就是左上角. 左上角是1吗? 于是我们引入第三个要被比较的dp值就是dp[i - 1][j - 1]. 这个值决定了是否能够
 * 到达左上角的位置. 首先这个值肯定是大于等于我们上面获得的dp最小的那个值再 - 1的, 因为只有左上角不确定是1, 其他都确定都是1.
 * 那么就看dp[i - 1][j - 1]和上面我们得到的最小值比较, 如果比它大或相等, 那么左上角可以cover到, 没毛病.
 * 如果比它们小(也就是小一个1). 那么gg, 只能达到dp[i - 1][j - 1]的边长再加个现在我们在的位置的长度也就是1.
 * 因此dp的关系就出来了, 就是dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]).
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m * n)
 */

class Solution {
    public int maximalSquare(char[][] matrix) {
        int[] dp = new int[matrix[0].length + 1];
        int diagonal = 0;
        int ans = 0;
        for (int i = 1; i <= matrix.length; i++) {
            for (int j = 1; j <= matrix[0].length; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), diagonal) + 1;
                    ans = Math.max(ans, dp[j]);
                } else {
                    dp[j] = 0;
                }
                diagonal = temp;
            }
        }
        return ans * ans;
    }
}
/**
 * 和通常的二维缩一维的思路一样. 就是开始遍历前, dp存的是上一行每一个位置的dp值, 我们来到dp[1], 此时的
 * dp[1]是上一行该位置的dp, dp[0]是这一行左边一个位置的dp, 于是我们利用这两个来更新dp[1].
 * 但是我们需要知道左上角那个dp是什么. 如果我们到达位置i, j, 此时dp[j - 1]的值
 * 是我们左侧格子为右下角能达到最大square的side length. 因此在更新dp[j - 1]之前, 我们需要把它之前的
 * 值也就是上一行该位置的最大square的side length存一下, 到一个变量中. 也就是更新一个dp前把它的值先给到
 * 一个中间变量, 然后更新, 之后再把中间变量的值给到存左上角的那个dp的变量中去.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(n)
 */