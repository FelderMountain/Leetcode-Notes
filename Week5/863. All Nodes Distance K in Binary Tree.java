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
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        dfs(root, null, parentMap);
        List<Integer> ans = new ArrayList<>();
        bfs(target, k, parentMap, ans);
        return ans;
    }

    private void dfs(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
        parentMap.put(node, parent);
        if (node.left != null) {
            dfs(node.left, node, parentMap);
        }
        if (node.right != null) {
            dfs(node.right, node, parentMap);
        }
    }

    private void bfs(TreeNode target, int k, Map<TreeNode, TreeNode> parentMap, List<Integer> ans) {
        if (k == 0) {
            ans.add(target.val);
            return;
        }
        int currDistance = 0;
        Queue<TreeNode> q = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        q.add(target);
        visited.add(target);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode currNode = q.poll();
                if (currNode.left != null && visited.add(currNode.left)) {
                    q.offer(currNode.left);
                }
                if (currNode.right != null && visited.add(currNode.right)) {
                    q.offer(currNode.right);
                }
                if (parentMap.get(currNode) != null && visited.add(parentMap.get(currNode))) {
                    q.offer(parentMap.get(currNode));
                }
            }
            currDistance += 1;
            if (currDistance == k) {
                break;
            }
        }
        if (currDistance == k) {
            while (!q.isEmpty()) {
                ans.add(q.poll().val);
            }
        }
    }
}

/**
 * 先dfs把所有node的parent记录好, 再bfs就行了.
 * 
 * 时间复杂度: O(N)
 * 空间复杂度: O(N)
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
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, Integer> nodeDistance = new HashMap<>();
        findNodesOnPathToTarget(root, target, nodeDistance);
        List<Integer> ans = new ArrayList<>();
        dfs(root, nodeDistance.get(root), k, nodeDistance, ans);
        return ans;
    }

    private int findNodesOnPathToTarget(TreeNode node, TreeNode target, Map<TreeNode, Integer> nodeDistance) {
        if (node == null) {
            return -1;
        }
        if (node == target) {
            nodeDistance.put(target, 0);
            return 0;
        }
        int left = findNodesOnPathToTarget(node.left, target, nodeDistance);
        if (left != -1) {
            nodeDistance.put(node, left + 1);
            return left + 1;
        }
        int right = findNodesOnPathToTarget(node.right, target, nodeDistance);
        if (right != -1) {
            nodeDistance.put(node, right + 1);
            return right + 1;
        }
        return -1;
    }

    private void dfs(TreeNode node, int distance, int k, Map<TreeNode, Integer> nodeDistance, List<Integer> ans) {
        if (node == null)
            return;
        if (nodeDistance.containsKey(node)) {
            distance = nodeDistance.get(node);
        }
        if (distance == k) {
            ans.add(node.val);
        }
        dfs(node.left, distance + 1, k, nodeDistance, ans);
        dfs(node.right, distance + 1, k, nodeDistance, ans);
    }
}
/**
 * 这个方法的精髓在于不往target方向走, 我们距离target的距离就会增加. 仔细体会这句话.
 * 
 * 于是我们需要知道从root到target的路线上都有哪些node以及它们到target的距离, 如果我们dfs的过程中走到了其中任意一个
 * node, 那么此时距离target的距离就不能是上一个node距离target的距离 + 1了, 因为我们是在往target的方向走,
 * 此时距离target的距离应该是上一个node距离target的距离 - 1. 由于我们把到target沿途的node距离target的距离
 * 都存到了map中, 因此应该从map中取.
 * 
 * 时间复杂度: O(n) dfs了两遍.
 * 空间复杂度: O(n) skewed tree.
 */