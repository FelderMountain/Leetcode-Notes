class Solution {
    public int missingNumber(int[] nums) {
        int ans = nums.length;
        for (int i = 0; i < nums.length; i++) {
            ans ^= i;
            ans ^= nums[i];
        }
        return ans;
    }
}
/**
 * XOR解法, 从0到n一共n + 1个数字要放在长度为n的数组中. 我们先从0到n都XOR一遍.
 * 然后再和数组中的每个数字XOR一遍, 此时出现两个的数字就会被抵消, 剩下的那个就是
 * 没有出现在数组中的数字, 也就是missing number.
 * 
 * 还有个方法就是计算0到n个和, 然后减去数组中所有的数字, 剩下的值就是那个没有被包含
 * 进去的missing num.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */