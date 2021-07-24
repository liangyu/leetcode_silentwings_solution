package LC1801_2100;
import java.util.*;
public class LC1937_MaximumNumberofPointswithCost {
    /**
     * You are given an m x n integer matrix points (0-indexed). Starting with 0 points, you want to maximize the
     * number of points you can get from the matrix.
     *
     * To gain points, you must pick one cell in each row. Picking the cell at coordinates (r, c) will add points[r][c]
     * to your score.
     *
     * However, you will lose points if you pick a cell too far from the cell that you picked in the previous row. For
     * every two adjacent rows r and r + 1 (where 0 <= r < m - 1), picking cells at coordinates (r, c1) and (r + 1, c2)
     * will subtract abs(c1 - c2) from your score.
     *
     * Return the maximum number of points you can achieve.
     *
     * abs(x) is defined as:
     *
     * x for x >= 0.
     * -x for x < 0.
     *
     * Input: points = [[1,2,3],[1,5,1],[3,1,1]]
     * Output: 9
     *
     * Constraints:
     *
     * m == points.length
     * n == points[r].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 0 <= points[r][c] <= 10^5
     * @param points
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public long maxPoints(int[][] points) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0) return 0;

        int m = points.length, n = points[0].length;

        long[][] dp = new long[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(dp[i], Integer.MIN_VALUE);
        for (int j = 0; j < n; j++) dp[0][j] = points[0][j];


        for (int i = 1; i < m; i++) {
            long rollingMax = Long.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                rollingMax = Math.max(rollingMax, dp[i - 1][j] + j);
                dp[i][j] = Math.max(dp[i][j], rollingMax + points[i][j] - j);
            }
            rollingMax = Long.MIN_VALUE;
            for (int j = n - 1; j >= 0; j--) {
                rollingMax = Math.max(rollingMax, dp[i - 1][j] - j);
                dp[i][j] = Math.max(dp[i][j], rollingMax + points[i][j] + j);
            }
        }

        long res = Long.MIN_VALUE;
        for (int j = 0; j < n; j++) {
            res = Math.max(res, dp[m - 1][j]);
        }
        return res;
    }
}
/**
 * dp[i][j]: starting from the first row until i-th row, and you pick points[i][j], then the maximum points you can obtain
 * for (int i = 0; i < m; i++) {
 *     for (int j = 0; j < n; j++) {
 *         for (int k = 0; k < n; k++) {
 *             dp[i][j] = max{dp[i - 1][k] + points[i][j] - Math.abs(j - k)};
 *         }
 *     }
 * }
 * time = O(m*n^2) => 10^10 TLE
 * dp[i][j] = max{dp[i - 1][k] + k} + points[i][j] - j;   k = 0, 1, 2, ..., j  (k <= j)
 * p[i][j] = max{dp[i - 1][k] - k} + points[i][j] + j;   k = j+1,j+2, j+3, ..., n-1 (k > j)
 * rolling max => 均摊O(1)
 *
 */
