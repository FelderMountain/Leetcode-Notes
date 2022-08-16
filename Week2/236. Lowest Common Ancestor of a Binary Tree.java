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
        List<TreeNode> routeOne = new ArrayList<>();
        List<TreeNode> routeTwo = new ArrayList<>();
        helper(routeOne, root, p);
        helper(routeTwo, root, q);
        int ptrOne = 0, ptrTwo = 0;
        TreeNode result;
        while (ptrOne < routeOne.size() && ptrTwo < routeTwo.size() && routeOne.get(ptrOne) == routeTwo.get(ptrTwo)) {
            ptrOne += 1;
            ptrTwo += 1;
        }
        if (ptrOne == routeOne.size()) {
            result = routeOne.get(ptrOne - 1);
        } else {
            result = routeTwo.get(ptrTwo - 1);
        }
        return result;
    }

    public boolean helper(List<TreeNode> route, TreeNode node, TreeNode target) {
        if (node == target) {
            route.add(node);
            return true;
        }

        route.add(node);
        boolean found = false;

        if (node.left != null)
            found = helper(route, node.left, target);

        if (found)
            return true;

        if (node.right != null)
            found = helper(route, node.right, target);

        if (found)
            return true;

        route.remove(route.size() - 1);
        return false;
    }
}

/**
 * 这个是我写的答案. 思路就是找到到两个目标node的路, 记录下来, 然后两个指针分别指向这两条路的
 * 开头, 如果发生分叉或者一方先走完, 那就表示找到了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(h) h是二叉树的高度.
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

    public class Info {
        TreeNode result;
        int foundNum;

        Info(TreeNode result, int foundNum) {
            this.result = result;
            this.foundNum = foundNum;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Info info = new Info(null, 0);
        helper(root, p, q, info);
        return info.result;
    }

    // result == 0 neither is found
    // result == 1 first node is found
    // result == 2 second node is found
    private void helper(TreeNode node, TreeNode p, TreeNode q, Info info) {
        int currentFoundNum = info.foundNum;
        if (node == p || node == q) {
            info.foundNum += 1;
            if (info.foundNum == 2)
                return;
        }
        if (node.left != null) {
            helper(node.left, p, q, info);
        }

        // Find the lowest?
        if (info.result != null)
            return;
        else if (info.foundNum - currentFoundNum == 2) { // If this node is the lowest?
            info.result = node;
            return;
        }
        if (node.right != null) {
            helper(node.right, p, q, info);
        }
        if (info.result == null && (info.foundNum - currentFoundNum) == 2) {
            info.result = node;
        }

    }
}
/**
 * 根据答案提示, 写回溯. 回溯分情况, 第一种是回来的时候一个没找到或找到一个, 那么继续找. 第二种是回来的时候找到两个, 此时就要看
 * result里面有东西没, 如果没有那自己就是答案, 如果有就直接return, 因为有比自己更靠下的node是lowest common
 * ancestor.
 * 
 * 递归函数功能就是走DFS找两个node的lowest common ancestor, 并记录目前为止找到的几个target node了.
 * 如何确定lowest common呢? 肯定是去的时候找到0个, 回来的时候就找到了两个的那个node. 那么lowest及以上的
 * node都符合这个条件. 由于是DFS, 返回也是从深到浅返回, 于是第一个满足这个条件的node就是lowest common.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */