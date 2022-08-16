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
    public List<Double> averageOfLevels(TreeNode root) {
        List<List<Double>> levelSum = new ArrayList<>();
        helper(root, 0, levelSum);
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < levelSum.size(); i++) {
            ans.add(levelSum.get(i).get(0) / levelSum.get(i).get(1));
        }
        return ans;
    }

    private void helper(TreeNode node, int level, List<List<Double>> levelSum) {
        if (node == null) {
            return;
        }
        if (level == levelSum.size()) {
            levelSum.add(new ArrayList<>(Arrays.asList((double) node.val, (double) 1)));
        } else {
            List<Double> levelList = levelSum.get(level);
            levelList.set(0, levelList.get(0) + node.val);
            levelList.set(1, levelList.get(1) + 1);
        }
        helper(node.left, level + 1, levelSum);
        helper(node.right, level + 1, levelSum);
    }
}

/**
 * DFS. 把每一个level的sum和count存到list中去. 有个总list存各个level情况(list).
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            double sum = 0, count = 0;
            for (int i = 0; i < size; i++) {
                TreeNode currNode = q.poll();
                sum += (double) currNode.val;
                count += 1;
                if (currNode.left != null) {
                    q.offer(currNode.left);
                }
                if (currNode.right != null) {
                    q.offer(currNode.right);
                }
            }
            ans.add(sum / count);
        }
        return ans;
    }
}
/**
 * BFS的解法.
 */