package LC001_300;
import java.util.*;
public class LC200_NumberofIslands {
    /**
     * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of
     * islands.
     *
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may
     * assume all four edges of the grid are all surrounded by water.
     *
     * Input: grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * Output: 1
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] is '0' or '1'.
     * @param grid
     * @return
     */
    // S1: dfs
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int numIslands(char[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length, count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        // base case - fail
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == '0') return;

        grid[i][j] = '0';
        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            dfs(grid, ii, jj);
        }
    }

    // S2: BFS
    // time = O(m * n), space = O(m * n)
    public int numIslands2(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != '1') continue;
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i * n + j);
                grid[i][j] = 0;

                while (!queue.isEmpty()) {
                    int cur = queue.poll();
                    int x = cur / n, y = cur % n;
                    for (int[] dir : DIRECTIONS) {
                        int ii = x + dir[0];
                        int jj = y + dir[1];
                        if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                        if (grid[ii][jj] != '1') continue;
                        queue.offer(ii * n + jj);
                        grid[ii][jj] = 0;
                    }
                }
                count++;
            }
        }
        return count;
    }
}