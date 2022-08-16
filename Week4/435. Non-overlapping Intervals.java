class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int count = 0;
        int start = intervals[0][0], end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int currStart = intervals[i][0];
            int currEnd = intervals[i][1];
            if (currStart < end) {
                count += 1;
                if (currEnd < end) {
                    end = currEnd;
                    start = currStart;
                }
            } else {
                start = currStart;
                end = currEnd;
            }
        }
        return count;
    }
}

/**
 * 先把intervals给sort一下. 然后从左向右遍历. 我们先看第0个和第1个interval, 我们知道interval 1的start肯定
 * 大于等于interval 0的start. 那么如果interval 1的start在interval 0的end之前, 二者就有交集. 此时我们会想
 * 这种情况下必须移走一个, 那移走哪一个呢? 移走那个覆盖范围广的, 也就是有更大可能也和别的intervalss有交集的那个. 由于
 * interval 0左边没东西, 于是我们就会考虑interval 0 和interval 1哪个会向右覆盖更多的其他intervals,
 * 此时自然想到比较二者的end, 谁的end更大, 就说明哪个interval会可能覆盖更多的其他的intervals. 如果移走了interval 1,
 * 那么我们继续往后看第二个interval, 进行相同的操作, 看interval 2和interval 0是否有交集,
 * 如果有再根据哪个interval的覆盖范围广就移动走哪个; 如果一开始移动走了interval 0, 那么剩下的就是interval 1.
 * 此时我们接着往后走看interval 2和interval 1是否有交集, 如果有交集再看二者哪个往右覆盖的多, 然后去移走哪个.
 * 因此我们需要一个变量来记录接下来的interval要和哪个interval比较是否有交集. 这也就是第5行两个变量的作用. 这两个
 * 变量记录了后面一个interval要比较的interval的start和end.
 * 
 * 当某个interval和第5行记录的interval没有交集, 我们就要更新第5行的interval为当前的这个interval了, 因为第5行当时
 * 记录的interval安全了, 之后的interval不可能和它再有交集. 如果某个interval和第5行记录的interval有交集并且我们要移走
 * 第5行的interval, 那么第5行记录的interval也应该改成当前的interval, 因为移走了自然要更新成当前的interval.
 * 如果不用移走第5行记录的interval, 那自然也不用更新第五行记录的interval.
 * 
 * 时间复杂度: O(nlogn) 排序
 * 空间复杂度: O(nlogn) 排序用栈.
 */

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int count = 0;
        // int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int currStart = intervals[i][0];
            int currEnd = intervals[i][1];
            if (currStart < end) {
                count += 1;
                if (currEnd < end) {
                    // start = currStart;
                    end = currEnd;
                }
            } else {
                // start = currStart;
                end = currEnd;
            }
        }
        return count;
    }
}
/**
 * 我们发现上面我们第五行无需记录start, 只需要记录end即可因为我们在循环中没有用到start.
 */