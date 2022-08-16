class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            int ans = 0;
            for (int j = 1; j <= i; j++) {
                int leftNodesCount = j - 1;
                int rightNodesCount = i - j;
                ans += dp[leftNodesCount] * dp[rightNodesCount];
            }
            dp[i] = ans;
        }
        return dp[n];
    }
}
/**
 * 给定一个数字n, 那么就会有从1到n共n个nodes. 轮流让它们当root node. 如果
 * 1当, 那么1左侧的nodes组成的BST的个数乘以1右侧的nodes组成的BST的个数就是
 * 1作为root的所有BST的个数. 同理让2当, 2左侧的nodes组成的BST个数乘以2右侧
 * nodes组成的BST个数就是2作为root的能组成BST的总个数. 此时我们会发现, 1作
 * root时, 我们需要知道1右侧nodes能组成多少BST, 也就是n - 1个nodes能组成多少.
 * 2做root时, 我们需要知道2左侧nodes也就是1个node能组成多少BST, 2右侧nodes也就是
 * n - 2个nodes能组成多少BST, 以此类推. 因此我们需要知道1个node能组成多少, 2个nodes能组成
 * 多少...
 * 这就让我们想到使用DP来解. dp[i]表示的是i个nodes能组成多少个BST. 于是就有了上面的答案.
 * dp[i]的得到就是让前i个nodes每个node轮流当root, 来看每个node做root时左侧nodes组成的BST个数
 * 以及右侧nodes组成的BST个数, 让这两个相乘就是该node做root能组成的BST个数.
 * 需要注意的是dp[0]初始为是0还是1. 答案是1. 假设1作为root, 左侧0个nodes, 右侧n - 1个nodes. 于是
 * 左树只有一种情况就是一个node也没有, 右侧看n - 1个nodes能组成多少种BST, 二者一相乘即可.
 * 
 * 因此dp的初始条件取一些例子看看是什么样即可.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(n)
 */