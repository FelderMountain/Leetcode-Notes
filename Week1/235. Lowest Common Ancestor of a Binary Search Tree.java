/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode currentNode = root;
        while (true) {
            if (p.val < currentNode.val && q.val < currentNode.val) {
                currentNode = currentNode.left;
            } else if (p.val > currentNode.val && q.val > currentNode.val) {
                currentNode = currentNode.right;
            } else {
                return currentNode;
            }
        }
    }
}

/**
 * 这道题没什么说的. 但是我的思路细节需要记录一下.
 * 
 * 我最初分成了以下的一些情况:
 * 1. 两个node都比currentNode小, 于是这两个的lowest common ancestor一定在currentNode的左边.
 * 更新currentNode.
 * 2. 两个node都比currentNode大, 于是这两个的lowest common ancestor一定在currentNode的右边.
 * 更新currentNode.
 * 3. 其中p就是currentNode, 那么返回p.
 * 4. 其中q就是currentNode, 那么返回q.
 * 5. p小于currentNode并且q大于currentNode或者p大于currentNode并且q小于currentNode,
 * 那么返回currentNode.
 * 
 * 这个思路的代码可以写成:
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode currentNode = root;
        while (true) {
            if (p.val < currentNode.val && q.val < currentNode.val) {
                currentNode = currentNode.left;
            } else if (p.val == currentNode.val) {
                return p;
            } else if (q.val == currentNode.val) {
                return q;
            } else if (p.val > currentNode.val && q.val > currentNode.val) {
                currentNode = currentNode.right;
            } else if (p.val < currentNode.val && q.val > currentNode.val
                    || p.val > currentNode.val && q.val < currentNode.val) {
                return currentNode;
            }
        }
    }
}

// 但是其实上面情况的3, 4, 5都是返回currentNode. 3中的返回p和4中的返回q其实和返回currentNode一样. 因此就简化成了
// 最上面的那个solution了. 没毛病.

// 时间复杂度: O(n) 这是因为我们可能遍历所有的node后才能得到答案比如链表形式的tree.
// 空间复杂度: O(1)