/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    private int pos;

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder ans = new StringBuilder();
        serial(root, ans);
        return ans.toString();
    }

    private void serial(TreeNode node, StringBuilder str) {
        if (node == null) {
            str.append("#");
            str.append(",");
            return;
        }
        str.append(String.valueOf(node.val));
        str.append(",");
        serial(node.left, str);
        serial(node.right, str);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodes = data.split(",");
        return deserial(nodes);
    }

    private TreeNode deserial(String[] nodes) {
        if (nodes[pos].equals("#")) {
            pos += 1;
            return null;
        }
        TreeNode currNode = new TreeNode(Integer.parseInt(nodes[pos++]));
        currNode.left = deserial(nodes);
        currNode.right = deserial(nodes);
        return currNode;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));

/**
 * preorder traversal. 我们用,来隔开不同的node, 用#表示null. 想明白这道题, 也就明白construct binary
 * tree from preorder and inorder traversal那道题了.
 * 这里我们还原一个node, 就让pos加1, 这样方便后面的递归函数, 它们可以直接就调用, 不需要再更新pos.
 * 
 * 注意39行那里不能直接在中括号里面++, 因为如果这行if条件没有成立, pos也会++, 这不是我们想要的.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */