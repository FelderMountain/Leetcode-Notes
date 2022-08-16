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
    public boolean isSymmetric(TreeNode root) {
        return helper(root.left, root.right);
    }

    private boolean helper(TreeNode p, TreeNode q) {
        if (p == null || q == null)
            return p == q;
        return p.val == q.val && helper(p.left, q.right) && helper(p.right, q.left);
    }
}

/**
 * 就是root的左树和右树同时进行dfs. 边走边比较. 只不过左树先走左边后走右边而
 * 右树先走右边再走左边.
 * 
 * 如果出现p或者q是null了, 就说明一方要返回了. 我们就要看两个是不是都是null, 但凡有一个不是,
 * 就是返回false.
 * 
 * DFS很简单, 给一个base case再调用自己就是DFS.
 */

class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root.left);
        stack.push(root.right);
        while (!stack.isEmpty()) {
            TreeNode nodeOne = stack.pop();
            TreeNode nodeTwo = stack.pop();
            if (nodeOne == null && nodeTwo == null)
                continue;
            if (nodeOne == null || nodeTwo == null || nodeOne.val != nodeTwo.val)
                return false;
            stack.push(nodeOne.left);
            stack.push(nodeTwo.right);
            stack.push(nodeOne.right);
            stack.push(nodeTwo.left);
        }
        return true;
    }
}
/**
 * 这是stack的写法. 我们可以把两边对称位置上的node看作一个pair. 我们每次压入的就是我们pop出来的
 * 两个nodes衍生出来的两个pairs. nodeOne.left和nodeTwo.right组成一个pair, nodeOne.right和
 * nodeOne.left组成一个pair. 因为可能需要有null能被装入, 因此我们没法选queue, 至于先看哪两个pair没有关系
 * 不需要严格按照BFS的顺序, 因此使用了stack.
 */