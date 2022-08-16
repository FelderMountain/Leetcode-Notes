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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null)
            return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        helper(ans, new ArrayList<>(), root, 0, targetSum);
        return ans;
    }

    private void helper(List<List<Integer>> ans, List<Integer> currentNodes, TreeNode node, int currSum, int target) {
        if (node == null)
            return;
        currSum += node.val;
        currentNodes.add(node.val);
        if (node.left == null && node.right == null && currSum == target) {
            ans.add(new ArrayList<>(currentNodes));
        }
        helper(ans, currentNodes, node.left, currSum, target);
        helper(ans, currentNodes, node.right, currSum, target);
        currentNodes.remove(currentNodes.size() - 1);
    }
}
/**
 * 就是DFS. 只是要注意, 一定是从root到leaf, 也就是最后一个node加上后等于targetSum时, 这个node必须是leaf node.
 * 第30行的那个判断很重要.
 * 
 * 时间复杂度: O(n^2) 遍历所有的nodes.
 * 我们在leaf处可能会new出来一个新的arraylist并且把currentNodes里面的元素加进去. 此时就是O(N)
 * 想象这样一个树, 是个链表, 但是每一个node还额外延伸出一个node. 这样沿着这个链表, 每到一个node就可以
 * 到达它延伸出来的这个node, 延伸出来的这个就是leaf node. 假设targetSum是0, 所有的tree node的值也是0,
 * 那么我们每到一个leaf node就要添加一遍. 因此是O(N^2)
 * 空间复杂度: O(n) 可能是个链表.
 */