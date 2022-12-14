class Solution {
    public int getFood(char[][] grid) {
        Queue<int[]> q1 = new ArrayDeque<>();
        Queue<int[]> q2 = new ArrayDeque<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[] startPos = getPoint(grid, '*');
        int distance = 0;
        int result = -1;
        q1.offer(startPos);
        visited[startPos[0]][startPos[1]] = true;
        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        while (!q1.isEmpty() || !q2.isEmpty()) {
            Queue<int[]> currentQueue = q1.isEmpty() ? q2 : q1;
            Queue<int[]> emptyQueue = q1.isEmpty() ? q1 : q2;
            while (!currentQueue.isEmpty()) {
                int[] currentPos = currentQueue.poll();
                char currentChar = grid[currentPos[0]][currentPos[1]];
                if (currentChar == '#') {
                    result = distance;
                    break;
                }
                for (int k = 0; k < 4; k++) {
                    int neighborRow = currentPos[0] + directions[k][0];
                    int neighborCol = currentPos[1] + directions[k][1];
                    if (!isOutOfBound(grid, neighborRow, neighborCol) && !visited[neighborRow][neighborCol]
                            && grid[neighborRow][neighborCol] != 'X') {
                        emptyQueue.offer(new int[] { neighborRow, neighborCol });
                        visited[neighborRow][neighborCol] = true;
                    }
                }
            }
            if (result != -1)
                break;
            distance += 1;
        }
        return result;
    }

    private int[] getPoint(char[][] grid, char target) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    private boolean isOutOfBound(char[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}

/**
 * ???????????????????????????, ???????????????, ??????BFS. ????????????????????????, ?????????X?????????. ???????????????queue. ??????????????????
 * ?????????distance???????????????queue???????????????distance???1, ??????????????????? q1 poll????????????q2, q2??????q1, ????????????
 * ??????????????????????????????queue???????????????.
 * 
 * ???????????????: O(n) ?????????????????????????????????.
 * ???????????????: O(n) ?????????visited??????queue.
 */

class Solution {
    public int getFood(char[][] grid) {
        Queue<int[]> q1 = new ArrayDeque<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[] startPos = getPoint(grid, '*');
        int distance = 0;
        int result = -1;
        q1.offer(startPos);
        visited[startPos[0]][startPos[1]] = true;
        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        while (!q1.isEmpty()) {
            int length = q1.size();
            for (int i = 0; i < length; i++) {
                int[] currentPos = q1.poll();
                char currentChar = grid[currentPos[0]][currentPos[1]];
                if (currentChar == '#') {
                    // result = distance;
                    // break;
                    return distance;
                }
                // for (int[] direction : directions)
                for (int k = 0; k < 4; k++) {
                    int neighborRow = currentPos[0] + directions[k][0];
                    int neighborCol = currentPos[1] + directions[k][1];
                    if (!isOutOfBound(grid, neighborRow, neighborCol) && !visited[neighborRow][neighborCol]
                            && grid[neighborRow][neighborCol] != 'X') {
                        q1.offer(new int[] { neighborRow, neighborCol });
                        visited[neighborRow][neighborCol] = true;
                    }
                }
            }
            // if (result != -1) break;
            distance += 1;
        }
        return result;
    }

    private int[] getPoint(char[][] grid, char target) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    private boolean isOutOfBound(char[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}

/**
 * ??????????????????????????????.77???78??????????????????, ?????????????????????break, ???????????????????????????result????????????-1. result???????????????.
 * ???????????????result, ?????????????????????X?????????return????????????.
 * 
 * ??????????????????????????????queue???. ??????poll??????????????????queue???????????????length, ???????????????????????????????????????????????????????????????.
 * ???????????????.
 * 
 * ???????????????: O(n)
 * ???????????????: O(n)
 */

class Solution {
    public int getFood(char[][] grid) {
        Queue<int[]> q1 = new ArrayDeque<>();
        int distance = 0;
        q1.offer(getPoint(grid, '*'));

        while (!q1.isEmpty()) {
            int length = q1.size();
            for (int i = 0; i < length; i++) {
                int[] currentPos = q1.poll();
                char currentChar = grid[currentPos[0]][currentPos[1]];

                int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
                for (int[] direction : directions) {
                    int neighborRow = currentPos[0] + direction[0];
                    int neighborCol = currentPos[1] + direction[1];
                    if (!isOutOfBound(grid, neighborRow, neighborCol) &&
                            grid[neighborRow][neighborCol] != 'X') {
                        if (grid[neighborRow][neighborCol] == '#') {
                            return distance + 1;
                        }
                        q1.offer(new int[] { neighborRow, neighborCol });
                        grid[neighborRow][neighborCol] = 'X';
                    }
                }
            }
            distance += 1;
        }
        return -1;
    }

    private int[] getPoint(char[][] grid, char target) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    private boolean isOutOfBound(char[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}

/**
 * ??????????????????. ??????????????????????????????????????????X, ????????????????????????????????????visited?????????. ???????????????visit?
 * ??????????????????????????????queue????????????visited???. ?????????????????????, ????????????????????????????????????????????????, ????????????
 * ?????????????????????#, ???????????????????????????#?????????X, ??????????????????food. ????????????????????????, ???????????????????????????????????????#,
 * ????????????????????????????????????. ???????????????,????????????????????????#, ???????????????????????????distance + 1. ????????????, ??????
 * ???????????????queue???????????????X. BFS??????????????????????????????. distance???????????????start?????????. ????????????start?????????
 * ???????????????. ?????????queue????????????????????????????????????.
 * 
 * ???????????????????????????#?????????????????????X. ???????????????queue???pop??????????????????????????????. ????????????????????????????????????neighbors???
 * ????????????????????????????????????queue????????????. ???????????????????????????????????????X????????????????????????X. ?????????????????????????????????????????????
 * #??????X?????????queue???, ???????????????????????????????????????X?????????#. ?????????????????????????????????????????????#, ?????????????????????X????????????.
 * ????????????????????????visited??????????????????.
 * ????????????????????????, ???????????????????????????????????????????????????. ????????????????????????.
 * 
 * ???01maxtrix?????????????????????, ????????????BFS?????????. DFS?????????non-cylic graph????????????.
 * 
 * 
 * ???????????????: O(n)
 * ???????????????: O(1)
 */
