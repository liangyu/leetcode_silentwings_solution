package LC1201_1500;

public class LC1277_CountSquareSubmatriceswithAllOnes {
    /**
     * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
     *
     * Input: matrix =
     * [
     *   [0,1,1,1],
     *   [1,1,1,1],
     *   [0,1,1,1]
     * ]
     * Output: 15
     *
     * Constraints:
     *
     * 1 <= arr.length <= 300
     * 1 <= arr[0].length <= 300
     * 0 <= arr[i][j] <= 1
     * @param matrix
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];

        int res = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    res += dp[i][j];
                }
            }
        }
        return res;
    }
}
/**
 * ref: LC221
 * matrix[i][j] = 1
 * dp[i][j] = min{dp[i-1][j], dp[i][j-1],dp[i-1][j-1]} + 1;
 */
