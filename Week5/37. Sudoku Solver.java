class Solution {
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
            // updateMaps(row, col, Character.getNumericValue(board[row][col]), false);
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
        return !rowMap.get(row).contains(num) && !colMap.get(col).contains(num)
                && !blockMap.get(blockNum).contains(num);
    }
}
/**
 * 这道题真是醉了. 花了一个多小时debug. 关键在于第47行. 我当时想的是如果1到9试过都不行, 我们就要把此时在的
 * 位置恢复为‘.’同时要更新maps, 把row, col和block map里装有的此时该位置的数字给移除. 问题就是我先把这个位置
 * 的数字给抹了, 然后才更新maps, 那等于移除所有map中的‘.’, 那跟没有更新一个样子. 找了半天, 我把47行调到了
 * 46行之前, 发现还不行. 之后是怎么也找不到.
 * 
 * 最后没办法打断点一点儿一点儿找. 最后发现的问题就是, 本来一个位置可以放一个数字但是却没有选择放这个数字, 于是我就想到
 * 看来是remove出现了问题. 一看才意识到, 如果我把remove这一步放到for循环结束, 那么我从map移除的只是这个位置最后一次出现的
 * 数字, 之前在这个位置出现的数字我都没有移除. 因此我们应该把remove这一步放到现在的43行这里, 也就是先填一个数字, 往后继续走,
 * 如果后面走完发现走不通回来了, 那么我们就要把这个数字给抹了, 也就是此时就得更新所有maps了. 问题也就出现在这里. 真是受罪.
 * 
 * 时间复杂度: O()
 * 空间复杂度: O(1)
 */