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
    private int pos;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        pos = postorder.length - 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return helper(map, 0, inorder.length - 1, postorder);
    }

    private TreeNode helper(Map<Integer, Integer> map, int iStart, int iEnd, int[] postorder) {
        if (iStart > iEnd)
            return null;
        TreeNode newNode = new TreeNode(postorder[pos]);
        int rootIdx = map.get(postorder[pos]);
        pos -= 1;
        newNode.right = helper(map, rootIdx + 1, iEnd, postorder);
        newNode.left = helper(map, iStart, rootIdx - 1, postorder);
        return newNode;
    }
}
/**
 * 和105题的解题模板一样. 只是pos要从后往前, 因为是postorder. 本质还是构造同样的场景: 给定一个inorder和postorder,
 * 根据它们去构造树, 只不过我们通过两个指针来界定在最初的inorder和postorder的哪个范围内去构建一个树, 而不是单独传入子array.
 * 进一步简化使得一个global variable记录在postorder中的位置. 具体的思考过程看105题的记录.
 * 构造相同的场景就自然而然的想到使用递归来解决问题.
 */