package LC2101_2400;
import java.util.*;
public class LC2267_CheckifThereIsaValidParenthesesStringPath {
    /**
     * A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following
     * conditions is true:
     *
     * It is ().
     * It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
     * It can be written as (A), where A is a valid parentheses string.
     * You are given an m x n matrix of parentheses grid. A valid parentheses string path in the grid is a path
     * satisfying all of the following conditions:
     *
     * The path starts from the upper left cell (0, 0).
     * The path ends at the bottom-right cell (m - 1, n - 1).
     * The path only ever moves down or right.
     * The resulting parentheses string formed by the path is valid.
     * Return true if there exists a valid parentheses string path in the grid. Otherwise, return false.
     *
     * Input: grid = [["(","(","("],[")","(",")"],["(","(",")"],["(","(",")"]]
     * Output: true
     *
     * Input: grid = [[")",")"],["(","("]]
     * Output: false
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * grid[i][j] is either '(' or ')'.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n * (m + n))
    boolean[][][] dp;
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        dp = new boolean[m][n][2 * Math.max(m, n)];

        return dfs(grid, 0, 0, 0);
    }

    private boolean dfs(char[][] grid, int i, int j, int delta) {
        int m = grid.length, n = grid[0].length;
        delta += grid[i][j] == '(' ? 1 : -1;
        if (delta < 0) return false;
        if (dp[i][j][delta]) return false;
        if (i == m - 1 && j == n - 1 && delta == 0) return true;

        if (i < m - 1) {
            if (dfs(grid, i + 1, j , delta)) return true;
        }
        if (j < n - 1) {
            if (dfs(grid, i, j + 1, delta)) return true;
        }
        dp[i][j][delta] = true; // invalid path
        return false;
    }

    // S2:
    public boolean hasValidPath2(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        HashSet[][] dp = new HashSet[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = new HashSet<Integer>();
            }
        }

        if (grid[0][0] == '(') dp[0][0].add(1);
        // else -> empty set, not valid solution!!!

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int k = grid[i][j] == '(' ? 1 : -1;
                if (i >= 1) {
                    HashSet<Integer> set = dp[i - 1][j];
                    for (int x : set) {
                        if (x + k >= 0 && (m + n - 1) - (i + j + 1) >= x + k) dp[i][j].add(x + k);
                    }
                }

                if (j >= 1) {
                    HashSet<Integer> set = dp[i][j - 1];
                    for (int x : set) {
                        if (x + k >= 0 && (m + n - 1) - (i + j + 1) >= x + k) dp[i][j].add(x + k);
                    }
                }
            }
        }
        return dp[m - 1][n - 1].contains(0);
    }

    // S3: 3D dp
    // time = O(m * n * (m + n) / 2), space = O(m * n * (m + n) / 2);
    public boolean hasValidPath3(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][][] dp = new boolean[101][101][101];
        if (grid[0][0] == '(') dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k <= 100; k++) {
                    if (grid[i][j] == '(') {
                        if (i >= 1 && k >= 1) {
                            dp[i][j][k] |= dp[i - 1][j][k - 1];
                        }
                        if (j >= 1 && k >= 1) {
                            dp[i][j][k] |= dp[i][j - 1][k - 1];
                        }
                    } else {
                        if (i >= 1 && k + 1 <= 100) {
                            dp[i][j][k] |= dp[i - 1][j][k + 1];
                        }
                        if (j >= 1 && k + 1 <= 100) {
                            dp[i][j][k] |= dp[i][j - 1][k + 1];
                        }
                    }
                }
            }
        }
        return dp[m - 1][n - 1][0];
    }
}
/**
 * 左上到右下，最常规的dp问题
 * 迷宫型dp
 * dp[i][j] = dp[i-1][j], dp[i][j-1]
 * 1. in any prefix string: left parentheses >= right parentheses
 * 2. by the end of the string: left parentheses == right parentheses
 * count => # of unmatched parentheses
 * starting from (0,0) to (i,j): set of {# of unmatched left parentheses}
 * (i-1,j) => {0,2,3}
 * (i,j) ='(' => dp[i][j] = {1,3,4}
 * (i,j) =')' => dp[i][j] = {1,2}
 * dp[i][j-1] =>
 * return dp[m-1][n-1] contains 0
 * time = O(m * n * # of set element) => O(m * n * (m + n - 1)) = O(10^6)
 * dp[i][j][k]: (0,0) => (i,j) with k unmatched left parentheses
 * for (int i = 0; i < m; i++) {
 *     for (int j = 0; j < n; j++) {
 *         for (int k = 0; k <= 100; k++) {
 *             if (grid[i][j] == '(') {
 *                 dp[i][j][k] = dp[i-1][j][k-1] || dp[i][j-1][k-1];
 *             } else {
 *                 dp[i][j][k] = dp[i-1][j][k+1] || dp[i][j-1][k+1]
 *             }
 *         }
 *     }
 * }
 * return dp[m-1][n-1][0]
 *
 */