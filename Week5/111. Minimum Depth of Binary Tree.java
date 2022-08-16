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
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMinDepth = minDepth(root.left);
        int rightMinDepth = minDepth(root.right);
        return (leftMinDepth == 0 || rightMinDepth == 0) ? leftMinDepth + rightMinDepth + 1
                : Math.min(leftMinDepth, rightMinDepth) + 1;
    }
}

/**
 * DFS的解法. 递归函数告诉我们给定的一个tree的min depth是多少. 需要注意的是, 如果一支为null, 那么
 * 该tree的minDepth就是另一支的minDepth + 1. 如果二者都为null, 那么该tree的minDepth就是1. 如果
 * 两支都不是null, 那么取最小的那个 + 1即可. 上面的叙述可以简单的浓缩为return的那一行, 很美.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

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
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int currDepth = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode currNode = q.poll();
                if (currNode.left == null && currNode.right == null) {
                    return currDepth;
                }
                if (currNode.left != null) {
                    q.offer(currNode.left);
                }
                if (currNode.right != null) {
                    q.offer(currNode.right);
                }
            }
            currDepth += 1;
        }
        return currDepth;
    }
}
/**
 * BFS的做法. DFS不是很高效, 因为假设一个root左支只有一个node, 右支有特别多的node. 那么我们需要遍历完所有的
 * node才能得出答案. 但是BFS则是发现左支是leaf node时, 直接返回.
 * 
 * 时间复杂度: O(n) perfect tree.
 * 空间复杂度: O(n)
 */