public class Solution {
    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return max;
    }

    // Given a node, it will update the global varibale max if
    // there is larger diameter in this tree and return the max
    // height of this tree.
    private int maxDepth(TreeNode root) {
        if (root == null)
            return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        max = Math.max(max, left + right);

        return Math.max(left, right) + 1;
    }
}
/**
 * 这个递归函数的功能就是给它一个node, 它能把它所代表的tree里面的最大的diamter存到max中, 并返回该node
 * 代表的树的max height. 这样逻辑就明白了. 先让左树中最大的diameter更新到max中, 再右树, 最后不要忘了
 * 自己所在的node也有可能成为最大. 于是left + right和max比一下. 然后返回就是left和right中大的那个height + 1
 * 即可.
 * 
 * 这个递归函数的功能取得好. 牛逼.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 因为可能是个链表
 */