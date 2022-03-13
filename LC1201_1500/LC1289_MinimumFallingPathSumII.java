package LC1201_1500;
import java.util.*;
public class LC1289_MinimumFallingPathSumII {
    /**
     * Given an n x n integer matrix grid, return the minimum sum of a falling path with non-zero shifts.
     *
     * A falling path with non-zero shifts is a choice of exactly one element from each row of grid such that no two
     * elements chosen in adjacent rows are in the same column.
     *
     * Input: arr = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: 13
     *
     * Constraints:
     *
     * n == grid.length == grid[i].length
     * 1 <= n <= 200
     * -99 <= grid[i][j] <= 99
     * @param grid
     * @return
     */
    // S1: DP
    // time = O(n^2 * logn), space = O(n^2)
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int j = 0; j < n; j++) dp[0][j] = grid[0][j]; // O(n)

        for (int i = 1; i < n; i++) { // O(n)
            List<int[]> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) { // O(n)
                temp.add(new int[]{dp[i - 1][j], j});
            }
            Collections.sort(temp, (o1, o2) -> o1[0] - o2[0]); // O(nlogn)

            for (int j = 0; j < n; j++) { // O(n)
                if (j == temp.get(0)[1]) {
                    dp[i][j] = temp.get(1)[0] + grid[i][j];
                } else {
                    dp[i][j] = temp.get(0)[0] + grid[i][j];
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            res = Math.min(res, dp[n - 1][j]);
        }
        return res;
    }

    // S2: DP
    // time = O(n^2), space = O(1)
    public int minFallingPathSum2(int[][] grid) {
        int n = grid.length;
        int min1 = -1, min2 = -1;

        for (int i = 0; i < n; i++) {
            // make a copy of last min1, min2
            int last1 = min1, last2 = min2;
            // reset min1, min2 for this new round i
            min1 = -1;
            min2 = -1;

            for (int j = 0; j < n; j++) {
                if (j != last1) {  // no conflict with the minimum index of last row
                    grid[i][j] += last1 < 0 ? 0 : grid[i - 1][last1];
                } else {
                    grid[i][j] += last2 < 0 ? 0 : grid[i - 1][last2];
                }

                // update min1 and min2
                if (min1 < 0 || grid[i][j] < grid[i][min1]) {
                    min2 = min1;
                    min1 = j;
                } else if (min2 < 0 || grid[i][j] < grid[i][min2]) {
                    min2 = j;
                }
            }
        }
        return grid[n - 1][min1];
    }
}
/**
 * same as LC265
 */