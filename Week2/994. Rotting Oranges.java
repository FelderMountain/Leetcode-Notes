class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> rotten = new LinkedList<>();
        int m = grid.length, n = grid[0].length;
        int unrotten = 0;
        // Add all rotten oranges positions into queue.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2)
                    rotten.offer(new int[] { i, j });
                if (grid[i][j] == 1)
                    unrotten += 1;
            }
        }

        int ans = 0;
        int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!rotten.isEmpty()) {
            int length = rotten.size();
            boolean newRotten = false;
            for (int i = 0; i < length; i++) {
                int[] pos = rotten.poll();
                for (int[] direction : directions) {
                    int newRow = pos[0] + direction[0];
                    int newCol = pos[1] + direction[1];
                    if (!isOutOfBound(grid, newRow, newCol) && grid[newRow][newCol] == 1) {
                        newRotten = true;
                        grid[newRow][newCol] = 2;
                        unrotten -= 1;
                        rotten.offer(new int[] { newRow, newCol });
                    }
                }
            }
            if (newRotten == true)
                ans += 1;
        }
        return unrotten == 0 ? ans : -1;
    }

    private boolean isOutOfBound(int[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}

/**
 * 这是我一开始写的. 就是简单的BFS. 但是值得注意的是第20和34行. 我加这两行是我发现如果直接加ans, 我们等于是默认会有
 * 新的tomato被污染. 然而想象这种情况, 经过最后一次污染后, 所有能被污染的tomato都被污染了, 现在queue中剩下的就是
 * 这最后一次被污染的tomatoes的坐标. 然后我们ans += 1. 到目前为止没有问题. 此时queue不是空的还要继续进入循环, 然后
 * 进入内循环. 但是此时已经没有别的tomatoes可以被污染了, 但我们再内循环结束后仍然对ans += 1, 此时就不对了. 因此这个while
 * 循环会在最后经历一次不往queue中新添加任何东西的一次循环. 我们在这次循环中会对ans多加一个1. 因此我们可以这样: 在内循环中
 * 记录一个boolean, 来判断此次是不是有新的tomato被污染, 如果有的话, 我们ans += 1, 否则就不加. 这样就把最后一次那个不污染
 * tomato的循环区别开了.
 * 时间复杂度: O(mn)
 * 空间复杂度: O(mn) queue会需要空间.
 */

class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> rotten = new LinkedList<>();
        int m = grid.length, n = grid[0].length;
        // It is possible that some oranges may never be reached.
        // The completion of BFS doesn't mean all oranges are
        // rotten therefore we need a variable to keep track of
        // how many oranges have been rottened.
        int unrotten = 0;

        // Add all rotten oranges positions into queue.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2)
                    rotten.offer(new int[] { i, j });
                if (grid[i][j] == 1)
                    unrotten += 1;
            }
        }

        if (unrotten == 0)
            return 0;
        int ans = 0;
        int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!rotten.isEmpty()) {
            int length = rotten.size();
            for (int i = 0; i < length; i++) {
                int[] pos = rotten.poll();
                for (int[] direction : directions) {
                    int newRow = pos[0] + direction[0];
                    int newCol = pos[1] + direction[1];
                    if (!isOutOfBound(grid, newRow, newCol) && grid[newRow][newCol] == 1) {
                        grid[newRow][newCol] = 2;
                        unrotten -= 1;
                        rotten.offer(new int[] { newRow, newCol });
                    }
                }
            }
            ans += 1;
        }
        return unrotten == 0 ? ans - 1 : -1;
    }

    private boolean isOutOfBound(int[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}
/**
 * 我们现在知道最后一次循环会给ans多加一个1, 因此我们没有必要去维护一个boolean然后看有没有污染tomato. 这样代价有点儿高,
 * 我们直接让ans - 1即可. 同时要考虑有没有可能这个while循环一次都不执行, 有可能, 那就是没有rotten的tomatoes, 于是我们在
 * 72行新添加一行表示如果没有rotten tomatoes的时候直接return 0.
 * 
 * 时间复杂度和空间复杂度和上面的解法一样.
 */