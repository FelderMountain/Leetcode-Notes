class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color)
            return image;
        helper(image, sr, sc, image[sr][sc], color);
        image[sr][sc] = color;
        return image;
    }

    public void helper(int[][] image, int row, int col, int color, int newColor) {
        if (!isOutOfBound(image, row - 1, col) && image[row - 1][col] == color) {
            image[row - 1][col] = newColor;
            helper(image, row - 1, col, color, newColor);
        }
        if (!isOutOfBound(image, row + 1, col) && image[row + 1][col] == color) {
            image[row + 1][col] = newColor;
            helper(image, row + 1, col, color, newColor);
        }
        if (!isOutOfBound(image, row, col - 1) && image[row][col - 1] == color) {
            image[row][col - 1] = newColor;
            helper(image, row, col - 1, color, newColor);
        }
        if (!isOutOfBound(image, row, col + 1) && image[row][col + 1] == color) {
            image[row][col + 1] = newColor;
            helper(image, row, col + 1, color, newColor);
        }
    }

    public boolean isOutOfBound(int[][] image, int row, int col) {
        return row < 0 || row >= image.length || col < 0 || col >= image[0].length;
    }
}

/**
 * 这个好让我纠结了一会儿. 本来是直接比较一个pixel上下左右和它的颜色是否相同. 比如从中间pixel开始, 假设上面的和它相同, 那么就把上面的
 * 改成新的color, 但这个问题就是和上面这个pixel相邻的相同pixel还是原来的颜色, 但是却不能被改变成新的颜色, 因为此时中心上面的颜色已经
 * 变了. 于是我想到引入一个result二维数组来存储最终的result, 这样在result这个数组中改变颜色就不影响image里面的东西了, 这样就能够
 * 正确判断相邻的pixel哪些颜色是一样的. 但是这是个很笨的方法, 为什么不直接告诉中心块的颜色是什么呢? 非要使用一个result数组. 因此改进
 * 版本就是传入central pixel之前的颜色和要被改变成的新的颜色.
 * 
 * 时间复杂度: O(m*n) 因为可能所有的pixel都是和中心pixel相同的颜色, 于是要遍历所有的pixel
 * 空间复杂度: O(m*n) 类似地, 所有的pixel都一个颜色, 从右下角开始的是起点, 路线会是先向上到头
 * 然后左走一格, 再向下到头, 左走一格, 再向上...
 * 那么此时stack tree等于是m*n.
 * 
 * 
 * 我还遇到的问题就是clone二维数组的问题. 直接使用clone()即 int[][] result = image.clone();
 * 会造成result和image同时指向一个数组.
 * 使用Arrays.copyOf()也是这样.
 * 
 * 刚才去实验了一波, 明白了. clone和copyOf都是对于一维数组的, 如果直接给个二维数组, 那么最后都会只是shallow copy.
 * 因此我们需要用循环, 一行一行的copy. 看下面的例子:
 * 
 */

public class test {
    public static void main(String[] args) {
        int[][] arrayOne = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };

        // Shallow Copy, arrayTwo will point to the same array as arrayOne
        // int[][] arrayTwo = arrayOne.clone();

        // Shallow Copy, arrayTwo will point to the same array as arrayOne
        // int[][] arrayTwo = Arrays.copyOf(arrayOne, arrayOne.length);

        int[][] arrayTwo = new int[arrayOne.length][];
        for (int i = 0; i < arrayOne.length; i++) {
            // Deep copy of a 1-D array.
            // arrayTwo[i] = arrayOne[i].clone();

            // Deep copy of a 1-D array.
            arrayTwo[i] = Arrays.copyOf(arrayOne[i], arrayOne[i].length);
        }

        arrayTwo[0][1] = 100;
        System.out.println(arrayOne[0][1]);
        System.out.println(arrayTwo[0][1]);
    }
}

// 在上面的例子中, 只有在循环中的那两个deep copy才会导致更改arrayTwo中的元素的值不会影响到arrayOne
// 在循环外的那两个直接给一个2-D array的情况会导致shallow copy, 如果那样, 改变arrayTwo指向数组的元素的
// 值会影响到arrayOne指向的数组的值, 毕竟此时这两个reference指向的是同一个数组.

class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color)
            return image;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] { sr, sc });
        int originalColor = image[sr][sc];
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            image[pos[0]][pos[1]] = color;
            int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            for (int[] direction : directions) {
                int x = pos[0] + direction[0];
                int y = pos[1] + direction[1];
                if (!isOutOfBound(image, x, y) && image[x][y] == originalColor) {
                    q.offer(new int[] { x, y });
                }
            }
        }
        return image;
    }

    private boolean isOutOfBound(int[][] image, int row, int col) {
        return row < 0 || row >= image.length || col < 0 || col >= image[0].length;
    }
}

/**
 * BFS的解法.
 */