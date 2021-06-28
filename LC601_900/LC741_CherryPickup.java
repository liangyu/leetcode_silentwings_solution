package LC601_900;

public class LC741_CherryPickup {
    /**
     * You are given an n x n grid representing a field of cherries, each cell is one of three possible integers.
     *
     * 0 means the cell is empty, so you can pass through,
     * 1 means the cell contains a cherry that you can pick up and pass through, or
     * -1 means the cell contains a thorn that blocks your way.
     * Return the maximum number of cherries you can collect by following the rules below:
     *
     * Starting at the position (0, 0) and reaching (n - 1, n - 1) by moving right or down through valid path cells
     * (cells with value 0 or 1).
     * After reaching (n - 1, n - 1), returning to (0, 0) by moving left or up through valid path cells.
     * When passing through a path cell containing a cherry, you pick it up, and the cell becomes an empty cell 0.
     * If there is no valid path between (0, 0) and (n - 1, n - 1), then no cherries can be collected.
     *
     * Input: grid = [[0,1,-1],[1,0,-1],[1,1,1]]
     * Output: 5
     *
     * Constraints:
     *
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 50
     * grid[i][j] is -1, 0, or 1.
     * grid[0][0] != -1
     * grid[n - 1][n - 1] != -1
     * @param grid
     * @return
     */
    // time = O(n^3), space = O(n^3)
    public int cherryPickup(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int n = grid.length;
        int[][][] dp = new int[n + 1][n + 1][n + 1];

        // init
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                for (int x = 0; x <= n; x++) {
                    dp[i][j][x] = Integer.MIN_VALUE; // 初值设置成最小值
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int x = 1; x <= n; x++) {
                    int y = i + j - x;
                    if (y < 1 || y > n) continue; // y出界
                    if (grid[i - 1][j - 1] == -1 || grid[x - 1][y - 1] == -1) continue; // block
                    if (i == 1 && j == 1 && x == 1) { // 起点是一定能到达的，左上角不是一个障碍物
                        dp[i][j][x] = grid[0][0];
                        continue;
                    }
                    dp[i][j][x] = Math.max(dp[i][j][x], dp[i -1][j][x - 1]);
                    dp[i][j][x] = Math.max(dp[i][j][x], dp[i][j - 1][x - 1]);
                    dp[i][j][x] = Math.max(dp[i][j][x], dp[i - 1][j][x]);
                    dp[i][j][x] = Math.max(dp[i][j][x], dp[i][j - 1][x]);

                    if (i == x && j == y) { // 两点重合
                        dp[i][j][x] += grid[i - 1][j - 1];
                    } else {
                        dp[i][j][x] += grid[i - 1][j - 1] + grid[x - 1][y - 1];
                    }
                }
            }
        }
        return Math.max(0, dp[n][n][n]); // if Integer.MIN_VALUE -> unreachable!
    }
}
/**
 * for (int i = 0; i < n; i++) {
 *     for (int j = 0; j < n; j++) {
 *          dp[i][j] = max{dp[i-1][j], dp[i][j-1]} + grid[i][j];
 *     }
 * }
 * dp[i][j][x][y] = max{dp[i-1][j][x-1][y], dp[i][j-1][x-1][y],
 *                      dp[i-1][j][x][y-1], dp[i][j-1][x][y-1]} + grid[i][j] + grid[x][y]
 *                      if (i,j) and (x,y) overlap -> only pick once
 *
 * 在走了 t 步之后，我们可能处于的位置 (x, y) 满足 x + y = t。因此如果我们在位置 (x1, x1) 和 (x2, x2) 有两个人，
 * 那么 x2 = x1 + y1 - y2。这意味着 x1，y1，y2 唯一地决定了两个走了 t 步数的人。
 * as i + j = x + y  => y = i + j - x;
 *
 * 每条路径上的一对cell做一个状态
 */
