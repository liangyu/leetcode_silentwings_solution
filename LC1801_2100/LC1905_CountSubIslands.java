package LC1801_2100;
import java.util.*;
public class LC1905_CountSubIslands {
    /**
     * You are given two m x n binary matrices grid1 and grid2 containing only 0's (representing water) and 1's
     * (representing land). An island is a group of 1's connected 4-directionally (horizontal or vertical). Any cells
     * outside of the grid are considered water cells.
     *
     * An island in grid2 is considered a sub-island if there is an island in grid1 that contains all the cells that
     * make up this island in grid2.
     *
     * Return the number of islands in grid2 that are considered sub-islands.
     *
     * Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], grid2 = [[1,1,1,0,0],[0,0,1,1,1],
     * [0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid1.length == grid2.length
     * n == grid1[i].length == grid2[i].length
     * 1 <= m, n <= 500
     * grid1[i][j] and grid2[i][j] are either 0 or 1.
     * @param grid1
     * @param grid2
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid2.length, n = grid2[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) res += dfs(grid1, grid2, i, j);
            }
        }
        return res;
    }

    private int dfs(int[][] grid1, int[][] grid2, int i, int j) {
        int m = grid2.length, n = grid2[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n || grid2[i][j] == 0) return 1;

        grid2[i][j] = 0;
        int res = 1;
        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            res &= dfs(grid1, grid2, ii, jj);
        }
        return res & grid1[i][j];
    }
}
