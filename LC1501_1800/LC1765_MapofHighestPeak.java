package LC1501_1800;
import java.util.*;
public class LC1765_MapofHighestPeak {
    /**
     * You are given an integer matrix isWater of size m x n that represents a map of land and water cells.
     *
     * If isWater[i][j] == 0, cell (i, j) is a land cell.
     * If isWater[i][j] == 1, cell (i, j) is a water cell.
     * You must assign each cell a height in a way that follows these rules:
     *
     * The height of each cell must be non-negative.
     * If the cell is a water cell, its height must be 0.
     * Any two adjacent cells must have an absolute height difference of at most 1. A cell is adjacent to another cell
     * if the former is directly north, east, south, or west of the latter (i.e., their sides are touching).
     * Find an assignment of heights such that the maximum height in the matrix is maximized.
     *
     * Return an integer matrix height of size m x n where height[i][j] is cell (i, j)'s height. If there are multiple
     * solutions, return any of them.
     *
     * Input: isWater = [[0,1],[0,0]]
     * Output: [[1,0],[2,1]]
     *
     * Input: isWater = [[0,0,1],[1,0,0],[0,0,0]]
     * Output: [[1,1,0],[0,1,1],[1,2,2]]
     *
     * Constraints:
     *
     * m == isWater.length
     * n == isWater[i].length
     * 1 <= m, n <= 1000
     * isWater[i][j] is 0 or 1.
     * There is at least one water cell.
     * @param isWater
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length, n = isWater[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    isWater[i][j] = -1;
                }
            }
        }

        int res = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];

                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (isWater[i][j] != 0) continue;
                    isWater[i][j] = (isWater[x][y] == -1 ? 0 : isWater[x][y]) + 1;
                    res = Math.max(res, isWater[i][j]);
                    queue.offer(new int[]{i, j});
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == -1) isWater[i][j] = 0;
            }
        }
        return isWater;
    }
}
