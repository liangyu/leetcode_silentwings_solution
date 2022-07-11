package LC001_300;
import java.util.*;
public class LC221_MaximalSquare {
    /**
     * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return
     * its area.
     *
     * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
     * Output: 4
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 300
     * matrix[i][j] is '0' or '1'.
     * @param matrix
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] f = new int[m + 1][n + 1];

        int res = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    f[i][j] = Math.min(f[i - 1][j], Math.min(f[i][j - 1], f[i - 1][j - 1])) + 1; // 别忘了 + 1 ！
                    res = Math.max(res, f[i][j]);
                }
            }
        }
        return res * res;
    }
}
/**
 * dp[i][j] = min{dp[i-1][j], dp[i][j-1],dp[i-1][j-1]} + 1
 * dp[i-1][j-1]
 * dp[i-a][j-b]
 *
 * f[i,j]: 以(i,j)为右下角的最大正方形边长
 *
 */
