package LC001_300;
import java.util.*;
public class LC63_UniquePathsII {
    /**
     * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
     *
     * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
     * corner of the grid (marked 'Finish' in the diagram below).
     *
     * Now consider if some obstacles are added to the grids. How many unique paths would there be?
     *
     * An obstacle and space is marked as 1 and 0 respectively in the grid.
     *
     * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
     * Output: 2
     *
     * Constraints:
     *
     * m == obstacleGrid.length
     * n == obstacleGrid[i].length
     * 1 <= m, n <= 100
     * obstacleGrid[i][j] is 0 or 1.
     * @param obstacleGrid
     * @return
     */
    // S1
    // time = O(m * n), space = O(m * n)
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // corner case
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0] == null || obstacleGrid[0].length == 0) {
            return 0;
        }

        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        boolean hasObs = false;
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1 || hasObs) {
                dp[i][0] = 0;
                hasObs = true;
            } else dp[i][0] = 1;
        }
        hasObs = false;
        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] == 1 || hasObs) {
                dp[0][j] = 0;
                hasObs = true;
            } else dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) dp[i][j] = 0;
                else dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    // S2:
    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        // corner case
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0] == null || obstacleGrid[0].length == 0) {
            return 0;
        }

        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                    continue;
                }
                if (i > 0) dp[i][j] += dp[i - 1][j];
                if (j > 0) dp[i][j] += dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
