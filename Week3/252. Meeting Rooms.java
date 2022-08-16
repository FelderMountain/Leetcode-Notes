class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1])
                return false;
        }
        return true;
    }
}
/**
 * Merge intervals的简单版本.
 * 
 * 时间复杂度: O(nlogn)
 * 空间复杂度: O(logn)
 * 排序消耗时间.
 */