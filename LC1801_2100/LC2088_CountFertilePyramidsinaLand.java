package LC1801_2100;

public class LC2088_CountFertilePyramidsinaLand {
    /**
     * A farmer has a rectangular grid of land with m rows and n columns that can be divided into unit cells. Each cell
     * is either fertile (represented by a 1) or barren (represented by a 0). All cells outside the grid are considered
     * barren.
     *
     * A pyramidal plot of land can be defined as a set of cells with the following criteria:
     *
     * The number of cells in the set has to be greater than 1 and all cells must be fertile.
     * The apex of a pyramid is the topmost cell of the pyramid. The height of a pyramid is the number of rows it
     * covers. Let (r, c) be the apex of the pyramid, and its height be h. Then, the plot comprises of cells (i, j)
     * where r <= i <= r + h - 1 and c - (i - r) <= j <= c + (i - r).
     * An inverse pyramidal plot of land can be defined as a set of cells with similar criteria:
     *
     * The number of cells in the set has to be greater than 1 and all cells must be fertile.
     * The apex of an inverse pyramid is the bottommost cell of the inverse pyramid. The height of an inverse pyramid is
     * the number of rows it covers. Let (r, c) be the apex of the pyramid, and its height be h. Then, the plot
     * comprises of cells (i, j) where r - h + 1 <= i <= r and c - (r - i) <= j <= c + (r - i).
     * Some examples of valid and invalid pyramidal (and inverse pyramidal) plots are shown below. Black cells indicate
     * fertile cells.
     *
     * Given a 0-indexed m x n binary matrix grid representing the farmland, return the total number of pyramidal and
     * inverse pyramidal plots that can be found in grid.
     *
     * Input: grid = [[0,1,1,0],[1,1,1,1]]
     * Output: 2
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 1000
     * 1 <= m * n <= 10^5
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int countPyramids(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] left = new int[m][n];
        int[][] right = new int[m][n];
        int[][] dp1 = new int[m][n];
        int[][] dp2 = new int[m][n];

        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) count++;
                else count = 0;
                left[i][j] = count;
            }
        }
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) count++;
                else count = 0;
                right[i][j] = count;
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                if (i == 0) {
                    if (grid[i][j] == 1) dp1[i][j] = 1;
                } else {
                    dp1[i][j] = Math.min(dp1[i - 1][j] + 1, Math.min(left[i][j], right[i][j]));
                }
                dp1[i][j] = Math.max(dp1[i][j], 1);
                res += Math.max(0, dp1[i][j] - 1);
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                if (i == m - 1) {
                    if (grid[i][j] == 1) dp2[i][j] = 1;
                } else {
                    dp2[i][j] = Math.min(dp2[i + 1][j] + 1, Math.min(left[i][j], right[i][j]));
                }
                dp2[i][j] = Math.max(dp2[i][j], 1);
                res += Math.max(0, dp2[i][j] - 1);
            }
        }
        return res;
    }
}
