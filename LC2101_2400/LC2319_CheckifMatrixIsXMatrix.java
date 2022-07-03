package LC2101_2400;

public class LC2319_CheckifMatrixIsXMatrix {
    /**
     * A square matrix is said to be an X-Matrix if both of the following conditions hold:
     *
     * All the elements in the diagonals of the matrix are non-zero.
     * All other elements are 0.
     * Given a 2D integer array grid of size n x n representing a square matrix, return true if grid is an X-Matrix.
     * Otherwise, return false.
     *
     * Input: grid = [[2,0,0,1],[0,3,1,0],[0,5,2,0],[4,0,0,2]]
     * Output: true
     *
     * Constraints:
     *
     * n == grid.length == grid[i].length
     * 3 <= n <= 100
     * 0 <= grid[i][j] <= 10^5
     * @param grid
     * @return
     */
    // time = O(n^2), space = O(1)
    public boolean checkXMatrix(int[][] grid) {
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || i + j == n - 1) {
                    if (grid[i][j] == 0) return false;
                } else {
                    if (grid[i][j] != 0) return false;
                }
            }
        }
        return true;
    }
}
