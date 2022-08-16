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
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        List<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            int length = q.size();
            TreeNode rightMostNode = q.peek();
            ans.add(rightMostNode.val);
            for (int i = 0; i < length; i++) {
                TreeNode currentNode = q.poll();
                if (currentNode.right != null)
                    q.offer(currentNode.right);
                if (currentNode.left != null)
                    q.offer(currentNode.left);
            }
        }
        return ans;
    }
}

/**
 * BFS, 但是装的时候先装右node, 再装左node. 一层一层往外pop. 每一层第一个pop出来的加入到ans中, 它就是
 * 该层最靠右的.
 */

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        helper(ans, 0, root);
        return ans;
    }

    private void helper(List<Integer> ans, int level, TreeNode node) {
        if (ans.size() == level) {
            ans.add(node.val);
        }
        if (node.right != null) {
            helper(ans, level + 1, node.right);
        }
        if (node.left != null) {
            helper(ans, level + 1, node.left);
        }
    }
}
/**
 * 这道题的启发就是正常的binary tree的dfs, 我们把node按照dfs的顺序存到一个list中,
 * 那么对于每一层的node, 越靠左左边的node会越先被visit. 比如我们把某一层的node在list中
 * 标记出来, 这些node在该层的从左到右的相对顺序在list中仍然拥有相同的相对顺序, 维持不变. 只不过
 * 它们之间可能夹杂着其他的node, 但是在某层中靠左的node, 在list中也会靠左. 某一层的node间的相对顺序
 * 在list中也是一样.
 * 
 * 时间复杂度: O(n) 要遍历所有的node.
 * 空间复杂度: O(n) 可能是skewed tree.
 */