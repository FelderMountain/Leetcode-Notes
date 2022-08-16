class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = 1;
        for (int i = 1; i < result.length; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        int rightProduct = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            rightProduct = rightProduct * nums[i + 1];
            result[i] = rightProduct * result[i];
        }
        return result;
    }
}
/**
 * 这个题很经典. 思路是假设第n个元素要计算左右的product, 我们发现算n - 1的时候, 它左边的product已经算过一次.
 * 到了第n个我们还要重新从头乘一遍吗? 如果我们知道n - 1算好的它左边元素的乘积, 让这个值乘以第n - 1个元素,
 * 不就是第n个元素左边元素的product的积. 右边的类似. 如果知道第n + 1个元素右边所有元素的product, 那么这个值再乘以n第 +
 * 1个元素不就是第n个元素右边所有元素的乘积.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1) 题目规定, result这个数组不计入空间复杂度的计算里面.
 */