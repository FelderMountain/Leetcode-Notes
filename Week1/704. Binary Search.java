class Solution {
    public int search(int[] nums, int target) {
        return helper(0, nums.length - 1, nums, target);
    }

    public int helper(int start, int end, int[] nums, int target) {
        if (start > end)
            return -1;
        int middleIdx = (start + end) / 2;
        if (target > nums[middleIdx]) {
            return helper(middleIdx + 1, end, nums, target);
        } else if (target < nums[middleIdx]) {
            return helper(start, middleIdx - 1, nums, target);
        } else {
            return middleIdx;
        }
    }
}

/**
 * 这个没什么说的, 就是二分查找.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(logn)
 */

class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (target < nums[middle])
                right = middle - 1;
            else if (target > nums[middle])
                left = middle + 1;
            else
                return middle;
        }
        return -1;
    }
}

/**
 * 当然也可以不用递归, 使用循环去做. 这个方法空间复杂度为O(1). 古德!
 */