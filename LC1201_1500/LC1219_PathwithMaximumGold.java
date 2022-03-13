package LC1201_1500;
import java.util.*;
public class LC1219_PathwithMaximumGold {
    /**
     * In a gold mine grid of size m x n, each cell in this mine has an integer representing the amount of gold in that
     * cell, 0 if it is empty.
     *
     * Return the maximum amount of gold you can collect under the conditions:
     *
     * Every time you are located in a cell you will collect all the gold in that cell.
     * From your position, you can walk one step to the left, right, up, or down.
     * You can't visit the same cell more than once.
     * Never visit a cell with 0 gold.
     * You can start and stop collecting gold from any position in the grid that has some gold.
     *
     * Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
     * Output: 24
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 15
     * 0 <= grid[i][j] <= 100
     * There are at most 25 cells containing gold.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    int res = 0;
    public int getMaximumGold(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    boolean[][] visited = new boolean[m][n];
                    dfs(grid, i, j, grid[i][j], visited);
                }
            }
        }
        return res;
    }

    private void dfs(int[][] grid, int i, int j, int sum, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;

        visited[i][j] = true;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n) continue;
            if (grid[i][j] == 0 || visited[i][j]) continue;
            dfs(grid, x, y, sum + grid[x][y], visited);
        }
        res = Math.max(res, sum);
        visited[i][j] = false;
    }
}
