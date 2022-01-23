package LC2100_2400;
import java.util.*;
public class LC2123_MinimumOperationstoRemoveAdjacentOnesinMatrix {
    /**
     * You are given a 0-indexed binary matrix grid. In one operation, you can flip any 1 in grid to be 0.
     *
     * A binary matrix is well-isolated if there is no 1 in the matrix that is 4-directionally connected (i.e.,
     * horizontal and vertical) to another 1.
     *
     * Return the minimum number of operations to make grid well-isolated.
     *
     * Input: grid = [[1,1,0],[0,1,1],[1,1,1]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    int[][] match, vis, grid;
    HashMap<String, Boolean> memo;
    int m, n;
    public int minimumOperations(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        memo = new HashMap<>();
        this.grid = grid;
        int count = 0;
        match = new int[m][n];
        vis = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(match[i], -1);
        for (int i = 0; i < m; i++) Arrays.fill(vis[i], -1);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && match[i][j] == -1) {
                    if (dfs(i, j, vis[i][j] = i * n + j)) count++;
                }
            }
        }
        return count;
    }

    private boolean dfs(int i, int j, int v) {
        String key = i + "#" + j + "#" + v;
        if (memo.containsKey(key)) return memo.get(key);

        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n) continue;
            if (grid[x][y] == 0) continue;
            if (vis[x][y] == v) continue;
            vis[x][y] = v;
            if (match[x][y] == -1 || dfs(match[x][y] / n, match[x][y] % n, v)) {
                match[x][y] = i * n + j;
                match[i][j] = x * n + y;
                memo.put(key, true);
                return true;
            }
        }
        memo.put(key, false);
        return false;
    }
}
