class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> courseDependentMap = new HashMap<>();
        Map<Integer, Integer> preCount = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        int pos = 0;
        int[] result = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            preCount.put(i, 0);
        }
        for (int[] prerequisite : prerequisites) {
            courseDependentMap.putIfAbsent(prerequisite[1], new ArrayList<>());
            courseDependentMap.get(prerequisite[1]).add(prerequisite[0]);
            preCount.put(prerequisite[0], preCount.get(prerequisite[0]) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : preCount.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        while (!queue.isEmpty()) {
            int currCourse = queue.poll();
            result[pos] = currCourse;
            pos += 1;
            if (courseDependentMap.containsKey(currCourse)) {
                for (Integer dependent : courseDependentMap.get(currCourse)) {
                    preCount.put(dependent, preCount.get(dependent) - 1);
                    if (preCount.get(dependent) == 0) {
                        queue.offer(dependent);
                    }
                }
                courseDependentMap.remove(currCourse);
            }
        }
        return courseDependentMap.isEmpty() ? result : new int[0];
    }
}
/**
 * 就是topological sort.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */