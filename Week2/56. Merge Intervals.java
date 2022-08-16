class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> mergedIntervals = new ArrayList<>();
        mergedIntervals.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int currentIntervalEnd = intervals[i][1];
            int currentIntervalStart = intervals[i][0];
            int[] lastMergedInterval = mergedIntervals.get(mergedIntervals.size() - 1);
            if (currentIntervalStart <= lastMergedInterval[1]) {
                lastMergedInterval[1] = Math.max(lastMergedInterval[1], currentIntervalEnd);
            } else {
                mergedIntervals.add(intervals[i]);
            }
        }
        return mergedIntervals.toArray(new int[mergedIntervals.size()][]);
    }
}
/**
 * 也是很经典的题.
 * 
 * 需要注意的是Collection可以直接转array.
 * 
 * 时间复杂度: O(nlogn) sort需要花时间. 然后再遍历一遍intervals.
 * 空间复杂度: O(N) 如果是个skewed tree的话, 否则就是logn.
 */