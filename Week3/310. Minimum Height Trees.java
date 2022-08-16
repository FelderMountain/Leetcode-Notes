class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n <= 2) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                result.add(i);
            }
            return result;
        }
        // Record each node's neighbors.
        Map<Integer, Set<Integer>> nodeNeighbors = new HashMap<>();
        for (int[] edge : edges) {
            int nodeOne = edge[0], nodeTwo = edge[1];
            if (!nodeNeighbors.containsKey(nodeOne)) {
                nodeNeighbors.put(nodeOne, new HashSet<>());
            }
            nodeNeighbors.get(nodeOne).add(nodeTwo);

            if (!nodeNeighbors.containsKey(nodeTwo)) {
                nodeNeighbors.put(nodeTwo, new HashSet<>());
            }
            nodeNeighbors.get(nodeTwo).add(nodeOne);
        }

        // Find leaf nodes.
        Queue<Integer> nodeQueue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (nodeNeighbors.get(i).size() == 1)
                nodeQueue.offer(i);
        }

        int remainingNodes = n;
        while (remainingNodes > 2) {
            int length = nodeQueue.size();
            for (int i = 0; i < length; i++) {
                int currNode = nodeQueue.poll();
                int neighbor = nodeNeighbors.get(currNode).iterator().next();
                nodeNeighbors.get(neighbor).remove(currNode);
                if (nodeNeighbors.get(neighbor).size() == 1)
                    nodeQueue.add(neighbor);
                remainingNodes -= 1;
                nodeNeighbors.remove(currNode);
            }
        }
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> entry : nodeNeighbors.entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }
}
/**
 * 可以这么想, 从一个leaf node一定可以走到另一个leaf node, 因此有许多不同的路线. 每条路线长度也不同.
 * 如果是个path graph(类似链表那样的), 那么minumum height就是中间那个node作为root, 如果是偶数个则中间
 * 那两个中的任意一个. 我们要找的就是最长的这个链, 从一个leaf node到达另一个leaf node最长. 我们可以这么想,
 * 假设每一条路的两端都分别有个ptr(不同的路的起点或者终点会重合, 因此一个leaf node处可能有多个ptr). 我们开始让
 * 每条路的两个ptr开始往中间移动, 相遇的时候这两个ptr就会消失. 到最后剩余的那两个ptr就代表着我们的答案了. 可能是
 * 两个ptr最后挨着, 或者刚好重合. 那如何去编程这样的想象呢? 想象一个画面, 一个graph的所有leaf nodes上站了不少ptr,
 * 统一下令, 各个ptr开始往里移动一格, 那么之前所在的位置就没用了, 我们把这些nodes移除. 此时可能出现有些ptr经过这次
 * 移动后已经重合了, 那么它们就不会再被考虑, 如果它们的相遇点不是leaf node, 那么问题不大, 如果是个leaf node, 那么
 * 我们会在下面考虑到. 剩下的ptr没有相遇的ptr站在一些node处. 如果没有相遇的ptr所在的node不是leaf node, 那么它们肯定比那些
 * 两个都在此时的leaf nodes的ptr其中之一相遇更早.
 * 
 * 需要注意的是35行的这个内循环是不能少的, 也就是必须要一圈一圈的移nodes. 如果这个内循环没了, 会有这种情况:
 * 比如有这样一个graph, 1和2, 3, 4相连, 2, 3, 4除了和1相连外均互不相连. 于是我们按照逻辑把2, 3, 4推入queue.
 * 首先我们把2移走, 然后再移走3, 此时剩余的的nodes只有两个了, 按照逻辑该停止循环了, 然而4并不是答案, 我们在一圈
 * 还没有移完的时候就提前停止了, 因此这个内循环也就是按圈移走nodes是很重要的.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O
 */