package LC2101_2400;
import java.util.*;
public class LC2258_EscapetheSpreadingFire {
    /**
     * You are given a 0-indexed 2D integer array grid of size m x n which represents a field. Each cell has one of
     * three values:
     *
     * 0 represents grass,
     * 1 represents fire,
     * 2 represents a wall that you and fire cannot pass through.
     * You are situated in the top-left cell, (0, 0), and you want to travel to the safehouse at the bottom-right cell,
     * (m - 1, n - 1). Every minute, you may move to an adjacent grass cell. After your move, every fire cell will
     * spread to all adjacent cells that are not walls.
     *
     * Return the maximum number of minutes that you can stay in your initial position before moving while still safely
     * reaching the safehouse. If this is impossible, return -1. If you can always reach the safehouse regardless of the
     * minutes stayed, return 109.
     *
     * Note that even if the fire spreads to the safehouse immediately after you have reached it, it will be counted as
     * safely reaching the safehouse.
     *
     * A cell is adjacent to another cell if the former is directly north, east, south, or west of the latter (i.e.,
     * their sides are touching).
     *
     * Input: grid = [[0,2,0,0,0,0,0],[0,0,0,2,2,1,0],[0,2,0,0,1,2,0],[0,0,2,2,2,0,2],[0,0,0,0,0,0,0]]
     * Output: 3
     *
     * Input: grid = [[0,0,0,0],[0,1,2,0],[0,2,0,0]]
     * Output: -1
     *
     * Input: grid = [[0,0,0],[2,2,0],[1,2,0]]
     * Output: 1000000000
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 300
     * 4 <= m * n <= 2 * 10^4
     * grid[i][j] is either 0, 1, or 2.
     * grid[0][0] == grid[m - 1][n - 1] == 0
     * @param grid
     * @return
     */
    // time = O(m * n * log(m * n)), space = O(m * n)
    int m, n;
    int[][] fire;
    boolean[][] visited;
    int res = -1;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int maximumMinutes(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        fire = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(fire[i], Integer.MAX_VALUE);
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j, 0});
                    fire[i][j] = 0;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1], time = cur[2];
            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (fire[i][j] != Integer.MAX_VALUE) continue;
                if (grid[i][j] == 2) continue;
                queue.offer(new int[]{i, j, time + 1});
                fire[i][j] = time + 1;
            }
        }

        // binary search
        int left = 0, right = (int) 1e9;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (helper(grid, mid)) left = mid;
            else right = mid - 1;
        }
        return helper(grid, left) ? left : left - 1;
    }

    private boolean helper(int[][] grid, int t) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0});
        visited = new boolean[m][n];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1], time = cur[2];
            if (time + t > fire[x][y]) continue;
            if (time + t == fire[x][y] && !(x == m - 1 && y == n - 1)) continue;
            if (x == m - 1 && y == n - 1) return true;

            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (visited[i][j]) continue;
                if (grid[i][j] == 2) continue;
                queue.offer(new int[]{i, j, time + 1});
                visited[i][j] = true;
            }
        }
        return false;
    }
}
