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
    private int count = 0;

    public int pathSum(TreeNode root, int targetSum) {
        if (root == null)
            return 0;
        Map<Long, Integer> sumCount = new HashMap<>();
        dfs(sumCount, root, 0L, targetSum);
        return count;
    }

    private void dfs(Map<Long, Integer> sumCount, TreeNode node, long currSum, int target) {
        currSum += node.val;
        long remaining = currSum - target;
        if (remaining == 0) {
            count += 1;
        }
        if (sumCount.containsKey(remaining)) {
            count += sumCount.get(remaining);
        }
        sumCount.put(currSum, sumCount.getOrDefault(currSum, 0) + 1);
        if (node.left != null) {
            dfs(sumCount, node.left, currSum, target);
        }
        if (node.right != null) {
            dfs(sumCount, node.right, currSum, target);
        }
        sumCount.put(currSum, sumCount.get(currSum) - 1);
    }
}
/**
 * prefix sum的应用. prefix sum的好处就是想知道某个subarray能否凑到一个target k.
 * 精髓在于假设当前的位置为j, 我们知道[0, i]的sum是多少(0 <= i <= j). 于是之前的任何范围内
 * (i, j]的sum可以表示为[0, j]sum减去[0, i]. 560题见具体的题目. 我们在hashmap中记录某个
 * sum有多少个位置能够达到([0, i], 多少个不同的i能达到).
 * 
 * binary tree唯一不一样的就是我们返回的时候, 我们要把从root到当前node的sum在hashmap中的count - 1.
 * 也就是43行. 这样map中始终存的是从root到当前node, 每一个node位置到root加在一起的sum的出现的count.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 一个是用栈, 还有就是map中存prefix sum.
 */