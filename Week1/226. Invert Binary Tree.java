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
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return root;
        if (root.left != null)
            invertTree(root.left);
        if (root.right != null)
            invertTree(root.right);
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }
}

// 这个就没啥好说的. 反转二叉树.

// 时间复杂度: O(n) n是node的总数量. 相当于是每个node都要翻转一下自己的两个node
// 空间复杂度: O(h) h是树的高度.
