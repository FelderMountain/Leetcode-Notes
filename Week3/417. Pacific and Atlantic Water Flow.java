
class Solution {
    private final int[][] DIRECTIONS = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        boolean[][] reachPacific = new boolean[heights.length][heights[0].length];
        boolean[][] reachAtlantic = new boolean[heights.length][heights[0].length];
        for (int i = 0; i < heights.length; i++) {
            reachPacific[i][0] = true;
            reachAtlantic[i][heights[0].length - 1] = true;
        }

        for (int i = 0; i < heights[0].length; i++) {
            reachPacific[0][i] = true;
            reachAtlantic[heights.length - 1][i] = true;
        }

        for (int i = 0; i < heights[0].length; i++) {
            dfs(0, i, reachPacific, heights);
            dfs(heights.length - 1, i, reachAtlantic, heights);
        }
        for (int i = 0; i < heights.length; i++) {
            dfs(i, 0, reachPacific, heights);
            dfs(i, heights[0].length - 1, reachAtlantic, heights);
        }

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

    private void dfs(int row, int col, boolean[][] reach, int[][] heights) {
        for (int[] direction : DIRECTIONS) {
            int x = row + direction[0];
            int y = col + direction[1];
            if (!isOutOfBound(heights, x, y) && !reach[x][y] && heights[x][y] >= heights[row][col]) {
                reach[x][y] = true;
                dfs(x, y, reach, heights);
            }
        }
    }

    private boolean isOutOfBound(int[][] heights, int row, int col) {
        return row < 0 || row >= heights.length || col < 0 || col >= heights[0].length;
    }
}

/**
 * 我之前的想法是从每一个位置出发, 走dfs, 看能不能走到pacific和atlantic. 这样会有一个问题, 如果我们一直走, 走到
 * 某个点前左右都走不通了, 那么这个点一定不可以到pacific或者atlantic吗? 不一定, 可能我们退后几步然后换条路线
 * 就走到了. 比如a -> b - > c - > d. 到达d点时它的前左右都比自己的height高, 那么d点一定不能到达atlantic或者
 * pacific吗? 不一定哦, 假设c左拐可以到pacific(只是DFS的选择先走了d), 那么d此时是可以到达pacific的. 于是我们
 * 得出的结论是我们走到某个点的时候走不下的时候, 是无法判断该点能不能到达某个ocean的.
 * 
 * 那么该怎么做呢? 从边线出发, 因为如果要到达pacific或者atlantic一定会到达边上某点处. 我们从边线上的点一一出发,
 * 每个点都使用DFS, 然后把能到达的点都标记上. 我们先从pacific的边线上的点挨个出发进行dfs, 能够达到的点都标记上.
 * 其次我们再从atlantic的边线上的点一一出发进行dfs, 标记上能够到达的点. 这样两次都是true的点就是既能到达pacific又能
 * 到达atlantic的了.
 * 
 */

class Solution {
    private final int[][] DIRECTIONS = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        boolean[][] reachPacific = new boolean[heights.length][heights[0].length];
        boolean[][] reachAtlantic = new boolean[heights.length][heights[0].length];
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
}
/**
 * 我们还可以使用BFS. 我们首先把pacific的边线上的点压入queue中, 看看每个点能把周围的哪些点给感染上.
 * 被感染的点再被压进queue中, 看它们能再感染周围的哪些点. 此时我们要注意, 某个点可能被check多次.
 * 比如点a可以到达点b, 但是比点b高, 此时b点就不能到达pacific. 随着我们感染点, 点c也能到达b, 此时点c比
 * 点b低, 那么此时点b就可以被压入queue了. queue的目的就是召集可以到达pacific的点, 然后感染周围的点.
 */

/**
 * 两种答案的时间复杂度都是O(M * N)
 * 空间复杂度也都是O(M * N)
 */