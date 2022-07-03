package LC2101_2400;

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
}
