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
 * 上面是我写的第一版, 思路很简单, 就是BFS. 注意不能走回头路, 不走是X的地方. 我用了两个queue. 因为新进来的
 * 元素的distance要比之前在queue中的元素的distance大1, 那么如何区分? q1 poll出去的给q2, q2再给q1, 也就是新
 * 进来的元素放到另一个queue中进行区分.
 * 
 * 时间复杂度: O(n) 最坏就是遍历所有的格子.
 * 空间复杂度: O(n) 需要存visited以及queue.
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
 * 这一版进行稍微的更改.77和78行我实在是傻, 还想着先内循环break, 再在外循环有个判断result是否等于-1. result也是多余的.
 * 因此不需要result, 内循环如果是‘X’直接return不就行了.
 * 
 * 最重要的是不需要两个queue了. 我们poll之前记录当前queue中的元素的length, 这样就不会混淆新压进来的元素和之前的元素了.
 * 这是个提升.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
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
 * 这个是最终版. 我们可以把经过的地方都标记为X, 这样就不需要维护一个二维visited数组了. 但是什么叫visit?
 * 我们之前定义是添加进queue后就代表visited了. 但是我们会发现, 此时在从一个格子向四个方向扩展时, 假如周围
 * 的邻居有一个是#, 那么此时就会把这个#标记为X, 从而会找不到food. 于是我们改变策略, 我们不单独看一个格子是不是#,
 * 而是在添加邻居的时候就看. 从开始位置,我们看邻居是不是#, 如果是那就直接返回distance + 1. 如果不是, 那就
 * 把它添加进queue并且标记为X. BFS等于是一圈一圈往外扩. distance记录着距离start的距离. 每一圈到start的距离
 * 都是相等的. 添加进queue的过程就代表着扩圈的过程.
 * 
 * 就是把检查过是否是#的格子都标记为X. 此时如果在queue中pop出来检查其实是可以的. 但是此时我们再添加它周围neighbors的
 * 时候会出现添加已经被加在queue中的格子. 于是我们想到不在检查完后改X而是在添加完后改X. 但是这样带来的问题就是可能会把
 * #改成X添加进queue中, 到时候再去这个位置的时候是X而不是#. 于是我们想到在添加前判断是否为#, 不是的话就改为X然后添加.
 * 这样就避免了使用visited这个二维数组.
 * 方法理解是一方面, 理解怎么思考出这个方法才是最关键的. 重要在于举一反三.
 * 
 * 和01maxtrix两道题形成互补, 体会到了BFS的威力. DFS一般在non-cylic graph中用得多.
 * 
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */
