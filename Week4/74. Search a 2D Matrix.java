class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        while (row < matrix.length && target > matrix[row][matrix[0].length - 1]) {
            row += 1;
        }
        if (row == matrix.length)
            return false;
        int left = 0, right = matrix[row].length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}

/**
 * 找到target可能出现在哪一行, 然后binary search.
 * 
 * 时间复杂度: O(m + logn)
 * 空间复杂度: O(1)
 */

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int start = 0, rows = matrix.length, cols = matrix[0].length;
        int end = rows * cols - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (matrix[mid / cols][mid % cols] == target) {
                return true;
            }
            if (matrix[mid / cols][mid % cols] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return false;
    }
}
/**
 * 把这个2D matrix当作是一维的, 直接binary search, 把mid转化成row和col的方式很妙.
 * 
 * 时间复杂度: O(log(m * n))
 * 空间复杂度: O(1)
 */
/**
 * 我的那个方法不用一行行看而是也用一个binary search, 找到那个比target大的每行结尾的数字中最小的那个.
 * 那么这一行就是target可能出现的位置.
 */