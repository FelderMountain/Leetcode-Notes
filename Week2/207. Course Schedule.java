class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            if (!map.containsKey(prerequisite[1])) {
                map.put(prerequisite[1], new ArrayList<>(Arrays.asList(0, prerequisite[0])));
            } else {
                map.get(prerequisite[1]).add(prerequisite[0]);
            }
            if (!map.containsKey(prerequisite[0])) {
                map.put(prerequisite[0], new ArrayList<>(Arrays.asList(1)));
            } else {
                List<Integer> info = map.get(prerequisite[0]);
                info.set(0, info.get(0) + 1);
            }
        }
        Queue<Integer> nodependencyCourses = new LinkedList<>();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> info = entry.getValue();
            if (info.get(0) == 0)
                nodependencyCourses.offer(entry.getKey());
        }

        while (!nodependencyCourses.isEmpty()) {
            int courseNum = nodependencyCourses.poll();
            List<Integer> list = map.get(courseNum);
            for (int i = 1; i < list.size(); i++) {
                List<Integer> targetList = map.get(list.get(i));
                targetList.set(0, targetList.get(0) - 1);
                if (targetList.get(0) == 0) {
                    nodependencyCourses.offer(list.get(i));
                }
            }
            map.remove(courseNum);
        }
        return map.isEmpty();
    }
}

/**
 * 这是我写的第一版答案. 也是知道topological sort后想了很久逻辑, 但是写完逻辑是正确没有问题的.
 * 比较重要的就是如何遍历一个map.
 */

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] adj = new List[numCourses];

        for (int i = 0; i < numCourses; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        int[] indegree = new int[numCourses];
        Queue<Integer> queue = new LinkedList();

        int finishCount = 0;
        for (int i = 0; i < prerequisites.length; i++) {
            int req = prerequisites[i][0];
            int prereq = prerequisites[i][1];
            adj[prereq].add(req);
            indegree[req]++;
        }
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int course = queue.poll();
            finishCount++;
            for (int nextCourse : adj[course]) {
                indegree[nextCourse]--;
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        return finishCount == numCourses;
    }
}
/**
 * 这个是用array来存信息. 建立两个array, 长度都是numCourses. 一个array存的都是List, 表示
 * 某个course指向了哪些其他的course(某个course是哪些course的先导课). 另一个array装的是每门课
 * 到目前还需要上多少先导课, 也就是还有多少其他课指向自己. 相比我用hashmap稍微好一些感觉. 因为
 * 我最后使用hashmap是否为空来判断, 但是这个答案没有hashmap因此用finishCount来计算目前上过的
 * 课的数目, 这样不需要最后再遍历一遍indegree来判断是否有非0的元素, 从而判断是否有的课还有先导课
 * 需要上但是queue已经空的情况出现.
 * 
 * Time Complexity: O(∣E∣+∣V∣) where |V| is the number
 * of courses, and |E| is the number of dependencies.
 * 
 * As in the previous algorithm, it would take us |E| time complexity to
 * build a graph in the first step.
 * Similar with the above postorder DFS traversal, we would visit each vertex
 * and each edge once and only once in the worst case, i.e. |E| + |V|∣E∣+∣V∣.
 * As a result, the overall time complexity of the algorithm would be
 * O(2|E| + |V|) = O(|E| + |V|).
 * 
 * Space Complexity: O(∣E∣+∣V∣), with the same denotation as in the above time
 * complexity.
 * 
 * We built a graph data structure in the algorithm, which would consume∣E∣+∣V∣
 * space.
 * In addition, we use a container to keep track of the courses that have no
 * prerequisite, and the size of the container would be bounded by |V|.
 * As a result, the overall space complexity of the algorithm would be
 * O(∣E∣+2⋅∣V∣)=O(∣E∣+∣V∣).
 */