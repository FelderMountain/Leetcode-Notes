class Solution {
    public int closedIsland(int[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if ((i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1) && grid[i][j] == 0) {
                    bfs(grid, i, j);
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    bfs(grid, i, j);
                    count += 1;
                }
            }
        }
        return count;
    }

    private void bfs(int[][] grid, int row, int col) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] { row, col });
        grid[row][col] = 1;
        int[][] directions = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            for (int[] direction : directions) {
                int x = pos[0] + direction[0];
                int y = pos[1] + direction[1];
                if (!isOutOfBound(grid, x, y) && grid[x][y] == 0) {
                    grid[x][y] = 1;
                    q.offer(new int[] { x, y });
                }
            }
        }
    }

    private boolean isOutOfBound(int[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}

/**
 * 比较聪明的是首先我们沿着边, 把是挨着边的islands全都标记为1, 如果某个island被1全部包围, 那么
 * 它们就不会被标记为1, 只有那些挨着边的会被标记. 然后再遍历一遍格子, 查剩下的islands个数即可.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(min(m, n)) 就是扩张先到那个边那个方法. 等到扩张过程中到达某个边的时候, 扩张出来的菱形边界可以填满较短的两个边.
 * 然后再扩的时候, 菱形长度就不会更长了.
 */

class Solution {
    int[][] DIRECTIONS = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public int closedIsland(int[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 0 && dfs(grid, row, col)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private boolean dfs(int[][] grid, int row, int col) {
        grid[row][col] = 1;
        boolean flag = row == 0 || row == grid.length - 1 || col == 0 || col == grid[0].length - 1 ? false : true;
        for (int[] direction : DIRECTIONS) {
            int x = row + direction[0];
            int y = col + direction[1];
            if (!isOutOfBound(grid, x, y) && grid[x][y] == 0) {
                flag &= dfs(grid, x, y);
            }
        }
        return flag;
    }

    private boolean isOutOfBound(int[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}
/**
 * 这是DFS的解法. 它能告诉我们在DFS的过程中是否有遇到触碰到边界的情况. 只有在不碰到边界的时候
 * 我们才让count加一.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m * n)
 */