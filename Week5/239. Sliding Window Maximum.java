class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        LinkedList<Integer> queue = new LinkedList<>();
        int[] ans = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            if (!queue.isEmpty() && queue.peek() < i - k + 1) {
                queue.poll();
            }
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                queue.pollLast();
            }
            queue.offer(i);
            if (i - k + 1 >= 0) {
                ans[i - k + 1] = nums[queue.peek()];
            }
        }
        return ans;
    }
}
/**
 * 学习了一个重要的数据结构: Monotonic Deque. 给定一个数组, 我们使用Monotonic Dequeu, 我们可以知道
 * 任意位置x, 在x左侧比nums[x]小(increasing deque)或者大(decreasing deque)的数字的index.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(k)
 */