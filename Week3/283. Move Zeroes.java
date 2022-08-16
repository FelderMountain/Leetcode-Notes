class Solution {
    public void moveZeroes(int[] nums) {
        int pos = 0, count = 0;
        while (pos < nums.length - count) {
            if (nums[pos] == 0) {
                for (int i = pos; i < nums.length - 1 - count; i++) {
                    swap(nums, i, i + 1);
                }
                count += 1;
            } else {
                pos += 1;
            }
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

/**
 * 类似于bubble sort, 把0都放到最后. 不是很好.
 * 
 * 时间复杂度: O(n^2)
 * 空间复杂度: O(1)
 */

class Solution {
    public void moveZeroes(int[] nums) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow += 1;
            }
            fast += 1;
        }
        while (slow < nums.length) {
            nums[slow] = 0;
            slow += 1;
        }
    }
}
/**
 * 这个就好多了. slow是去指向现在要填的位置, slow指向的是要填的非0的数字.
 * fast去找非0的数字, 找到一个后, 把这个数字填到slow指向的位置. 然后slow
 * 向右移动一位, fast同样移动一位, 直到fast遍历完. 最后, 如果slow小于
 * nums.length, 那我们把剩下的位置全部填为0即可.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */