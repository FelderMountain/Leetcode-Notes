class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int currSum = nums[i] + nums[left] + nums[right];
                if (Math.abs(currSum - target) < Math.abs(ans - target)) {
                    ans = currSum;
                }
                if (currSum < target) {
                    left += 1;
                } else if (currSum > target) {
                    right -= 1;
                } else {
                    return currSum;
                }
            }
        }
        return ans;
    }
}
/**
 * 是3Sum的变种.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(nlogn) sort需要用栈.
 */