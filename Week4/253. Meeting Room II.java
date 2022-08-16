class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0)
            return 0;
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<Integer> allocator = new PriorityQueue<>(intervals.length, (a, b) -> Integer.compare(a, b));
        allocator.add(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= allocator.peek()) {
                allocator.poll();
            }
            allocator.offer(intervals[i][1]);
        }
        return allocator.size();
    }
}

/**
 * 这是最直接的思路, 按照开始时间给intervals进行排序. PriorityQueue中存的就是rooms的个数.
 * 当一个新meeting开始的时候, 看有的房间中最先结束的会议是否结束,如果结束了就把它pop出来, 在add进这个会议.
 * 也就是更新房间的信息, 如果没有, 那么直接add一个新的房间. 为了能够快速获得哪个房间的会议最先结束, 我们使用了
 * PriorityQueue也就是min heap.
 * 
 * 时间复杂度: O(nlogn) 排序需要nlogn, heap的sift up需要logn.
 * 空间复杂度: O(n) 可能所有的会议都重复. 我们把所有的intervals都推到queue中.
 */

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0)
            return 0;
        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start);
        Arrays.sort(end);

        int count = 0, endPtr = 0;
        for (int i = 0; i < start.length; i++) {
            if (start[i] < end[endPtr]) {
                count += 1;
            } else {
                endPtr += 1;
            }
        }
        return count;
    }
}
/**
 * 思考的时候, 发现room的个数就是在某个时刻能达到最多meeting都有重叠的个数. 那么这个特点用数学公式如何表示?
 * 开始我是只把intervals按照开始时间sort. 第0个interval肯定会使用一个room, 然后看第1个, 如果它不和第0个interval
 * 相交, 那么我们不需要添加room, 第0个interval结束, 我们重复使用这个room即可, 往后看interval 2看它和interval
 * 1是否重合. 这又回到了类似判断interval 1和interval 0是否重合的情景; 如果interval 1和interval 0重合,
 * 那么room就要 + 1, interval 2此时要看是否和interval 0和interval 1是否都重合,
 * 也就是看是否小于Math.min(interval[0][1], intervals[1][1]). 如果是的话就要room + 1.
 * 如果只是和一个interval有交集, 那么room不需要 + 1. 如果没交集, 那么也不需要添加room. 那么如何判断到底和几个intervals
 * 有交集呢? 这又是个问题, 如果比前两个intervals中最小的end小, 那就是和两个都相交, 如果值小于大的那个end, 那么只和一个相交.
 * 如果都不小, 那么就没有交集. 这样的话我们还要记录当前有几个intervals在相交, 以及它们的end的大小?(用栈记录?) 至此, 我就死机了.
 * 不知道该如何进展.
 * 
 * 因此需要换种思路. 我们再把intervals按照结束时间从早到晚排序. 最早结束的meeting在它结束前, 我们看有多少个meeting开始了.
 * 此时有多少个meetings开始, 就需要多少个rooms. 当我们遇到一个meeting它的开始时间比最早结束的meeting晚, 那么这个最早结束的
 * meeting可以腾出位置, 让这个新的meeting去开会. 之后的meeting再去看是否在第二早结束的meeting结束前开始, 如果有,
 * 那么room的数目就要增加. 如果新的meeting比第二早结束的meeting结束后开始, 那么这个第二早的meeting就可以离开腾出位置给这个
 * 新的meeting. 因此这个pattern就明确了.
 * 
 * 精髓在于每个meeting开完不走, 等着后续的meeting来敲门, 如果结束了, 那么就腾位置, 否则就继续开. 我们没必要记录此时有多少房间
 * 空, 多少房间在开会, 只需要让meeting开完不走, 后续的meeting来敲门才走.
 * 
 * 这个算法就是看最多有多少个intervals会互相重合.
 * 
 * 时间复杂度: O(nlogn) 排序
 * 空间复杂度: O(nlogn) 排序
 * 
 */