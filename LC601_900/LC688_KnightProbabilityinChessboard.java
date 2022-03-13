package LC601_900;
import java.util.*;
public class LC688_KnightProbabilityinChessboard {
    /**
     * On an n x n chessboard, a knight starts at the cell (row, column) and attempts to make exactly k moves. The rows
     * and columns are 0-indexed, so the top-left cell is (0, 0), and the bottom-right cell is (n - 1, n - 1).
     *
     * A chess knight has eight possible moves it can make, as illustrated below. Each move is two cells in a cardinal
     * direction, then one cell in an orthogonal direction.
     *
     * Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece
     * would go off the chessboard) and moves there.
     *
     * The knight continues moving until it has made exactly k moves or has moved off the chessboard.
     *
     * Return the probability that the knight remains on the board after it has stopped moving.
     *
     * Input: n = 3, k = 2, row = 0, column = 0
     * Output: 0.06250
     *
     * Constraints:
     *
     * 1 <= n <= 25
     * 0 <= k <= 100
     * 0 <= row, column <= n
     * @param n
     * @param k
     * @param row
     * @param column
     * @return
     */
    // S1: dfs + memo
    // time = O(n^2*k), space = O(n^2 * k)
    private int[][] directions = new int[][]{{-2, 1}, {-2, -1}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {1, -2}, {1, 2}};
    public double knightProbability(int n, int k, int row, int column) {
        double[][][] memo = new double[n][n][k + 1];
        return dfs(n, k, row, column, memo);
    }

    private double dfs(int n, int k, int i, int j, double[][][] memo) {
        // base case
        if (i < 0 || i >= n || j < 0 || j >= n) return 0;
        if (k == 0) return 1;
        if (memo[i][j][k] != 0) return memo[i][j][k];

        double res = 0;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            res += 1.0 / 8 * dfs(n, k - 1, x, y, memo);
        }
        memo[i][j][k] = res;
        return res;
    }

    // S2: dp
    public double knightProbability2(int n, int k, int row, int column) {
        double[][][] dp = new double[n][n][k + 1];
        // init
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int d = 1; d <= k; d++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int[] dir : directions) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if (x < 0 || x >= n || y < 0 || y >= n) continue;
                        dp[i][j][d] += dp[x][y][d - 1] / 8;
                    }
                }
            }
        }
        return dp[row][column][k];
    }
}
