class Solution {
    public int majorityElement(int[] nums) {
        int major = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                major = nums[i];
                count += 1;
            } else if (nums[i] == major) {
                count += 1;
            } else {
                count -= 1;
            }
        }
        return major;
    }
}
/**
 * 我的最初想法就是sort一下或者直接HashMap去解决. 这个方法确实巧妙.
 * 抵消的概念. 两个不同的数可以互相抵消, 最后剩下的那个数一定是出现次数
 * 超过一半的那个.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */