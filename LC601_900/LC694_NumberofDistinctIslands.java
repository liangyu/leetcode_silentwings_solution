package LC601_900;
import java.util.*;
public class LC694_NumberofDistinctIslands {
    /**
     * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected
     * 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
     *
     * Count the number of distinct islands. An island is considered to be the same as another if and only if one
     * island can be translated (and not rotated or reflected) to equal the other.
     *
     * 11000
     * 11000
     * 00011
     * 00011
     * Given the above grid map, return 1.
     *
     * Note: The length of each dimension in the given grid does not exceed 50.
     *
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int numDistinctIslands(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int row = grid.length, col = grid[0].length;
        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder path = new StringBuilder();
                    dfs(grid, i, j, path, 's');
                    set.add(path.toString());
                }
            }
        }
        return set.size();
    }

    private void dfs(int[][] grid, int i, int j, StringBuilder path, char dir) {
        int row = grid.length, col = grid[0].length;
        // base case - fail
        if (i < 0 || i >= row || j < 0 || j >= col || grid[i][j] == 0) return;

        grid[i][j] = 0; // 走过的地方设置为0，或者用boolan[][] visited来mark
        path.append(dir);
        dfs(grid, i - 1, j, path, 'u');
        dfs(grid, i, j + 1, path, 'r');
        dfs(grid, i + 1, j, path, 'd');
        dfs(grid, i, j - 1, path, 'l');
        path.append('b'); // 别忘了每次换方向要记录拐点的位置
    }

    // S1.2: dfs
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int numDistinctIslands2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder path = new StringBuilder();
                    dfs2(grid, i, j, path, -1); // start has no direction, set as -1
                    set.add(path.toString());
                }
            }
        }
        return set.size();
    }

    private void dfs2(int[][] grid, int i, int j, StringBuilder path, int dir) {
        int m = grid.length, n = grid[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == 0) return;

        grid[i][j] = 0;
        path.append(dir);
        for (int k = 0; k < 4; k++) {
            dfs2(grid, i + directions[k][0], j + directions[k][1], path, k);
        }
        path.append(4); // setback -> turn back
    }
}
/**
 * "down"->"left" is not same as "down"->deadend->"back"->"left".
 */
