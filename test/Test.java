import java.util.*;

public class Test {
    public static void main(String[] args) {
        char[][] board = new char[][] {{'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        Test myTest = new Test();
        myTest.solveSudoku(board);
    }

    private Map<Integer, Set<Integer>> rowMap;
    private Map<Integer, Set<Integer>> colMap;
    private Map<Integer, Set<Integer>> blockMap;
    public void solveSudoku(char[][] board) {
        rowMap = new HashMap<>();
        colMap = new HashMap<>();
        blockMap = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            rowMap.put(i, new HashSet<>());
            colMap.put(i, new HashSet<>());
            blockMap.put(i, new HashSet<>());
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    updateMaps(i, j, Character.getNumericValue(board[i][j]), true);
                }
            }
        }
        helper(board, 0, 0);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private boolean helper(char[][] board, int row, int col) {
        if (col == 9) {
            row += 1;
            if (row == 9) {
                return true;
            }
            col = 0;
        }
        if (board[row][col] != '.') {
            return helper(board, row, col + 1);
        } else {
            for (int i = 1; i <= 9; i++) {
                if (isValid(row, col, i)) {
                    board[row][col] = (char) (i + '0');
                    updateMaps(row, col, i, true);
                    if (helper(board, row, col + 1)) {
                        return true;
                    }
                    updateMaps(row, col, Character.getNumericValue(board[row][col]), false);
                }
            }
            board[row][col] = '.';
            return false;
        }
    }

    private void updateMaps(int row, int col, int num, boolean isAdd) {
        int blockNum = (row / 3) * 3 + col / 3;
        if (isAdd) {
            rowMap.get(row).add(num);
            colMap.get(col).add(num);
            blockMap.get(blockNum).add(num);
        } else {
            rowMap.get(row).remove(num);
            colMap.get(col).remove(num);
            blockMap.get(blockNum).remove(num);
        }
    }

    private boolean isValid(int row, int col, int num) {
        int blockNum = (row / 3) * 3 + col / 3;
        return !rowMap.get(row).contains(num) && !colMap.get(col).contains(num) && !blockMap.get(blockNum).contains(num);
    }

    private final int[][] DIRECTIONS = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        boolean[][] reachPacific = new boolean[heights.length][heights[0].length];
        boolean[][] reachAtlantic = new boolean[heights.length][heights[0].length];
        Set<Integer> set = new HashSet<>();
        String string = new String();
        string.substring(0);
        Queue<int[]> queuePacific = new ArrayDeque<>();
        Queue<int[]> queueAtlantic = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            reachPacific[i][0] = true;
            queuePacific.offer(new int[] { i, 0 });
            reachAtlantic[i][heights[0].length - 1] = true;
            queueAtlantic.offer(new int[] { i, heights[0].length - 1 });
        }

        for (int i = 0; i < heights[0].length; i++) {
            reachPacific[0][i] = true;
            queuePacific.offer(new int[] { 0, i });
            reachAtlantic[heights.length - 1][i] = true;
            queueAtlantic.offer(new int[] { heights.length - 1, i });
        }

        bfs(reachPacific, heights, queuePacific);
        bfs(reachAtlantic, heights, queueAtlantic);

        List<List<Integer>> ans = new ArrayList<>();
        for (int row = 0; row < heights.length; row++) {
            for (int col = 0; col < heights[0].length; col++) {
                if (reachPacific[row][col] && reachAtlantic[row][col]) {
                    ans.add(new ArrayList<>(Arrays.asList(row, col)));
                }
            }
        }
        return ans;
    }

    private void bfs(boolean[][] reach, int[][] heights, Queue<int[]> queue) {
        while (!queue.isEmpty()) {
            int[] currPos = queue.poll();
            for (int[] direction : DIRECTIONS) {
                int x = currPos[0] + direction[0];
                int y = currPos[1] + direction[1];
                if (!isOutOfBound(heights, x, y) && !reach[x][y] && heights[x][y] >= heights[currPos[0]][currPos[1]]) {
                    reach[x][y] = true;
                    queue.offer(new int[] { x, y });
                }
            }
        }
    }

    private boolean isOutOfBound(int[][] heights, int row, int col) {
        return row < 0 || row >= heights.length || col < 0 || col >= heights[0].length;
    }

    public static int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        int result = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (list.get(middle) == target) {
                return middle;
            } else if (list.get(middle) < target) {
                result = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return result;
    }
}
