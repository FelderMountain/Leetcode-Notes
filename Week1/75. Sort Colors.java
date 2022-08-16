class Solution {
    public void sortColors(int[] nums) {
        int pos = 0, ptr = 0;
        while (ptr < nums.length) {
            if (nums[ptr] == 0) {
                swap(nums, pos, ptr);
                ptr += 1;
                pos += 1;
            } else {
                ptr += 1;
            }
        }

        pos = nums.length - 1;
        ptr = nums.length - 1;
        while (ptr >= 0) {
            if (nums[ptr] == 2) {
                swap(nums, pos, ptr);
                ptr -= 1;
                pos -= 1;
            } else {
                ptr -= 1;
            }
        }
        return;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/**
 * 我的开始的解法, 要遍历两遍.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */

class Solution {
    public void sortColors(int[] nums) {
        int left = 0, right = nums.length - 1, current = 0;
        while (current <= right) {
            if (nums[current] == 0) {
                swap(nums, current, left);
                left += 1;
                current += 1;
            } else if (nums[current] == 2) {
                swap(nums, current, right);
                right -= 1;
            } else {
                current += 1;
            }
        }
        return;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
/**
 * 这是one pass. 一个left, 一个right指针. 然后一个current. 如果current指向的数字等于0, 那么就和left交换.
 * 如果等于2, 就和right交换. 需要注意的是在和left交换完后后更新current, 但和right交换完后则不更新. 这是为什么?
 * 由于current是从左侧出发. 如果遇到的都是0, 和left交换完, left和current更新没毛病. 如果遇到了1呢? currrent接着
 * 往前走就行, left停下来, 因为这个left位置需要是0(如果后面有的交换的话). 当current遇到了2, left一样停下,
 * 此时current和right交换. 但是我们不知道right指的是什么, 于是交换完, current可能指向的是0, 1, 2.
 * 于是current还不能急着更新, 还要再看是什么. 如果此时是0, 那么和left交换, 然后往前走. 因为left指向的只能是1.
 * 如果left指向2, 那么当时current和left重合的时候就会把2换到right, 并且换完后如果current指向了0,
 * 还会和left交换然后二者都往前一步, 因此left不可能指向2. left如果是0也不可能, 因为此时left和
 * current会共同往前, 因此此时left只能指向1. 于是和left交换完后可以大胆放心地更新left和current.
 * 
 * current的位置要么是和left重合, 要么是领先. 如果current遇到了什么会告诉left, 不会让left指向0或者2的.
 * 
 * 这一点很关键.
 * 
 * 时间复杂度和空间复杂度和上面的一样.
 */