// From right to left, pop last, add to first
// From left to right, pop first, add to last
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        boolean fromLeft = true;
        Deque<TreeNode> q = new LinkedList<>();
        q.offer(root);
        List<List<Integer>> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            int currLength = q.size();
            List<Integer> currLevel = new ArrayList<>();
            for (int i = 0; i < currLength; i++) {
                TreeNode currNode = fromLeft ? q.removeFirst() : q.removeLast();
                currLevel.add(currNode.val);
                if (fromLeft) {
                    if (currNode.left != null) {
                        q.addLast(currNode.left);
                    }
                    if (currNode.right != null) {
                        q.addLast(currNode.right);
                    }
                } else {
                    if (currNode.right != null) {
                        q.addFirst(currNode.right);
                    }
                    if (currNode.left != null) {
                        q.addFirst(currNode.left);
                    }
                }
            }
            ans.add(currLevel);
            fromLeft = !fromLeft;
        }
        return ans;
    }
}

/**
 * 思路就是我们每一层level都是让node保持在原来的order. 如果从左向右, 那么就是从头部pop,
 * 如果从右向左那就是从尾部pop. 对于add该level node的child, 如果当前level从左向右, 那么child应该
 * 加在末尾, 并且按照先添加左, 再添加右的原则. 如果当前level从右向左, 那么child应该加在开头,
 * 并且按照先添加右再添加左, 这样能保证child在list里面的顺序还是和在树中该level的顺序相同.
 * 
 * 时间复杂度: O(n) n是node的个数.
 * 空间复杂度: O(n) 可能是balanced tree.
 */

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList();
        travel(res, 0, root);
        return res;
    }

    private void travel(List<List<Integer>> res, int level, TreeNode cur) {
        if (cur == null)
            return;
        if (res.size() <= level) {
            res.add(new ArrayList<Integer>());
        }
        if (level % 2 == 0) {
            res.get(level).add(cur.val);
        } else {
            res.get(level).add(0, cur.val);
        }
        travel(res, level + 1, cur.left);
        travel(res, level + 1, cur.right);
    }
}
/**
 * 这个是dfs. 通过level的值来判断这一层是从左向右还是从右向左. DFS的特征就是每一个level最左边的
 * node会被先visit, 那么如果到了一个level, 此时这个level是第一次到达, 那么我们需要给该level先
 * new一个list, 也就是第61行干的事情. 然后看当前的level是zig还是zag. 如果是zig也就是从左向右,
 * 后面遇到的node应该添加到该level对应list的末尾, 因为后遇到的是在靠右的位置. 如果是zag, 也就是
 * 从右向左, 那么后遇到的node应该添加到该level对应的list的前面, 因为先遇到的node更靠左, 而我们想要的
 * 是靠右的在前面. 这样就完成了traverse.
 * 
 * 时间复杂度: O(N)是traverse所有node, 还有奇数level要在头部插入value, 此时要移动在list中的元素.
 * 因此会需要时间, 但是我不知道怎么表示这个.
 * 
 * 空间复杂度: O(N) 可能是skewed tree因此栈帧有N个, 每一个level会有一个list, 因此也会有N个list.
 */