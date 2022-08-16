class Solution {
    private final int[][] DIRECTIONS = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public void solve(char[][] board) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board[0].length; i++) {
            if (!visited[0][i] && board[0][i] == 'O') {
                dfs(board, 0, i, false, visited);
            }
            if (!visited[board.length - 1][i] && board[board.length - 1][i] == 'O') {
                dfs(board, board.length - 1, i, false, visited);
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (!visited[i][0] && board[i][0] == 'O') {
                dfs(board, i, 0, false, visited);
            }
            if (!visited[i][board[0].length - 1] && board[i][board[0].length - 1] == 'O') {
                dfs(board, i, board[0].length - 1, false, visited);
            }
        }
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[0].length - 1; col++) {
                if (!visited[row][col] && board[row][col] == 'O') {
                    dfs(board, row, col, true, visited);
                }
            }
        }
    }

    private void dfs(char[][] board, int row, int col, boolean markX, boolean[][] visited) {
        char markChar = markX ? 'X' : 'O';
        board[row][col] = markChar;
        visited[row][col] = true;
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (!isOutOfBound(board, newRow, newCol) && !visited[newRow][newCol] && board[newRow][newCol] == 'O') {
                dfs(board, newRow, newCol, markX, visited);
            }
        }
    }

    private boolean isOutOfBound(char[][] board, int row, int col) {
        return row < 0 || row >= board.length || col < 0 || col >= board[0].length;
    }
}
/**
 * 第一种最直接的做法. 就是先把边界是‘O’的进行DFS一波, 把所有能够走到边界的O标记一下.
 * 然后在遍历所有的元素, 把之前没有被标记到的‘O’改为‘X’.
 * 
 * 时间复杂度: O(m * n)
 * 空间复杂度: O(m * n)
 */