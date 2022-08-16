class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int start = newInterval[0];
        int end = newInterval[1];
        int idx = 0;
        while (idx < intervals.length && intervals[idx][1] < start) {
            result.add(intervals[idx++]);
        }

        while (idx < intervals.length && intervals[idx][0] <= end) {
            start = Math.min(start, intervals[idx][0]);
            end = Math.max(end, intervals[idx][1]);
            idx += 1;
        }
        result.add(new int[] { start, end });

        while (idx < intervals.length && intervals[idx][0] > end) {
            result.add(intervals[idx++]);
        }
        return listToArray(result);
    }

    private int[][] listToArray(List<int[]> list) {
        int[][] array = new int[list.size()][2];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}

/**
 * 这上面是clean and concise的解法. 首先是找和自己没有交集的intervals. 分两部分: 在自己前面的和在自己后面的. 其次找到那些
 * 和自己有交集的也就是和自己的那些intervals不管是他们的start或者是end落在咱们的interval之间的. 也就是和咱们有交集的.
 * 此时咱们挨个遍历和咱们有交集的interval. 其中第一个和最后一个和咱们有交集的最重要. 因为他俩决定了咱们和这一堆intervals merge后的
 * 总interval的上界和下界. 大概思路就是这样, 比较直接.
 * 
 * 一个interval和另一个interval有交集无非四种情况. A的end在B之中, A完全被包裹在B中, A的start在B中, B被完全包裹在A中.
 * 再简化就分为包含和不完全包含的关系. 因此和newInterval有交集的intervals都可以和newInterval进行merge. 若只有一个,
 * 那么好说也就是看谁的start更小, 谁的end更大. 若有多个, 合并后的start由最靠左的那个interval决定. 要么是它的start要么就是
 * newInterval的start当头. end则是最后一个interval决定, 要么它的end要么是newInterval的end作为结尾.
 * 因为这些intervals都是按照从小到大顺序排好的而且互相没有交集.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int newLeftBound = newInterval[0];
        int newRightBound = newInterval[1];
        int idx = 0;
        List<int[]> result = new ArrayList<>();
        while (idx < intervals.length) {
            int currentIntervalLeftBound = intervals[idx][0];
            int currentIntervalRightBound = intervals[idx][1];
            if ((newLeftBound <= currentIntervalRightBound && newLeftBound >= currentIntervalLeftBound)
                    || (newRightBound >= intervals[idx][0] && newRightBound <= intervals[idx][1])) {
                intervals[idx][0] = Math.min(currentIntervalLeftBound, newLeftBound);
                intervals[idx][1] = Math.max(currentIntervalRightBound, newRightBound);
                break;
            }
            idx += 1;
        }
        if (idx == intervals.length) {
            for (int[] interval : intervals) {
                result.add(interval.clone());
            }
            result.add(newInterval.clone());
            return arrayListToArray(result);
        }

        for (int i = 0; i <= idx; i++) {
            result.add(intervals[i].clone());
        }
        idx += 1;
        while (idx < intervals.length) {
            int currentIntervalLeftBound = intervals[idx][0];
            int currentIntervalRightBound = intervals[idx][1];
            if (currentIntervalLeftBound <= result.get(result.size() - 1)[1]) {
                result.get(result.size() - 1)[1] = Math.max(currentIntervalRightBound,
                        result.get(result.size() - 1)[1]);
            } else {
                result.add(intervals[idx].clone());
            }
            idx += 1;
        }
        return arrayListToArray(result);
    }

    public int[][] arrayListToArray(List<int[]> list) {
        int[][] array = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}

/**
 * 上面是反面教材. 我的思路是找到这个newInterval最先和哪个interval有交集, 然后和它合并, 合并完后从那里一直往后merge.
 * 然而大问题就是newInterval不一定和某个interval是有交集的. 其次就是newInterval和interval的交集方式还有三种情况(体现
 * 在了第10 ,11行那么纠结的代码中). 我还认为只要和当前的interval没有交集就说明只可能和后面的有所交集, 如果没有一个有交集
 * 就说明这个newInterval必须被插到最后面. 这样的想法是因为我想当然了, 之前做了那个merge interval的题, 被那个思路影响了.
 * 
 */

[[1,0,1,1,0,0,1,0,0,1],
 [0,1,1,0,1,0,1,0,1,1],
 [0,0,1,0,1,0,0,1,0,0],
 [1,0,1,0,1,1,1,1,1,1],
 [0,1,0,1,1,0,0,0,0,1],
 [0,0,1,0,1,1,1,0,1,0],
 [0,1,0,1,0,1,0,0,1,1],
 [1,0,0,0,1,1,1,1,0,1],
 [1,1,1,1,1,1,1,0,1,0],
 [1,1,1,1,0,1,0,0,1,1]]

 [[1,0,1,1,0,0,1,0,0,1],
 [0,1,1,0,-1,0,1,0,1,1],
 [-1,0,1,0,-1,-1,0,-1,0,0],
 [-1,0,1,0,-1,-1,-1,-1,-1,-1],
 [-1,-1,0,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]]

 [[1,0,1,1,0,0,1,0,0,1],
 [0,1,1,0,-1,0,1,0,1,1],
 [-1,0,1,0,-1,-1,0,-1,0,0],
 [-1,0,1,0,-1,-1,-1,-1,-1,-1],
 [-1,-1,0,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
 [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]]

 result: [[1,0,1,1,0,0,1,0,0,1],[0,1,1,0,-2,0,1,0,1,1],[-1,0,1,0,-2,-1,0,-1,0,0],[-1,0,1,0,-2,-1,-1,-1,-1,-1],[-1,-1,0,-1,-2,0,-1,-1,-1,-1],[-1,-1,-1,0,-2,-2,-1,-1,-1,-1],[0,-1,-1,-1,0,-2,-1,-1,-1,-1],[1,0,0,0,1,-2,-1,-1,-1,-1],[2,-2,-2,-2,-2,-2,-1,-1,-1,-1],[3,-2,-2,-2,0,1,0,-1,-1,-1]]
