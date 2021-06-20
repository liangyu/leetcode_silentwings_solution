package LC1801_2100;
import java.util.*;
public class LC1895_LargestMagicSquare {
    /**
     * A k x k magic square is a k x k grid filled with integers such that every row sum, every column sum, and both
     * diagonal sums are all equal. The integers in the magic square do not have to be distinct. Every 1 x 1 grid is
     * trivially a magic square.
     *
     * Given an m x n integer grid, return the size (i.e., the side length k) of the largest magic square that can be
     * found within this grid.
     *
     * Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 50
     * 1 <= grid[i][j] <= 10^6
     * @param grid
     * @return
     */
    // S1: brute-force
    // time = O(m^2 * n^2), space = O(1)
    public int largestMagicSquare(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int res = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, helper(grid, i, j, Math.min(m - i, n - j)));
            }
        }
        return res;
    }

    private int helper(int[][] grid, int x, int y, int len) {
        for (int k = len; k > 1; k--) {
            int sum = 0;
            boolean flag = true;

            for (int j = 0; j < k; j++) {
                sum += grid[x][y + j];
            }

            // calculate row
            for (int i = 0; i < k; i++) {
                int rs = 0;
                for (int j = 0; j < k; j++) {
                    rs += grid[x + i][y + j];
                }
                if (rs != sum) {
                    flag = false;
                    break;
                }
            }
            if (!flag) continue;

            // calculate col
            for (int j = 0; j < k; j++) {
                int cs = 0;
                for (int i = 0; i < k; i++) {
                    cs += grid[x + i][y + j];
                }
                if (cs != sum) {
                    flag = false;
                    break;
                }
            }
            if (!flag) continue;

            // calculate diagonal
            int ds = 0;
            for (int i = 0; i < k; i++) {
                ds += grid[x + i][y + i];
            }
            if (ds != sum) continue;

            // calculate anti-diagonal
            int as = 0;
            for (int i = 0; i < k; i++) {
                as += grid[x + i][y + k - 1 - i];
            }
            if (as != sum) continue;

            return k;
        }
        return 1;
    }
}
