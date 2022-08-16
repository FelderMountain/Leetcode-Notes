class Solution {
    public boolean isValidSudoku(char[][] board) {
        Map<Integer, Set<Character>> rowRecorder = new HashMap<>();
        Map<Integer, Set<Character>> colRecorder = new HashMap<>();
        Map<Integer, Set<Character>> boxRecorder = new HashMap<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != '.') {
                    rowRecorder.putIfAbsent(row, new HashSet<>());
                    colRecorder.putIfAbsent(col, new HashSet<>());
                    int boxIndex = (row / 3) * 3 + col / 3;
                    boxRecorder.putIfAbsent(boxIndex, new HashSet<>());
                    if (!rowRecorder.get(row).add(board[row][col]) || !colRecorder.get(col).add(board[row][col])
                            || !boxRecorder.get(boxIndex).add(board[row][col])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
/**
 * 使用hashset的add会返回boolean, 这个很好用.
 * 
 * 时间复杂度: O(9 * 9) = O(1)
 * 空间复杂度: O(9 * 9 * 3) = O(1) 同一个非.的字符会被存储三次.
 */