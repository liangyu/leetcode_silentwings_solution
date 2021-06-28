package LC301_600;

public class LC576_OutofBoundaryPaths {
    /**
     * There is an m x n grid with a ball. The ball is initially at the position [startRow, startColumn]. You are
     * allowed to move the ball to one of the four adjacent cells in the grid (possibly out of the grid crossing the
     * grid boundary). You can apply at most maxMove moves to the ball.
     *
     * Given the five integers m, n, maxMove, startRow, startColumn, return the number of paths to move the ball out of
     * the grid boundary. Since the answer can be very large, return it modulo 10^9 + 7.
     *
     * Input: m = 2, n = 2, maxMove = 2, startRow = 0, startColumn = 0
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= m, n <= 50
     * 0 <= maxMove <= 50
     * 0 <= startRow < m
     * 0 <= startColumn < n
     * @param m
     * @param n
     * @param maxMove
     * @param startRow
     * @param startColumn
     * @return
     */
    // S1
    // time = O(m * n * k), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][] dp = new int[m][n];
        int M = (int)(1e9 + 7);
        dp[startRow][startColumn] = 1;
        int res = 0;

        for (int k = 0; k < maxMove; k++) {
            int[][] temp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int[] dir : DIRECTIONS) {
                        int ii = i + dir[0];
                        int jj = j + dir[1];
                        if (ii < 0 || ii >= m || jj < 0 || jj >= n) {
                            res = (res + dp[i][j]) % M;
                        } else {
                            temp[ii][jj] = (dp[i][j] + temp[ii][jj]) % M;
                        }
                    }
                }
            }
            dp = temp;
        }
        return res;
    }

    // S2
    // time = O(m * n * k), space = O(m * n)
    public int findPaths2(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][] dp = new int[m][n];
        int M = (int)(1e9 + 7);

        for (int k = 0; k < maxMove; k++) {
            int[][] temp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    long a = i - 1 < 0 ? 1 : dp[i - 1][j];
                    long b = i + 1 >= m ? 1 : dp[i + 1][j];
                    long c = j - 1 < 0 ? 1 : dp[i][j - 1];
                    long d = j + 1 >= n ? 1 : dp[i][j + 1];
                    temp[i][j] = (int)((a + b + c + d) % M);
                }
            }
            dp = temp;
        }
        return dp[startRow][startColumn];
    }
}
/**
 * 1 0      1 1    2 1     2 4
 * 0 0      1 0    1 2     4 2
 *
 *   x x
 * x 2 4 x
 * x 4 2 x
 *   x x
 * 每一步同步dp的网格
 *   x x
 * x 6 6 x
 * x 6 6 x
 *   x x
 * 最多2步可以从界外走到6这个位置的方案数
 * 邻接点在界外 -> +1，界内的话 -> +上一轮的状态
 * 反过来从界外走入内的方法
 * dp[i][j][k] = dp[i-1][j][k-1] + dp[i+1][j][k-1] + dp[i][j-1][k-1] + dp[i][j+1][k-1]
 * 本题特别注意更新新dp值得时候必须要使用上一轮的旧dp值，只有这一轮全更新完了才能全面覆盖上一轮的旧dp值，不能在旧dp里混进新的dp值
 * 另外注意从外向内走来倒推
 */
