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
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        helper(root);
    }

    public TreeNode helper(TreeNode node) {
        if (node.left == null && node.right == null) {
            return node;
        }
        TreeNode leftSubTreeEnd = node.left == null ? null : helper(node.left);
        TreeNode rightSubTreeEnd = node.right == null ? null : helper(node.right);
        if (leftSubTreeEnd != null) {
            leftSubTreeEnd.right = node.right;
            node.right = node.left;
            node.left = null;
        }
        return rightSubTreeEnd == null ? leftSubTreeEnd : rightSubTreeEnd;
    }
}
/**
 * 就是递归的使用. 递归函数的功能是把给定的tree给flatten了, 并返回最后一个node.
 * base case就是传入的node是leaf node的时候, 直接返回该node.
 * 
 * 时间复杂度: O(n) n是node的个数.
 * 空间复杂度: O(n) 可能tree是left skewed tree.
 */