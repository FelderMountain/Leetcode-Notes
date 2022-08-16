/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    TreeNode successor;
    TreeNode lastVisited;
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        dfs(root, p);
        return successor;
    }
    
    private void dfs(TreeNode node, TreeNode target) {
        if (node == null)
            return;
        dfs(node.left, target);
        if (successor != null)
            return;
        if (lastVisited == target) {
            successor = node;
            return;
        }
        lastVisited = node;
        dfs(node.right, target);
    }
    
}
/**
 * 用一个全局变量来存最近visit的那个node是什么. 然后dfs遍历所有的node即可.
 * 遇到某个node的上一个是target的, 就返回该node.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode currNode = root;
        TreeNode lastValidParent = null;
        while (currNode != p) {
            if (p.val < currNode.val) {
                lastValidParent = currNode;
                currNode = currNode.left;
            } else {
                currNode = currNode.right;
            }
        }
        if (currNode.right != null) {
            currNode = currNode.right;
            while (currNode.left != null) {
                currNode = currNode.left;
            }
            return currNode;
        }
        return lastValidParent;
    }
}
/**
 * 这个方法就是我们先根据BST的性质找到这个p. 如果p有右树, 那么successor就是右树最左边的那个node.
 * 如果没有我们要开始从p原路向上走, 如果p是它的parent的左node, 那么它的parent就是答案. 如果是它的parent的
 * 右node, 这表明p的parent的代表的树遍历完毕, 我们需要继续向上, 再看p的parent是它的parent的哪个node, 如果是
 * 左node, 那么p的parent的parent就是答案, 如果是右node, 那么我们继续走, 以此类推.我们一直走要么就是遇到某个node是自己parent
 * 的左node, 然后此时该node的parent就是答案, 要么就是到达root的时候也没有遇到这种情况, 此时答案就是null.
 * 
 * 由于我们没有parent这个指针. 因此我们用一个变量来记录我们找p的时候, 看哪个node作为了它的parent的左node, 我们记录这个
 * parent, 只要遇到这样的parent我们就更新我们用来记录的变量来存最近遇到的满足我们要求的parent. 那么等到p往上回去找的时候, 
 * 一定会原路返回到达这个node, 此时该node是自己parent的左node, 因此这个parent就是答案.
 * 这里需要注意的是只要在找的过程中遇到某个node是它parent的左node, 我们就更新我们的变量, 记录下这个parent, 因此等到最后找到p的时候, 
 * 我们记录的这个parent是距离p最近的(沿着找的路线回去的时候第一个会遇到的满足我们要求的parent).
 * 
 * 时间复杂度: Average: O(logn) Worst: O(n) 可能是个链表.
 * 空间复杂度: O(1)
 */

 /**
  * 我之前一直在尝试, 如何到达某个node知道它的前一个node是什么. 如果它有左树, 那么最后一个node就是左树最左边的那个.
  如果没有, 那么就是一路向上, 直到到达某个node时, 是从该node的右侧回到该node的, 不能简单的认为如果该node不是自己parent的right
  node, 这个node就没有prev了, 因为自己的parent可能还是它的parent的right node, 此时自己parent的parent就是prev node. 当然parent的
  parent可能也不符合条件, 需要我们继续找, 直到找到root, 才能证明我们的prev是null. 因此某个node的prev node既可能在自己的上方,
  又可能在自己的下方. 所以用一个global variable记录last visited node是比较好的方法.
  */


  class Solution {
    
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        
        TreeNode successor = null;
        
        while (root != null) {
            
            if (p.val >= root.val) {
                root = root.right;
            } else {
                successor = root;
                root = root.left;
            }
        }
        
        return successor;
    }
}
/**
 * 这个方法等于是把我们的方法二的两种情况结合了. 也就是遇到在某个node要左拐的时候, 我们就记录下这个node,
 * 一直到我们找到p的时候, 我们记录的就是距离p最近的满足我们条件的parent. 此时如果我们还有右树,
 * 那么我们就会右拐(p.val == root.val), 之后遇到的值肯定都大于p.val(因为是p的右树), 因此我们一直
 * 会左拐, 直到到达null. 此时记录的就是p右树最左边的node.
 * 
 * 时间复杂度和空间复杂度不变.
 */

/**
 * 网友分享的答案. 思路就是我们给一个node以及target, 递归函数告诉我们这个node代表的tree中target的successor是谁.
 */
class Solution {
    public TreeNode successor(TreeNode root, TreeNode p) {
        if (root == null)
          return null;
      
        if (root.val <= p.val) {
          return successor(root.right, p);
        } else {
          TreeNode left = successor(root.left, p);
          return (left != null) ? left : root;
        }
    }
}
/**
 * 这个思路很巧妙. 如果p.val > root.val那么p在root.right所在的树中, 我们直接
 * return helper(root.right, target)
 * 如果p.val < root.val, p在左树中, 但是很有可能p没有右树, 于是我们记录此时的node是什么.
 * 然后去看左树中有没有successor.
 * 如果p.val == root.val, 我们找到了p, 此时我们看右树有没有node, 因此继续调用
 * helper(root.right, target). 如果它返回null, 就说明右树没有successor, 我们直接return即可.
 */
