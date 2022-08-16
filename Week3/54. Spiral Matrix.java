class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return new ArrayList<>();
        int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1;
        List<Integer> result = new ArrayList<>();
        while (left <= right && up <= down) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[up][i]);
            }
            for (int i = up + 1; i <= down; i++) {
                result.add(matrix[i][right]);
            }
            if (up != down) {
                for (int i = right - 1; i >= left; i--) {
                    result.add(matrix[down][i]);
                }
            }
            if (left != right) {
                for (int i = down - 1; i > up; i--) {
                    result.add(matrix[i][left]);
                }
            }
            left += 1;
            right -= 1;
            up += 1;
            down -= 1;
        }
        return result;
    }
}

/**
 * 其实就是algo expert的答案. 如果能进入while循环, 那肯定可以从左到右traverese一遍.
 * 因为要么这一行有一个, 要么有多个. 分类讨论:
 * 如果横向有一个, 那么traverse完看纵向有几个, 有一个和有多个. 我们纵向traverse是从up + 1开始.
 * 那么这两种情况都可以cover到.
 * 如果横向有多个, 那么traverse完看纵向有几个, 有一个或有多个, 我们纵向traverse是从up + 1开始.
 * 那么这两种情况都可以cover到.
 * 
 * 完成前两个traverse后, 我们就要看是否要从右往左, 从右往左需要up和down不能重合. 我们从right - 1
 * 到left. 那么不管此时横向有几个element都可以被cover到.
 * 
 * 最后一个traverse要看left和right, 它俩不能重合. 我们从down - 1到up + 1, 所有情况都能cover到.
 * 
 * 唯一需要考虑的两个特殊情况就是1 * n和n * 1. 如果n == 1, 1 * 1我们的逻辑符合吗? 符合.
 * 1 * n呢? 符合. n * 1呢符合? 除了这几种, 其他的情况都能构成一个框至少.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m * n)
 */

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return new ArrayList<>();
        int m = matrix.length, n = matrix[0].length, matrixSize = m * n;
        int left = 0, right = n - 1, up = 0, down = m - 1;
        List<Integer> result = new ArrayList<>();
        while (result.size() < matrixSize) {
            for (int i = left; i <= right && result.size() < matrixSize; i++) {
                result.add(matrix[up][i]);
            }
            for (int i = up + 1; i <= down && result.size() < matrixSize; i++) {
                result.add(matrix[i][right]);
            }
            for (int i = right - 1; i >= left && result.size() < matrixSize; i--) {
                result.add(matrix[down][i]);
            }
            for (int i = down - 1; i > up && result.size() < matrixSize; i--) {
                result.add(matrix[i][left]);
            }
            left += 1;
            right -= 1;
            up += 1;
            down -= 1;
        }
        return result;
    }
}
/**
 * 这个是网友给的答案. 我觉得很好. 就是看现在是否把所有的数字都装进来了, 不管1 * n还是n * 1这种.
 * 
 * 时间复杂度和空间复杂度都是一样的.
 */