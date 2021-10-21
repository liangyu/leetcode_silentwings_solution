package LC1201_1500;
import java.util.*;
public class LC1293_ShortestPathinaGridwithObstaclesElimination {
    /**
     * You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up,
     * down, left, or right from and to an empty cell in one step.
     *
     * Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner
     * (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
     *
     * Input:
     * grid =
     * [[0,0,0],
     *  [1,1,0],
     *  [0,0,0],
     *  [0,1,1],
     *  [0,0,0]],
     * k = 1
     * Output: 6
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 40
     * 1 <= k <= m * n
     * grid[i][j] == 0 or 1
     * grid[0][0] == grid[m - 1][n - 1] == 0
     * @param grid
     * @param k
     * @return
     */
    // time = O(m * n * min(k, m + n), space = O(m * n * min(k, m + n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        if (k >= m + n - 3) return m + n - 2; // 如果k >= m + n - 3，那么最短路径一定是m + n - 2

        boolean[][][] visited = new boolean[m][n][k + 1];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0});
        visited[0][0][0] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1], r = cur[2];
                if (x == m - 1 && y == n - 1) return step;
                for (int t = 0; t < 4; t++) {
                    int i = x + directions[t][0];
                    int j = y + directions[t][1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (grid[i][j] == 1) {
                        if (r == k) continue;
                        if (visited[i][j][r + 1]) continue;
                        visited[i][j][r + 1] = true;
                        queue.offer(new int[]{i, j, r + 1});
                    } else {
                        if (visited[i][j][r]) continue;
                        visited[i][j][r] = true;
                        queue.offer(new int[]{i, j, r});
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
/**
 * 允许你穿越一些障碍物，可以穿越k次
 * visited[i][j] = 0/1
 * step 0: (0,0)
 * step 1: (0,1), (1,0)
 * step 2: (1,1),(0,2),(2,0) ...
 * 有障碍物之后，之前访问过的也可能可以访问
 * 0 x 0 0
 * 0 x 0 0
 * 0 0 0 0
 * visited[i][j][k]
 * step 2: (0,2,1) 记录使用几次穿墙
 * step 6: (0,2,0) ...
 * 两者是有区别的，选哪一个呢？答案是说不准！
 * 虽然step2步数少，但是用了一次穿墙，以后说不定想用就没机会了 => 两者都是需要记载的
 * step: (i,j,k)
 * step+1: (i,j+1,k) or (i,j+1,k+1)
 * 本题可以上下左右移动，无后效性，所以不能使用DP来做。
 * bfs多了一维状态，ijk
 * 假设网格中都是0，没有障碍物，每次只能走四个方向，那么最短路径一定是m+n-2。
 * 如果k>=m+n-3，那么最短路径一定是m+n-2。不需要BFS，浪费性能。 如果k<m+n-3，才需要BFS。
 */
