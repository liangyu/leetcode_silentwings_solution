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
    // time = O(m * n * k), space = O(m * n * k)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        if (m == 1 && n == 1) return 0;

        boolean[][][] visited = new boolean[m][n][k + 1];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0});
        visited[0][0][0] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int i = cur[0], j = cur[1], r = cur[2];
                if (i == m - 1 && j == n - 1) return step;
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                    if (grid[ii][jj] == 1) {
                        if (r == k) continue;
                        if (visited[ii][jj][r + 1]) continue;
                        visited[ii][jj][r + 1] = true;
                        queue.offer(new int[]{ii, jj, r + 1});
                    } else {
                        if (visited[ii][jj][r]) continue;
                        visited[ii][jj][r] = true;
                        queue.offer(new int[]{ii, jj, r});
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
 */
