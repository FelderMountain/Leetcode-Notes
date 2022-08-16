class Solution {
    public void rotate(int[][] matrix) {
        int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1;
        while (left < right) {
            for (int i = left; i < right; i++) {
                // element in row up.
                int temp = matrix[up][i];
                // row up to col right
                temp = swap(matrix, temp, i - left + up, right);
                // col right to row down
                temp = swap(matrix, temp, down, right - (i - left));
                // row down to col left
                temp = swap(matrix, temp, down - (i - left), left);
                // col left to row up
                temp = swap(matrix, temp, up, i);
            }
            left += 1;
            right -= 1;
            up += 1;
            down -= 1;
        }
    }

    private int swap(int[][] matrix, int tmp, int row, int col) {
        int temp = matrix[row][col];
        matrix[row][col] = tmp;
        return temp;
    }
}

/**
 * 这是我原来的想法. 就是一个边框一个边框进行旋转. 存左上角和右上角换, 换之前把右上角的存起来. 换完后和右下角换
 * 并且把右下角之前的值存到我们的变量里, 然后再和左下角换(存好左下角之前的变量), 最后和左上角换. 把上边框除了最后一个
 * 元素外所有的遍历完即可.
 * 
 * 如何确定上边框某个位置旋转后是在右边框哪个位置呢? 其实很简单, 假设上边框某个点x, y. 它到left的距离和它旋转后到
 * up的距离应该是一样的(简单的几何, 旋转). 因此我们有x - left = ? - up, 这里的?表示旋转后的横坐标.
 * 类似地从右边框某个位置旋转到下边框一样的道理. 右边框的这个点到up的距离和旋转后的点到right的距离相同也当然和最开始
 * 上边框的点到left的距离. 于是有i - left = ? - right. ?是右边框旋转到下边框的横坐标. 这样所有的点就都能找到了.
 * 
 * 时间复杂度: O(n^2) n是边长.
 * 空间复杂度: O(1)
 * 
 */

class Solution {
    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix[0].length; j++) {
                swap(matrix, i, j, j, i);
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0, k = matrix[0].length - 1; j < k; j++, k--) {
                swap(matrix, i, j, i, k);
            }
        }
    }

    private void swap(int[][] matrix, int rowOne, int colOne, int rowTwo, int colTwo) {
        int temp = matrix[rowOne][colOne];
        matrix[rowOne][colOne] = matrix[rowTwo][colTwo];
        matrix[rowTwo][colTwo] = temp;
    }
}
/**
 * 这个方法太牛逼了. 先转置, 再把每行的element reverse一下就行了.
 * 这个转置需要记一下.
 * 转置的话从第0行开始, 第0个位置在斜对角线上, 因此我们从第1个位置开始, 然后和对角线对称的位置交换位置, 遍历完所有的数字.
 * 到第一行的话, 我们要注意那些交换过的位置不要再和它的对角线对称的位置再交换了. 此时第一行的第0个位置已经完成交换而第一个
 * 位置在对角线上, 因此我们从第二个位置开始. 现在规律明显了. 如果我们遍历到第i行, 我们要从i + 1个位置开始进行交换, 这样
 * 避免对角线上的位置自己和自己交换, 也避免交换之前交换过的位置. 这次有了49行的j = i + 1.
 * 
 * 时间复杂度和空间复杂度不变.
 * 
 * 推广一下:
 * 顺时针转: 先转置, 再左右reverse.
 * 逆时针转: 先转置, 再上下reverse.
 */