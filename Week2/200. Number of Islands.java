class Solution {
    public int numIslands(char[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    grid[i][j] = '0';
                    q.offer(new int[] { i, j });
                    clearIsland(i, j, grid, q);
                    ans += 1;
                }
            }
        }
        return ans;
    }

    private void clearIsland(int row, int col, char[][] grid, Queue<int[]> q) {
        int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            for (int[] direction : directions) {
                int neighborRow = pos[0] + direction[0];
                int neighborCol = pos[1] + direction[1];
                if (!isOutOfBound(grid, neighborRow, neighborCol)
                        && grid[neighborRow][neighborCol] == '1') {
                    grid[neighborRow][neighborCol] = '0';
                    q.offer(new int[] { neighborRow, neighborCol });
                }
            }
        }
    }

    private boolean isOutOfBound(char[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}

/**
 * BFS的方法. 很直接.
 * 时间复杂度: O(M * N)
 * 空间复杂度: O(min(M, N))
 * 
 * BFS的扩展规律, 假设一个无限大的matrix, 全部是1. 我们随便挑一个点, 开始
 * 扩散. 第一圈扩散是感染四个(2 * 2矩阵的外围). 第二次感染是8个(3 * 3矩阵外围), 第四次是12个. 第n次
 * 是4n - 4个. 这个通项公式4n - 4要求n >= 2. 假设是有边界的, 那么能扩展多少次呢? 看长和宽哪个小. 小的
 * 那个先被触碰到. 假设小的那个是m. 那么扩散的次数就是m / 2个. 此时当扩散到边界的时候, 这一圈感染的个数是:
 * 4*(m / 2) - 4 = 2m - 4. 那么O一下就是O(m)了, 也就是O(min(m ,n)).
 * 
 * 也有人说可能是max(m, n) 看这样一个例子:
 * 0 1 1 0
 * 1 1 1 1
 * 1 1 1 1
 * 在某个时候, queue中会有4个元素出现. 首先是(0, 1), (1, 0)和(1, 1)被推入queue. 然后(1, 0)出queue, 此时
 * queue的长度变为2. 然后添加(1, 0)的neighbors: (2, 0)进入queue, (1, 1)在queue里面就不添加, 此时长度为3.
 * 然后(1, 1)出queue, 长度变为2, 然后再把(1, 1)的neighors添加进来: (1, 2)和(2, 1), 此时queue长度变为4.
 * 因此感觉是max(m, n). 但至于为什么m和n会限制queue中的elements的数量. 还要再思考一下.
 */

class Solution {
    public int numIslands(char[][] grid) {
        int ans = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '1') {
                    helper(grid, row, col);
                    ans += 1;
                }
            }
        }
        return ans;
    }

    private void helper(char[][] grid, int row, int col) {
        grid[row][col] = '0';
        int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (!isOutOfBound(grid, newRow, newCol) && grid[newRow][newCol] == '1') {
                helper(grid, newRow, newCol);
            }
        }
    }

    private boolean isOutOfBound(char[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}
/**
 * DFS解法. 为了防止走向走过的格子, 走到一个格子就标记为0.
 * 
 * 时间复杂度: O(M * N) 加入全部是‘1’. 从左上角开始向下到底, 右移一格然后向上, 这样走s型路线.
 * 空间复杂度: O(M * N) 也是全是‘1’, 走s型路线.
 */