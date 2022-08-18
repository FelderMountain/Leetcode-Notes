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

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] leftMax = new int[nums.length];
        int currLeftMax = nums[0];
        leftMax[0] = currLeftMax;
        for (int i = 1; i < nums.length; i++) {
            currLeftMax = i % k == 0 ? nums[i] : Math.max(currLeftMax, nums[i]);
            leftMax[i] = currLeftMax;
        }

        int[] rightMax = new int[nums.length];
        int currRightMax = nums[nums.length - 1];
        rightMax[rightMax.length - 1] = currRightMax;
        for (int i = nums.length - 2; i >= 0; i--) {
            currRightMax = (i + 1) % k == 0 ? nums[i] : Math.max(currRightMax, nums[i]);
            rightMax[i] = currRightMax;
        }
        int[] ans = new int[nums.length - k + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = Math.max(rightMax[i], leftMax[k + i - 1]);
        }
        return ans;
    }
}
/**
 * 这个做法我是真想不出来. 不知道想出这个答案的思路是什么.
 * 
 * 原理很容易理解. 把nums划分成block, 每个block的长度为k, 除了最后一个block的长度可能小于k, 这是没问题的.
 * leftMax装的东西这样理解. 在某个位置i, 它一定属于某个block, 那么leftMax[i]表示的是从它所在的block的开头
 * 到这里最大的值.
 * rightMax装的东西则是, 在某个位置j, 它一定属于某个block, 那么从该rightMax[i]表示的是从它所在的block的结尾
 * 到这里的最大值.
 * 于是我们的sliding window在某个位置时, 要么window刚好fit进一个block,
 * 此时最大值就是rightMax[blockStart]或者
 * leftMax[blockEnd]; 要么window横跨两个block, 此时最大值就是Math.max(rightMax[blockStart],
 * leftMax[blockEnd]).
 * 这个方法是真的巧. 我不懂的是为何想到要划分block这件事, 以及block的size为何确定成k, 既不大于k, 也不小于k.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */