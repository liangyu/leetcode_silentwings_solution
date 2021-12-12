package LC901_1200;

public class LC1034_ColoringABorder {
    /**
     * You are given an m x n integer matrix grid, and three integers row, col, and color. Each value in the grid
     * represents the color of the grid square at that location.
     *
     * Two squares belong to the same connected component if they have the same color and are next to each other in
     * any of the 4 directions.
     *
     * The border of a connected component is all the squares in the connected component that are either 4-directionally
     * adjacent to a square not in the component, or on the boundary of the grid (the first or last row or column).
     *
     * You should color the border of the connected component that contains the square grid[row][col] with color.
     *
     * Return the final grid.
     *
     * Input: grid = [[1,1],[1,2]], row = 0, col = 0, color = 3
     * Output: [[3,3],[3,2]]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 50
     * 1 <= grid[i][j], color <= 1000
     * 0 <= row < m
     * 0 <= col < n
     * @param grid
     * @param row
     * @param col
     * @param color
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int m, n;
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        m = grid.length;
        n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        if (grid[row][col] == color) return grid;
        dfs(grid, row, col, grid[row][col], color, visited);
        return grid;
    }

    private void dfs(int[][] grid, int i, int j, int k, int color, boolean[][] visited) {
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n) return;
        if (grid[i][j] != k) return;
        if (visited[i][j]) return;

        visited[i][j] = true;
        boolean isBorder = false;
        if (i == 0 || i == m - 1 || j == 0 || j == n - 1) isBorder = true;
        if (i + 1 < m && grid[i + 1][j] != k) isBorder = true;
        if (i - 1 >= 0 && grid[i - 1][j] != k) isBorder = true;
        if (j + 1 < n && grid[i][j + 1] != k) isBorder = true;
        if (j - 1 >= 0 && grid[i][j - 1] != k) isBorder = true;

        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            dfs(grid, x, y, k, color, visited);
        }
        if (isBorder) grid[i][j] = color;
    }
}
