package LC2101_2400;

public class LC2245_MaximumTrailingZerosinaCorneredPath {
    /**
     * You are given a 2D integer array grid of size m x n, where each cell contains a positive integer.
     *
     * A cornered path is defined as a set of adjacent cells with at most one turn. More specifically, the path should
     * exclusively move either horizontally or vertically up to the turn (if there is one), without returning to a
     * previously visited cell. After the turn, the path will then move exclusively in the alternate direction:
     * move vertically if it moved horizontally, and vice versa, also without returning to a previously visited cell.
     *
     * The product of a path is defined as the product of all the values in the path.
     *
     * Return the maximum number of trailing zeros in the product of a cornered path found in grid.
     *
     * Note:
     *
     * Horizontal movement means moving in either the left or right direction.
     * Vertical movement means moving in either the up or down direction.
     *
     * Input: grid = [[23,17,15,3,20],[8,1,20,27,11],[9,4,6,2,21],[40,9,1,10,6],[22,7,4,5,3]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 1 <= grid[i][j] <= 1000
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int maxTrailingZeros(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] self2 = new int[m][n];
        int[][] self5 = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = grid[i][j];
                while (x % 2 == 0) {
                    self2[i][j]++;
                    x /= 2;
                }
                while (x % 5 == 0) {
                    self5[i][j]++;
                    x /= 5;
                }
            }
        }

        int[][] left2 = new int[m][n];
        int[][] up2 = new int[m][n];
        int[][] right2 = new int[m][n];
        int[][] down2 = new int[m][n];
        int[][] left5 = new int[m][n];
        int[][] right5 = new int[m][n];
        int[][] down5 = new int[m][n];
        int[][] up5 = new int[m][n];


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                left2[i][j] = (j == 0 ? 0 : left2[i][j - 1]) + self2[i][j];
                left5[i][j] = (j == 0 ? 0 : left5[i][j - 1]) + self5[i][j];
            }
            for (int j = n - 1; j >= 0; j--) {
                right2[i][j] = (j == n - 1 ? 0 : right2[i][j + 1]) + self2[i][j];
                right5[i][j] = (j == n - 1 ? 0 : right5[i][j + 1]) + self5[i][j];
            }
        }

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                up2[i][j] = (i == 0 ? 0 : up2[i - 1][j]) + self2[i][j];
                up5[i][j] = (i == 0 ? 0 : up5[i - 1][j]) + self5[i][j];
            }

            for (int i = m - 1; i >= 0; i--) {
                down2[i][j] = (i == m - 1 ? 0 : down2[i + 1][j]) + self2[i][j];
                down5[i][j] = (i == m - 1 ? 0 : down5[i + 1][j]) + self5[i][j];
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, Math.min(left2[i][j] + up2[i][j] - self2[i][j], left5[i][j] + up5[i][j] - self5[i][j]));
                res = Math.max(res, Math.min(right2[i][j] + up2[i][j] - self2[i][j], right5[i][j] + up5[i][j] - self5[i][j]));
                res = Math.max(res, Math.min(right2[i][j] + down2[i][j] - self2[i][j], right5[i][j] + down5[i][j] - self5[i][j]));
                res = Math.max(res, Math.min(left2[i][j] + down2[i][j] - self2[i][j], left5[i][j] + down5[i][j] - self5[i][j]));
            }
        }
        return res;
    }
}
/**
 * 首先，作为常识，Trailing zero的个数只与因子2和因子5的个数有关。
 * 对于任何类型的cornered path，他们都有“拐点”。
 * 一路走到底
 * 全局最优？ "前缀和"的思想
 * up2(i,j) + right2(i,j) - self2(i,j), up5(i,j) + right5(i,j) - self5(i,j)
 */