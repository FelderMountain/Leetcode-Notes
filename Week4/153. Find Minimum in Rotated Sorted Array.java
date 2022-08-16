class Solution {
    public int findMin(int[] nums) {
        if (nums[nums.length - 1] >= nums[0])
            return nums[0];
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[0]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }
}
/**
 * 就是和那个找某个数字在sorted array中是否存在的那道题一样. 模板是一样的.
 * 就是left是小于right而不是小于等于, 遇到nums[mid] < nums[0]这个数字可能是
 * 最小值但不一定, 我们此时要接着往左找, 于是让right = mid, 意思就是让mid的位置
 * 依旧在我们找的范围之中. 如果遇到nums[mid] > nums[0], 这说明mid这个数字一定
 * 不是答案, 我们排除他然后往右找. 很简单.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 */