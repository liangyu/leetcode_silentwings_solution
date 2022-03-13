package LC901_1200;

public class LC1020_NumberofEnclaves {
    /**
     * You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
     *
     * A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the
     * boundary of the grid.
     *
     * Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of
     * moves.
     *
     * Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 500
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    // S1: 正向dfs
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    boolean flag;
    public int numEnclaves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int res = 0;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    flag = true;
                    int val = dfs(grid, i, j, visited);
                    if (flag) res += val;
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int i, int j,  boolean[][] visited) {
        int m = grid.length, n = grid[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n) {
            flag = false;
            return 0;
        }
        if (grid[i][j] == 0 || visited[i][j]) return 0;

        visited[i][j] = true;
        int count = 1;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            count += dfs(grid, x, y, visited);
        }
        return count;
    }

    // S2: 反向dfs (optimal solution!!!)
    // time = O(m * n), space = O(m * n)
    public int numEnclaves2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int res = 0;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    if (grid[i][j] == 1) dfs(grid, i, j);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) res++;
            }
        }
        return res;
    }

    private void dfs(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n) return;
        if (grid[i][j] == 0) return;

        grid[i][j] = 0;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            dfs(grid, x, y);
        }
    }
}
