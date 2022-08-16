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
    static private class Info {
        int result;
        int num;

        Info(int num, int result) {
            this.num = num;
            this.result = result;
        }
    }

    public int kthSmallest(TreeNode root, int k) {
        Info info = new Info(0, -1);
        helper(root, k, info);
        return info.result;
    }

    private void helper(TreeNode node, int k, Info info) {
        if (node.left != null) {
            helper(node.left, k, info);
        }
        if (info.num == k)
            return;
        info.num += 1;
        if (info.num == k) {
            info.result = node.val;
            return;
        }
        if (node.right != null)
            helper(node.right, k, info);

    }
}

/**
 * DFS解法. 递归函数的功能就是给定一个node, 它能把该node所有的node按照in order遍历, 根据info, 能够在info
 * 里面的num == k时停止遍历, 并把此时的结果记录在info.result中.
 * 
 * 时间复杂度: O(H + K) 可以想象一个root它左branch很长, 在该branch中的某个node往右分出一个node, 其他在该branch中
 * 的nodes都不会分出右支. 这个唯一被分出去的node就是我们要的答案. 那么我们需要做的是把root node的左branch走到底
 * 然后再上来, 到达这个分支点往右走, 找到第k个, 然后返回. 此时就是h + k. 大概想表达的意思就是我们找第k个最小不意味着我们走k
 * 次就能找到, 而是要走遍某一支甚至多支, 回来的时候才能找到.
 * 
 * 我个人觉得这里的H是第K小的node所在的depth. 相当于是我们肯定要遍历完所有比k小的node, 同时我们达到K这个node也需要时间.
 * 需要的时间和K所在的深度有关. 这样的话O(H + K)就比较合理了.
 * 
 * 
 * 空间复杂度: O(H) 相当于是某一个branch走到底.
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
    public int kthSmallest(TreeNode root, int k) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (true) {
            // 往左走.
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            // 走到头了.
            node = stack.pop();
            if (--k == 0)
                return node.val;

            // 往右走.
            node = node.right;
        }
    }
}
/**
 * 这个是iterative solution. 等于是模仿递归, 把TreeNode都存在栈里面. 这个方法写得好漂亮.
 * 需要重点记忆.
 * 
 * 时间复杂度和空间复杂度一样.
 */