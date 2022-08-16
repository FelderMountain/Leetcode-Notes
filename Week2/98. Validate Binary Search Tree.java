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
    public boolean isValidBST(TreeNode root) {
        return helper(null, null, root);
    }

    public boolean helper(Integer low, Integer up, TreeNode node) {
        if (node == null)
            return true;
        if ((low != null && node.val <= low) || (up != null && node.val >= up))
            return false;
        return helper(low, node.val, node.left) && helper(node.val, up, node.right);
    }
}
/**
 * DFS. 就是给定一个node, 就能告诉我们这个node以及它包含的node是否符合BST要求. 假设给定一个node, 就知道
 * 它所代表的tree是否是BST是没用的. 因为假如我的左树是BST, 右树也是, 我可以取一个值让我所代表的树不是BST.
 * 因此我们要做的是告诉每个node它们可以取的范围是多少, 让它们判断.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 可能是skewed tree.
 */