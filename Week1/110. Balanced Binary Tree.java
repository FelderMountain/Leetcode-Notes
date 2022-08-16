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
    public boolean isBalanced(TreeNode root) {
        return helper(root) != -1 ? true : false;
    }

    public int helper(TreeNode node) {
        if (node == null)
            return 0;
        int leftSubTreeHeight = helper(node.left);
        if (leftSubTreeHeight < 0)
            return -1;
        int rightSubTreeHeight = helper(node.right);
        if (rightSubTreeHeight < 0)
            return -1;
        int diff = Math.abs(leftSubTreeHeight - rightSubTreeHeight);
        return diff > 1 ? -1 : Math.max(leftSubTreeHeight, rightSubTreeHeight) + 1;
    }
}

/**
 * 这道题也没什么好说的. 可以简单记录一下递归的思路:
 * 看到这道题, 首先想到就是写这样一个递归函数: 我给它一个node, 这个递归函数就能告诉我这个node所代表的tree以及这个tree中所有node所
 * 代表的tree是否是balanced的. 但是当我写递归函数逻辑的时候, 我发现给我一个node后, 我可以知道它的左支和右支是否是balanced.
 * 假如有一支不是, 那么返回false即可, 但是如果两支都是balanced, 如何判断该node自己所代表的tree是不是balanced的呢? 毕竟
 * 我不知道左支和右支的height. 于是递归函数的功能不能是这样, 它得不仅告诉我某个tree是不是balanced, 还要告诉我这个给定tree的高度.
 * 
 * 于是我重新规定递归函数的功能是, 如果给定的一个tree不是balanced, 那么返回-1, 如果是, 那么则返回这个树的高度.
 * 这样通过判断返回值的正负值就能判断树是否为balanced并且也能知道树的高度(如果树是balanced的话). 于是当我知道某个node的左右
 * 支都是balanced的之后, 我可以再将左右支的高度作差来判断该node自己是不是balanced, 如果是那么就返回左支或右支中最大的那个的高度 +
 * 1. 这个+ 1是因为这个被传入的这个node构成了一个单位高度. 如果自己不是balanced, 那么直接返回-1即可.
 * 至此, 这个递归函数逻辑就写出来了.
 * 只能说这个写递归函数的步骤无敌!!!
 * 
 * 时间复杂度: O(n) 因为每个node所代表的tree都要被判断是否为balanced. 因此每个node都会被遍历.
 * 空间复杂度: O(n) 可能是链表形式的tree.
 */