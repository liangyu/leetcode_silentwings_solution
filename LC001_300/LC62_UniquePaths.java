package LC001_300;
import java.util.*;
public class LC62_UniquePaths {
    /**
     * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
     *
     * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
     * corner of the grid (marked 'Finish' in the diagram below).
     *
     * How many possible unique paths are there?
     *
     * Input: m = 3, n = 7
     * Output: 28
     *
     * Constraints:
     *
     * 1 <= m, n <= 10^0
     * It's guaranteed that the answer will be less than or equal to 2 * 10^9.
     *
     * @param m
     * @param n
     * @return
     */
    // S1: dp
    // time = O(m * n), space = O(m * n)
    public int uniquePaths(int m, int n) {
        int[][] f = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) f[i][j] = 1;
                else {
                    if (i > 0) f[i][j] += f[i - 1][j];
                    if (j > 0) f[i][j] += f[i][j - 1];
                }
            }
        }
        return f[m - 1][n - 1];
    }

    // S2
    // time = O(m * n), space = O(m * n)
    public int uniquePaths2(int m, int n) {
        int[][] dp = new int[m][n];

        // init
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    // S2: dp

}
