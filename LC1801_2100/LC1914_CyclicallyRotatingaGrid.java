package LC1801_2100;

public class LC1914_CyclicallyRotatingaGrid {
    /**
     * You are given an m x n integer matrix grid, where m and n are both even integers, and an integer k.
     *
     * The matrix is composed of several layers, which is shown in the below image, where each color is its own layer:
     *
     * A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. To cyclically rotate a
     * layer once, each element in the layer will take the place of the adjacent element in the counter-clockwise
     * direction. An example rotation is shown below:
     *
     * Return the matrix after applying k cyclic rotations to it.
     *
     * Input: grid = [[40,10],[30,20]], k = 1
     * Output: [[10,20],[40,30]]
     *
     * Input: grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]], k = 2
     * Output: [[3,4,8,12],[2,11,10,16],[1,7,6,15],[5,9,13,14]]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 50
     * Both m and n are even integers.
     * 1 <= grid[i][j] <= 5000
     * 1 <= k <= 10^9
     * @param grid
     * @param k
     * @return
     */
    // time = O(k * min(m, n) * (m + n)), space = O(1)
    public int[][] rotateGrid(int[][] grid, int k) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return null;

        int m = grid.length, n = grid[0].length;
        for (int offset = 0; offset < Math.min(m, n) / 2; offset++) { // O(min(m, n))
            int count = k % ((m - 2 * offset + n - 2 * offset - 2) * 2);
            for (int i = 0; i < count; i++) rotate(grid, offset); // O(k)
        }
        return grid;
    }

    private void rotate(int[][] grid, int offset) { // O(m + n)
        int m = grid.length, n = grid[0].length;

        int temp = grid[offset][offset];

        // top
        for (int j = offset; j < n - 1 - offset; j++) grid[offset][j] = grid[offset][j + 1];

        // right
        for (int i = offset; i < m - 1 - offset; i++) grid[i][n - 1 - offset] = grid[i + 1][n - 1 - offset];

        // bottom
        for (int j = n - 1 - offset; j > offset; j--) grid[m - 1 - offset][j] = grid[m - 1 - offset][j - 1];

        // left
        for (int i = m - 1 - offset; i > offset; i--) grid[i][offset] = grid[i - 1][offset];
        grid[offset + 1][offset] = temp;
    }
}
