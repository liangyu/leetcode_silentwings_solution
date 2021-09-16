package LC601_900;
import java.util.*;
public class LC764_LargestPlusSign {
    /**
     * You are given an integer n. You have an n x n binary grid grid with all values initially 1's except for some
     * indices given in the array mines. The ith element of the array mines is defined as mines[i] = [xi, yi] where
     * grid[xi][yi] == 0.
     *
     * Return the order of the largest axis-aligned plus sign of 1's contained in grid. If there is none, return 0.
     *
     * An axis-aligned plus sign of 1's of order k has some center grid[r][c] == 1 along with four arms of length k - 1
     * going up, down, left, and right, and made of 1's. Note that there could be 0's or 1's beyond the arms of the
     * plus sign, only the relevant area of the plus sign is checked for 1's.
     *
     * Input: n = 5, mines = [[4,2]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 500
     * 1 <= mines.length <= 5000
     * 0 <= xi, yi < n
     * All the pairs (xi, yi) are unique.
     * @param n
     * @param mines
     * @return
     */
    // S1: brute-force
    // time = O(n^3), space = O(n^2)
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
        for (int[] m : mines) grid[m[0]][m[1]] = 0;

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int count = 1, dir = 1;
                    while (j - dir >= 0 && j + dir < n && i - dir >= 0 && i + dir < n && grid[i][j - dir] == 1 && grid[i][j + dir] == 1 && grid[i - dir][j] == 1 && grid[i + dir][j] == 1) {
                        count++;
                        dir++;
                    }
                    res = Math.max(count, res);
                }
            }
        }
        return res;
    }

    // S2: DP
    // time = O(n^2), space = O(n^2)
    public int orderOfLargestPlusSign2(int n, int[][] mines) {
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(grid[i], n);
        for (int[] m : mines) grid[m[0]][m[1]] = 0;

        for (int i = 0; i < n; i++) {
            int l = 0, r = 0, u = 0, d = 0, k = n - 1;
            for (int j = 0; j < n; j++) {
                grid[i][j] = Math.min(grid[i][j], l = (grid[i][j] == 0 ? 0 : l + 1));
                grid[i][k] = Math.min(grid[i][k], r = (grid[i][k] == 0 ? 0 : r + 1));
                grid[j][i] = Math.min(grid[j][i], u = (grid[j][i] == 0 ? 0 : u + 1));
                grid[k][i] = Math.min(grid[k][i], d = (grid[k][i] == 0 ? 0 : d + 1));
                k--;
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, grid[i][j]);
            }
        }
        return res;
    }
}
