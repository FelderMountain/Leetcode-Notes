public class Solution {
    public int[] searchRange(int[] a, int target) {

        int[] result = { -1, -1 };

        if (a == null || a.length == 0)
            return result;

        result[0] = findStartPosition(a, target);
        result[1] = findEndPosition(a, target);

        return result;
    }

    public int findStartPosition(int[] a, int target) {

        int left = 0;
        int right = a.length - 1;
        int start = -1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (a[mid] == target) {
                start = mid; // this is start
                right = mid - 1; // lets see if there one more on the left
            } else if (target > a[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return start;
    }

    public int findEndPosition(int[] a, int target) {
        int left = 0;
        int right = a.length - 1;
        int end = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (a[mid] == target) {
                end = mid; // this is the end
                left = mid + 1; // lets see if there is one more on the right
            } else if (target > a[mid])
                left = mid + 1;
            else
                right = mid - 1;

        }

        return end;
    }
}

/**
 * 上面这个答案完美. 正是我想要的. 这道题也是binary search. 只是要找left界限和right界限.
 * 先找左界限. 循环条件依然是left <= right. 如果出现nums[middle] == target, 那么我们记录
 * 此时middle的值到start里面, 然后让right = middle - 1, 看看是否还有index更加靠左并和target相等
 * 的值, 如果左边还有, 那么start会继续被更新.
 * 
 * 同样的道理, 我们找右边界, 如果出现nums[middle] == target, 那么我们记录下来此时的middle到end中. 然后让
 * left = middle + 1, 去看右边是否还有index更大的和target相等的值.
 * 
 * 这个每次遇到nums[middle] == target就记录的方法很好. 很容易让人理解.
 * 
 * 时间复杂度: O(logn) 两次binary search
 * 空间复杂度: O(1)
 */
