class Solution {
    private int count = 0;

    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1)
            return false;
        if (edges.length == 0)
            return true;
        Map<Integer, List<Integer>> nodeMap = new HashMap<>();
        boolean[] visited = new boolean[n];
        for (int[] edge : edges) {
            nodeMap.putIfAbsent(edge[0], new ArrayList<>());
            nodeMap.get(edge[0]).add(edge[1]);
            nodeMap.putIfAbsent(edge[1], new ArrayList<>());
            nodeMap.get(edge[1]).add(edge[0]);
        }
        return helper(nodeMap, visited, edges[0][0], edges[0][0]) && count == n;
    }

    private boolean helper(Map<Integer, List<Integer>> nodeMap, boolean[] visited, int currNode, int prevNode) {
        if (visited[currNode])
            return false;
        visited[currNode] = true;
        count += 1;
        for (Integer node : nodeMap.get(currNode)) {
            if (node != prevNode && !helper(nodeMap, visited, node, currNode))
                return false;
        }
        return true;
    }
}

/**
 * 比较直接, 就是DFS, 但注意的是不能走回头路. 于是有个变量prevNode要被传入.
 * 时间复杂度: O(N)
 * 空间复杂度: O(N)
 */

class Solution {
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1)
            return false;
        if (edges.length == 0)
            return true;
        Map<Integer, List<Integer>> nodeMap = new HashMap<>();
        boolean[] visited = new boolean[n];
        for (int[] edge : edges) {
            nodeMap.putIfAbsent(edge[0], new ArrayList<>());
            nodeMap.get(edge[0]).add(edge[1]);
            nodeMap.putIfAbsent(edge[1], new ArrayList<>());
            nodeMap.get(edge[1]).add(edge[0]);
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { edges[0][0], edges[0][0] });
        while (!queue.isEmpty()) {
            int[] currPrevNode = queue.poll();
            visited[currPrevNode[0]] = true;
            for (Integer node : nodeMap.get(currPrevNode[0])) {
                if (node == currPrevNode[1])
                    continue;
                if (!visited[node]) {
                    queue.offer(new int[] { node, currPrevNode[0] });
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
/**
 * 这是BFS的解法, 一样不走回头路.
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */