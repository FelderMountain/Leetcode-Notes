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
        int xDepth;
        int yDepth;
        TreeNode xParent;
        TreeNode yParent;
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        Info info = new Info();
        dfs(root, null, x, y, 0, info);
        if (info.xDepth == info.yDepth && info.xParent != info.yParent) {
            return true;
        }
        return false;
    }

    private void dfs(TreeNode node, TreeNode parent, int x, int y, int depth, Info info) {
        if (node == null) {
            return;
        }
        if (node.val == x) {
            info.xDepth = depth;
            info.xParent = parent;
        } else if (node.val == y) {
            info.yDepth = depth;
            info.yParent = parent;
        }
        dfs(node.left, node, x, y, depth + 1, info);
        dfs(node.right, node, x, y, depth + 1, info);
    }
}

/**
 * DFS一遍, 来获得x和y的depth以及它们的parent.
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
    public boolean isCousins(TreeNode root, int x, int y) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            boolean foundX = false, foundY = false;
            for (int i = 0; i < size; i++) {
                TreeNode currNode = q.poll();
                if (currNode.val == x) {
                    foundX = true;
                }
                if (currNode.val == y) {
                    foundY = true;
                }
                if (currNode.left != null && currNode.right != null) {
                    if ((currNode.left.val == x && currNode.right.val == y)
                            || (currNode.left.val == y && currNode.right.val == x)) {
                        return false;
                    }
                }
                if (currNode.left != null) {
                    q.offer(currNode.left);
                }
                if (currNode.right != null) {
                    q.offer(currNode.right);
                }
            }
            if (foundX && foundY) {
                return true;
            } else if (foundX || foundY) {
                return false;
            }
        }
        return false;
    }
}
/**
 * BFS的解法. 一开始我们level order traversal即可. 如果x和y都没发现, 那么继续. 如果只发现一个, 那么return
 * false. 如果到了发现了x和y, 那么就说明...等等, 如果此时x和y同一个parent呢? 这就麻烦了. 如何捕捉到这种情况?
 * 如果x和y是前后相邻被pop出? 这也不一定, 可能两个node有不同的parent但是pop出来时是相邻的. 于是这就增加了一个判定,
 * 在我们判断当前node是x或者y后准备向queue中压入我们的children时, 我们要看一下如果自己两个child都不是null, 判断
 * 他俩不是一个x一个y, 如果是, 直接返回false, 否则就正常添加. 这样就cover到了x和y同属一个parent的情况.
 * 
 * 时间复杂度: O(N) perfect binary tree
 * 空间复杂度: O(N) perfect binary tree
 */