class Solution {
    public void rotate(int[] nums, int k) {
        int realK = k % nums.length;
        if (realK == 0)
            return;
        int[] temp = new int[realK];
        for (int i = nums.length - 1, j = temp.length - 1; j >= 0; i--, j--) {
            temp[j] = nums[i];
        }
        int left = nums.length - realK - 1, right = nums.length - 1;
        while (left >= 0) {
            nums[right] = nums[left];
            right -= 1;
            left -= 1;
        }
        while (right >= 0) {
            nums[right] = temp[right];
            right -= 1;
        }
    }
}

/**
 * 第一种方法, 也是最直接的. 把后k个数字存起来. 然后把前nums.length - k个数字
 * 统一后移, 然后再把后k个数字放到前面.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */

class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        if (k == 0)
            return;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start += 1;
            end -= 1;
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
/**
 * 这个方法比较巧妙, 我是怎么也肯定想不出来. 就是先整体reverse. 然后前k个reverse, 后面的再reverse一下
 * 就行了. 把一个array想象成两部分, 前nums.length - k个, 后k个. 两个部分看成是俩纸板. 整体反转一下之后
 * 右边的纸板跑到了左边, 左边跑到了右边. 但是跑过去的都是正面朝下, 于是两个都再反转一下就可以了.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */