class Solution {
    // 0 -> 1 mark as -2
    // 1 -> 0 mark as -1
    private final int[][] DIRECTIONS = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 },
            { 1, -1 }, { 0, -1 } };

    public void gameOfLife(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int liveNeighbors = countLiveNeighbors(board, row, col);
                if (board[row][col] == 1) {
                    board[row][col] = liveNeighbors < 2 ? -1 : liveNeighbors <= 3 ? 1 : -1;
                } else {
                    board[row][col] = liveNeighbors == 3 ? -2 : 0;
                }
            }
        }
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == -1) {
                    board[row][col] = 0;
                } else if (board[row][col] == -2) {
                    board[row][col] = 1;
                }
            }
        }
    }

    private int countLiveNeighbors(int[][] board, int row, int col) {
        int ans = 0;
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (!isOutOfBound(board, newRow, newCol) && Math.abs(board[newRow][newCol]) == 1) {
                ans += 1;
            }
        }
        return ans;
    }

    private boolean isOutOfBound(int[][] board, int row, int col) {
        return row < 0 || row >= board.length || col < 0 || col >= board[0].length;
    }
}
/**
 * 要用一个数字表示0 -> 1以及一个数字1 - > 0.
 * 这样我们不仅知道某个格子之前是什么, 变化后应该是什么.
 * 
 * 我是用1 -> 0 用-1表示, 0 -> 1用-2表示.
 * 也可以1 -> 0 用2表示, 0 -> 1用3表示. 这样最后第二次遍历的时候, 每个位置只需要除以二取余
 * 即为更新后的值了.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(1)
 */