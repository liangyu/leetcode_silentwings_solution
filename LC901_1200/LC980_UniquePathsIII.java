package LC901_1200;
import java.util.*;
public class LC980_UniquePathsIII {
    /**
     * You are given an m x n integer array grid where grid[i][j] could be:
     *
     * 1 representing the starting square. There is exactly one starting square.
     * 2 representing the ending square. There is exactly one ending square.
     * 0 representing empty squares we can walk over.
     * -1 representing obstacles that we cannot walk over.
     * Return the number of 4-directional walks from the starting square to the ending square, that walk over every
     * non-obstacle square exactly once.
     *
     * Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
     * Output: 2
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 20
     * 1 <= m * n <= 20
     * -1 <= grid[i][j] <= 2
     * There is exactly one starting cell and one ending cell.
     * @param grid
     * @return
     */
    // time = O(3^n), space = O(n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int res = 0;
    public int uniquePathsIII(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int count = 0, sx = -1, sy = -1, tx = -1, ty = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) count++;
                if (grid[i][j] == 1) {
                    sx = i;
                    sy = j;
                }
            }
        }
        boolean[][] visited = new boolean[m][n];
        visited[sx][sy] = true;
        dfs(grid, sx, sy, count, visited);
        return res;
    }

    private void dfs(int[][] grid, int x, int y, int count, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;

        for (int[] dir : directions) {
            int i = x + dir[0];
            int j = y + dir[1];
            if (i < 0 || i >= m || j < 0 || j >= n) continue;
            if (visited[i][j]) continue;
            if (grid[i][j] == -1) continue;
            if (grid[i][j] == 2 && count == 0) {
                res++;
                return;
            }
            visited[i][j] = true;
            dfs(grid, i, j, count - 1, visited);
            visited[i][j] = false;
        }
    }
}
