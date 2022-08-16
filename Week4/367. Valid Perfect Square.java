class Solution {
    public boolean isPerfectSquare(int num) {
        // limit is [1, 46340]
        int left = 1, right = 46340;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midSquare = mid * mid;
            if (midSquare < num) {
                left = mid + 1;
            } else if (midSquare > num) {
                right = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}

/**
 * Binary Search. 2147483647的square root小于46341. 因此我们知道num的square root的范围就在
 * [1, 46340]. 我们在这个范围binary search即可.
 * 
 * 时间复杂度: O(log46340)
 * 空间复杂度: O(1)
 */

class Solution {
    public boolean isPerfectSquare(int num) {
        int left = 1, right = num;
        while (left <= right) {
            int mid = left + (right - left) / 2; // to avoid overflow incase (left+right)>2147483647
            int res = num / mid, remain = num % mid;
            if (res == mid && remain == 0)
                return true; // check if mid * mid == num
            if (res > mid) { // mid is small -> go right to increase mid
                left = mid + 1;
            } else {
                right = mid - 1; // mid is large -> to left to decrease mid
            }
        }
        return false;
    }
}
/**
 * 这个是如果不知道2147483647的root square是多少的时候. 我们使用一个巧妙的方法, 让num / mid, 看商和余数是多少来判断.
 * 如果商是mid且余数是0, 这说明mid就是num的square root. 如果商大于mid, 那说明num大于mid的平方. 于是我们往右找.
 * 如果res < mid, 那么num < mid的平方, 我们想左找. 如果res == mid但是remain不为0, 那么说明num == mid ^
 * 2 + remain. 此时num是大于mid的平方, 我们向右找. 但实际上如果遇到res == mid, 也就是mid * mid + remain
 * == num,这说明0 < remain < mid, 那么如果我们往右找, 即使往右走一步, 也就是mid + 1. (mid + 1) ^ 2 =
 * mid ^ 2 + 2 * mid + 1.这个数字一定大于num. 于是遇到res ==
 * mid且remain不为0的时候就代表num不是一个square了.
 * 
 * 时间复杂度: O(logn) n == num
 * 空间复杂度: O(1)
 */