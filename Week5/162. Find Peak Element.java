class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid + 1 < nums.length && nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else if (mid > 0 && nums[mid] < nums[mid - 1]) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return ans;
    }
}
/**
 * 这个需要证明.
 * 如果nums[mid]比nums[mid + 1]小, 那么mid右侧肯定有peak. 因为mid + 1可能就是, 如果不是,
 * 那说明mid + 2更大, 那么mid + 2可能是, 如果不是mid + 3比mid + 2大, mid + 3可能是, 如果mid + 3不是...
 * 一直到nums[nums.length - 1]. 如果一直递增到这个位置, 那么nums[nums.length -
 * 1]一定是因为规定nums[nums.length]是负无穷.
 * 如果nums[mid]比nums[mid - 1]小, 那么mid左侧一定有peak, 一样的证明方式.
 * 如果以上两个条件都不满足, 那就说明mid比mid + 1和mid - 1都大(假设nums[i] != nums[i +
 * 1]对于所有valid的i都成立), 此时mid就是peak.
 * 
 * 唯一需要考虑的是mid + 1和mid - 1不能出界. 如果mid + 1出界, 这说明此时mid来到了nums[nums.length - 1]处,
 * 被逼到这里了, 那么这里就是peak. 同样地如果mid - 1出界, 说明mid来到了nums[0], 也被逼到了这里, 那说明这里就是
 * peak.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(1)
 */