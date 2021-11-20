package LC901_1200;
import java.util.*;
public class LC994_RottingOranges {
    /**
     * You are given an m x n grid where each cell can have one of three values:
     *
     * 0 representing an empty cell,
     * 1 representing a fresh orange, or
     * 2 representing a rotten orange.
     * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
     *
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible,
     * return -1.
     *
     * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
     * Output: 4
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10
     * grid[i][j] is 0, 1, or 2.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) count++;
            }
        }
        if (count == 0) return 0; // corner case, if there is no fresh oranges at the beginning, return 0

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (grid[i][j] == 1) {
                        grid[i][j] = 2;
                        count--;
                        queue.offer(new int[]{i, j});
                    }
                }
            }
            step++;
        }
        return count == 0 ? step - 1 : -1;
    }
}
