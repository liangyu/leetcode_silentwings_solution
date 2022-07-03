package LC2101_2400;
import java.util.*;
public class LC2328_NumberofIncreasingPathsinaGrid {
    /**
     * You are given an m x n integer matrix grid, where you can move from a cell to any adjacent cell in all 4 directions.
     *
     * Return the number of strictly increasing paths in the grid such that you can start from any cell and end at any
     * cell. Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Two paths are considered different if they do not have exactly the same sequence of visited cells.
     *
     * Input: grid = [[1,1],[3,4]]
     * Output: 8
     *
     * Input: grid = [[1],[2]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 1000
     * 1 <= m * n <= 10^5
     * 1 <= grid[i][j] <= 10^5
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    int[][] grid;
    int m, n;
    long[][] f;
    long M = (long)(1e9 + 7);
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int countPaths(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        f = new long[m][n];
        long res = 0;
        for (int i = 0; i < m; i++) Arrays.fill(f[i], -1);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = (res + dfs(i, j)) % M;
            }
        }
        return (int) res;
    }

    private long dfs(int i, int j) {
        if (f[i][j] != -1) return f[i][j];

        f[i][j] = 1;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n) continue;
            if (grid[x][y] <= grid[i][j]) continue;
            f[i][j] = (f[i][j] + dfs(x, y)) % M;
        }
        return f[i][j];
    }
}
/**
 * dp
 * 状态表示 f(i,j)
 * 1. 集合：所有以(i,j)为起点的路线的集合
 * 2. 属性；集合中的元素数量
 *
 * 状态计算 —— 集合的划分
 * 5大类：不走 上 右 下 左
 * 拓扑序 -> 严格递增来走，所以不存在环 => 一定不重复
 */