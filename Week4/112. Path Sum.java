/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        return helper(root, 0, targetSum);
    }

    private boolean helper(TreeNode node, int sum, int targetSum) {
        sum += node.val;
        if (node.left == null && node.right == null && sum == targetSum)
            return true;
        if (node.left != null && helper(node.left, sum, targetSum))
            return true;
        if (node.right != null)
            return helper(node.right, sum, targetSum);
        return false;
    }
}
/**
 * 就是backtrack没什么好说的. 当时我突然意识到这个backtrack从某个递归函数出发, 又最终回到该递归函数.
 * 于是我们通过合理的设置递归函数的返回值就能知道backtrack过程中是否遇到了我们想要的结果. 比如这道题
 * 就是让递归函数的返回值是boolean就是说明是否到某个leaf凑到了targetSum. 比如25行递归下去, 又回来,
 * 如果递归函数告诉我们有leaf达到了targetSum, 那么我们直接返回true即可, 告诉上层下面有找到, 不必再走
 * node.right.
 */