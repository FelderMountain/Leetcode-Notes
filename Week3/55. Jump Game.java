class Solution {
    public boolean canJump(int[] nums) {
        int maxReach = 0;
        for (int i = 0; i < nums.length && i <= maxReach; i++) {
            maxReach = Math.max(i + nums[i], maxReach);
            if (maxReach >= nums.length - 1)
                return true;
        }
        return maxReach >= nums.length - 1;
    }
}
/**
 * 也是很简单的思路, 一个变量记录当前能到达最远的点是哪里, 遍历nums中的每个元素来不断更新这个变量,
 * 直到走不动或者可以到达最后一个index停止.
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */