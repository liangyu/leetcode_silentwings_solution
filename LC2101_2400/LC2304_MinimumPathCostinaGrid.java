package LC2101_2400;
import java.util.*;
public class LC2304_MinimumPathCostinaGrid {
    /**
     * You are given a 0-indexed m x n integer matrix grid consisting of distinct integers from 0 to m * n - 1. You can
     * move in this matrix from a cell to any other cell in the next row. That is, if you are in cell (x, y) such that
     * x < m - 1, you can move to any of the cells (x + 1, 0), (x + 1, 1), ..., (x + 1, n - 1). Note that it is not
     * possible to move from cells in the last row.
     *
     * Each possible move has a cost given by a 0-indexed 2D array moveCost of size (m * n) x n, where moveCost[i][j]
     * is the cost of moving from a cell with value i to a cell in column j of the next row. The cost of moving from
     * cells in the last row of grid can be ignored.
     *
     * The cost of a path in grid is the sum of all values of cells visited plus the sum of costs of all the moves made.
     * Return the minimum cost of a path that starts from any cell in the first row and ends at any cell in the last row.
     *
     * Input: grid = [[5,3],[4,0],[2,1]], moveCost = [[9,8],[1,5],[10,12],[18,6],[2,4],[14,3]]
     * Output: 17
     *
     * Input: grid = [[5,1,2],[4,0,3]], moveCost = [[12,10,15],[20,23,8],[21,7,1],[8,1,13],[9,10,25],[5,3,2]]
     * Output: 6
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 50
     * grid consists of distinct integers from 0 to m * n - 1.
     * moveCost.length == m * n
     * moveCost[i].length == n
     * 1 <= moveCost[i][j] <= 100
     * @param grid
     * @param moveCost
     * @return
     */
    // S1: dp
    // time = O(m * n^2), space = O(m * n)
    public int minPathCost(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;
        int[][] f = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(f[i], Integer.MAX_VALUE);
        for (int j = 0; j < n; j++) f[0][j] = grid[0][j];
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    f[i][j] = Math.min(f[i][j], f[i - 1][k] + moveCost[grid[i - 1][k]][j] + grid[i][j]);
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) res = Math.min(res, f[m - 1][j]);
        return res;
    }

    // S2: PQ
    // time = O(n^2 * logn), space = O(m * n)
    public int minPathCost2(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);
        for (int j = 0; j < n; j++) {
            pq.offer(new int[]{grid[0][j], grid[0][j], 0});
            visited[0][j] = grid[0][j];
        }

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int sum = cur[0], val = cur[1], level = cur[2];
            if (level == m - 1) return sum;

            for (int j = 0; j < moveCost[val].length; j++) {
                int nextSum = sum + moveCost[val][j] + grid[level + 1][j];
                if (visited[level + 1][j] > nextSum) {
                    visited[level + 1][j] = nextSum;
                    pq.offer(new int[]{nextSum, grid[level + 1][j], level + 1});
                }
            }
        }
        return -1;
    }
}
/**
 * 类似数字三角形dp
 * f(i, j) = f(i - 1, k) + cost
 * 枚举下是从上一层哪个格子走过来的
 */