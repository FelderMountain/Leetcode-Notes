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
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null)
            return false;
        if (isSameTree(root, subRoot))
            return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null)
            return p == q;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
/**
 * 很简单, 就是isSameTree的变种. isSameTree的模板要记牢.
 * 
 * 时间复杂度: O(m * n) m是root中nodes的个数. 比较坏的情况就是我们在遍历完所有nodes后也没找到.
 * 由于isSameTree是O(n), 我们每个isSubtree都会调用isSameTree, 因此是O(m * n)
 * 空间复杂度: O(m) 不会超过root nodes的总数.
 */