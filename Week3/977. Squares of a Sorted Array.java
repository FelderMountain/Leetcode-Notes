class Solution {
    public int[] sortedSquares(int[] nums) {
        int left = 0, right = nums.length - 1;
        int[] ans = new int[nums.length];
        for (int pos = ans.length - 1; pos >= 0; pos--) {
            ans[pos] = Math.abs(nums[left]) >= Math.abs(nums[right]) ? nums[left] * nums[left++]
                    : nums[right]
                            * nums[right--];
        }
        return ans;
    }
}
/**
 * 由于是sorted, 那么根据二次函数的特性. 两端大中间小. 我们使用双指针即可.
 * 这里的++和--必须在后一个地方写, 因为如果写在前面会在nums[left]取值后
 * 就把left更新了, right也是类似.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n) 因为要装答案.
 */