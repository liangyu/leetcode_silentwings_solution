package LC2101_2400;

public class LC2174_RemoveAllOnesWithRowandColumnFlipsII {
    /**
     * You are given a 0-indexed m x n binary matrix grid.
     *
     * In one operation, you can choose any i and j that meet the following conditions:
     *
     * 0 <= i < m
     * 0 <= j < n
     * grid[i][j] == 1
     * and change the values of all cells in row i and column j to zero.
     *
     * Return the minimum number of operations needed to remove all 1's from grid.
     *
     * Input: grid = [[1,1,1],[1,1,1],[0,1,0]]
     * Output: 2
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 15
     * 1 <= m * n <= 15
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int removeOnes(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int[][] temp = new int[m][n];
                    for (int k = 0; k < m; k++) temp[k] = grid[k].clone();
                    flip(temp, i, j);
                    res = Math.min(res, 1 + removeOnes(temp));
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    private void flip(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        for (int k = 0; k < n; k++) grid[i][k] = 0;
        for (int k = 0; k < m; k++) grid[k][j] = 0;
    }
}
