/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    public Node connect(Node root) {
        if (root == null)
            return root;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            Node lastNode = q.peek();
            for (int i = 0; i < size; i++) {
                Node currNode = q.poll();
                currNode.next = q.peek();
                if (currNode.left != null) {
                    q.offer(currNode.left);
                }
                if (currNode.right != null) {
                    q.offer(currNode.right);
                }
                lastNode = currNode;
            }
            lastNode.next = null;
        }
        return root;
    }
}

/**
 * 用BFS去解. 很直接, 直接上.
 * 
 * 时间复杂度: O(N)
 * 空间复杂度: O(N) 因为用了queue.
 */

/*
 * // Definition for a Node.
 * class Node {
 * public int val;
 * public Node left;
 * public Node right;
 * public Node next;
 * 
 * public Node() {}
 * 
 * public Node(int _val) {
 * val = _val;
 * }
 * 
 * public Node(int _val, Node _left, Node _right, Node _next) {
 * val = _val;
 * left = _left;
 * right = _right;
 * next = _next;
 * }
 * };
 */

class Solution {
    public Node connect(Node root) {
        if (root == null)
            return root;
        helper(root.left, root);
        helper(root.right, root);
        return root;
    }

    private void helper(Node node, Node parent) {
        if (node == null)
            return;
        if (parent.left == node) {
            node.next = parent.right;
        } else {
            if (parent.next != null) {
                node.next = parent.next.left;
            }
        }
        helper(node.left, node);
        helper(node.right, node);
    }
}

/**
 * 这个方法充分利用了next pointer, 如果某个node是parent的左node, 那么它的next就指向parent.right.
 * 如果是parent的右node, 这就分两种情况. parent不是它那一层最右侧的, 此时该node的next就指向parent.next.left.
 * 如果parent是那一层最右侧的, 那么node.next就是null. 因为node.next本身默认就是null. 于是我们什么就不操作即可.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1) 题目说用栈不算入空间复杂度之中.
 */

class Solution {
    public Node connect(Node root) {
        helper(root, null);
        return root;
    }

    private void helper(Node node, Node next) {
        if (node == null)
            return;
        node.next = next;
        helper(node.left, node.right);
        if (node.next == null) {
            helper(node.right, null);
        } else {
            helper(node.right, node.next.left);
        }
    }
}
/**
 * 另一种写法, 就是我们告诉某个node它的右边是谁. 因为在parent的位置, 我们能清楚的看见child的
 * 右边是谁.
 * 
 * 时间复杂度和空间复杂度不变.
 */