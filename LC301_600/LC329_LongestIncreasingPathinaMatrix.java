package LC301_600;
import java.util.*;
public class LC329_LongestIncreasingPathinaMatrix {
    /**
     * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
     *
     * From each cell, you can either move in four directions: left, right, up, or down. You may not move diagonally or
     * move outside the boundary (i.e., wrap-around is not allowed).
     *
     * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
     * Output: 4
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 200
     * 0 <= matrix[i][j] <= 2^31 - 1
     * @param matrix
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int longestIncreasingPath(int[][] matrix) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;
        int res = 0;
        int[][] mem = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int max = dfs(matrix, null, i, j, mem);
                res = Math.max(max, res);
            }
        }
        return res;
    }

    private int dfs(int[][] matrix, Integer preVal, int i, int j, int[][] mem) {
        int m = matrix.length, n = matrix[0].length;
        // base case - fail
        if (i < 0 || i >= m || j < 0 || j >= n || preVal != null && matrix[i][j] <= preVal) return 0;

        if (mem[i][j] != 0) return mem[i][j];

        preVal = matrix[i][j];
        int max = 0;
        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            max = Math.max(max, dfs(matrix, preVal, ii, jj, mem));
        }
        mem[i][j] = max + 1;
        return mem[i][j];
    }
}
