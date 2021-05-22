package LC001_300;
import java.util.*;
public class LC64_MinimumPathSum {
    /**
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes
     * the sum of all numbers along its path.
     *
     * Note: You can only move either down or right at any point in time.
     *
     * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
     * Output: 7
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * 0 <= grid[i][j] <= 100
     * @param grid
     * @return
     */
    // S1
    // time = O(m * n), space = O(m * n)
    public int minPathSum(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                    continue;
                }
                dp[i][j] = Integer.MAX_VALUE;
                if (i > 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);
                if (j > 0) dp[i][j] = Math.min(dp[i][j], dp[i][j - 1]);
                dp[i][j] += grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    // S2
    public int minPathSum2(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                    continue;
                }
                dp[i][j] = Integer.MAX_VALUE;
                if (i > 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);
                if (j > 0) dp[i][j] = Math.min(dp[i][j], dp[i][j - 1]);
                dp[i][j] += grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    // S3: rolling array 滚动数组
    public int minPathSum3(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[2][n]; // create 2 rows of array

        // 2 pointers
        // where is row i is stored: now
        // where is row i - 1 is stored: old (old = 1 - now)
        int old = 0, now = 0;
        for (int i = 0; i < m; i++) {
            old = now;
            now = 1 - now;
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[now][j] = grid[i][j];
                    continue;
                }
                dp[now][j] = Integer.MAX_VALUE;
                if (i > 0) dp[now][j] = Math.min(dp[now][j], dp[old][j]);
                if (j > 0) dp[now][j] = Math.min(dp[now][j], dp[now][j - 1]);
                dp[now][j] += grid[i][j];
            }
        }
        return dp[now][n - 1];
    }
}
