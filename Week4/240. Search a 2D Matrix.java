class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (target == matrix[row][col]) {
                return true;
            } else if (target < matrix[row][col]) {
                col -= 1;
            } else {
                row += 1;
            }
        }
        return false;
    }
}
/**
 * 思路就是要到一个位置, 如果出现target < 该位置, 我们能往大的方向走, 如果小于该位置, 我们能
 * 往小的方向走. 这个位置就是第0行最右边. 从这个位置开始, 如果target比它大, 那么该行左侧的所有数字
 * 都不可能是target, 于是我们让row + 1. 如果target比它小, 那么在该位置所在的col中, 该位置以下的
 * 数字都不会是target, 于是我们让col - 1. 等于是往下走是increase, 往左走是decrease. 根据target和
 * 当前位置的大小, 我们来移动.
 * 
 * 时间复杂度: O(m + n)
 * 空间复杂度: O(1)
 */