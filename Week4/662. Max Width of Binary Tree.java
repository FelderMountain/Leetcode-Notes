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
    private int maxWidth = 0;

    public int widthOfBinaryTree(TreeNode root) {
        helper(root, 0, 1, new ArrayList<>());
        return maxWidth;
    }

    private void helper(TreeNode node, int depth, int index, List<Integer> leftMostIndex) {
        if (node == null)
            return;
        if (depth == leftMostIndex.size()) {
            leftMostIndex.add(index);
        }
        int currWidth = index - leftMostIndex.get(depth) + 1;
        maxWidth = Math.max(currWidth, maxWidth);
        helper(node.left, depth + 1, index * 2, leftMostIndex);
        helper(node.right, depth + 1, index * 2 + 1, leftMostIndex);
    }
}

/**
 * 用一个list来存每一个level最左边node的index. 假设root index是1, 然后左node是2, 右node是3
 * 按照这个方法去编号. 假设某个node的index是n, 那么它的左node的index就是2n, 右node的index就是2n + 1.
 * 
 * 然后DFS一遍, 如果遇到某个level的最左侧node的index没有存, 那么我们就把当前node的index存到相应的位置,
 * 因为DFS一定是把每个level最靠左的node先达到, 如果到达一个level发现该level没有存储最左侧node的index, 这
 * 只能说明当前node就是该level最左侧的node. 然后计算当前node和当前level最左侧node的距离, 然后再更新maxWidth.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 链表
 *
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
    class NodeIdxPair {
        int index;
        TreeNode node;

        NodeIdxPair(int index, TreeNode node) {
            this.index = index;
            this.node = node;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        int maxWidth = 0;
        Queue<NodeIdxPair> q = new LinkedList<>();
        q.offer(new NodeIdxPair(1, root));
        while (!q.isEmpty()) {
            int currLength = q.size();
            int leftMostIndex = q.peek().index;
            for (int i = 0; i < currLength; i++) {
                NodeIdxPair currPair = q.poll();
                int width = currPair.index - leftMostIndex + 1;
                maxWidth = Math.max(maxWidth, width);
                if (currPair.node.left != null) {
                    q.offer(new NodeIdxPair(currPair.index * 2, currPair.node.left));
                }
                if (currPair.node.right != null) {
                    q.offer(new NodeIdxPair(currPair.index * 2 + 1, currPair.node.right));
                }
            }
        }
        return maxWidth;
    }

}
/**
 * BFS的解法, 也很直接. 在遍历一个level前, 这一个level在queue中最靠前的那个node, 它的index是当前level最靠前的.
 * 因此先记录一下它, 然后遍历这一层的所有node, 看看它们到leftMostIdx的距离是否比maxWidth大. 比较完后再把自己非null
 * 的child压入queue中.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */