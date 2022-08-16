class Solution {
    public int countComponents(int n, int[][] edges) {
        Map<Integer, Set<Integer>> nodeEntry = new HashMap<>();
        for (int[] edge : edges) {
            nodeEntry.putIfAbsent(edge[0], new HashSet<>());
            nodeEntry.get(edge[0]).add(edge[1]);
            nodeEntry.putIfAbsent(edge[1], new HashSet<>());
            nodeEntry.get(edge[1]).add(edge[0]);
        }
        Set<Integer> visited = new HashSet<>();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                if (nodeEntry.containsKey(i)) {
                    dfs(i, nodeEntry, visited);
                }
                ans += 1;
            }
        }
        return ans;
    }

    private void dfs(int node, Map<Integer, Set<Integer>> nodeEntry, Set<Integer> visited) {
        visited.add(node);
        Set<Integer> neighbors = nodeEntry.get(node);
        for (Integer neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, nodeEntry, visited);
            }
        }
    }
}
/**
 * 最基础的DFS做法.
 * 
 * 时间复杂度: O(N) n个nodes.
 * 空间复杂度: O(N) 可能是skewed graph.
 */