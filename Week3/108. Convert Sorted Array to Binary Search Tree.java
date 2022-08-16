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
    static class Node {
        int low, up;
        TreeNode node;

        Node(int low, int up, TreeNode node) {
            this.low = low;
            this.up = up;
            this.node = node;
        }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode root = new TreeNode();
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(0, nums.length - 1, root));
        while (!stack.isEmpty()) {
            Node currNode = stack.pop();
            int middle = currNode.low + (currNode.up - currNode.low) / 2;
            currNode.node.val = nums[middle];
            if (currNode.low <= middle - 1) {
                currNode.node.left = new TreeNode();
                stack.push(new Node(currNode.low, middle - 1, currNode.node.left));
            }

            if (currNode.up >= middle + 1) {
                currNode.node.right = new TreeNode();
                stack.push(new Node(middle + 1, currNode.up, currNode.node.right));
            }
        }
        return root;
    }
}

/**
 * 这个是iterative solution. 把一个新node和这个node所在范围封装成一个Node类, 我们用栈去存.
 * pop出来一个, 我们就赋值然后继续看它的children在不在合理的range里面.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    private TreeNode helper(int[] nums, int left, int right) {
        if (left > right)
            return null;
        int middle = left + (right - left) / 2;
        TreeNode newNode = new TreeNode(nums[middle]);
        newNode.left = helper(nums, left, middle - 1);
        newNode.right = helper(nums, middle + 1, right);
        return newNode;
    }
}
/**
 * 递归解法.
 * 时间复杂度: O(n)
 * 空间复杂度: O(logn)
 */