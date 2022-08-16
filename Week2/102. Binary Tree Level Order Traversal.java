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
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int length = q.size();
            ArrayList<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                TreeNode node = q.poll();
                currentLevel.add(node.val);
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
            }
            res.add(currentLevel);
        }
        return res;
    }
}

/**
 * 就是典型的BFS. 没什么好说的.
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 假如是个满树, 最后一行包含的是N / 2个node. 那不就是O(N)了.
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
    List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        helper(0, root);
        return res;
    }

    public void helper(int level, TreeNode node) {
        if (node == null)
            return;
        if (level == res.size())
            res.add(new ArrayList<Integer>());

        res.get(level).add(node.val);
        helper(level + 1, node.left);
        helper(level + 1, node.right);
    }
}

/*
 * DFS的解法.
 */