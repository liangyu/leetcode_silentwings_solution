package LC901_1200;
import java.util.*;
public class LC1102_PathWithMaximumMinimumValue {
    /**
     * Given an m x n integer matrix grid, return the maximum score of a path starting at (0, 0) and ending
     * at (m - 1, n - 1) moving in the 4 cardinal directions.
     *
     * The score of a path is the minimum value in that path.
     *
     * For example, the score of the path 8 → 4 → 5 → 9 is 4.
     *
     * Input: grid = [[5,4,5],[1,2,6],[7,4,6]]
     * Output: 4
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * 0 <= grid[i][j] <= 10^9
     * @param grid
     * @return
     */
    // S1: BS + BFS
    // time = O(log(m * n)), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int maximumMinimumPath(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int left = 0, right = (int)1e9;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (checkOK(grid, mid)) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private boolean checkOK(int[][] grid, int target) {
        if (grid[0][0] < target) return false; // 记得一上来就要check起点是否满足条件；不满足的话，直接return false

        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / n, j = cur % n;
            if (i == m - 1 && j == n - 1) return true;
            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj] && grid[ii][jj] >= target) {
                    queue.offer(ii * n + jj);
                    visited[ii][jj] = true;
                }
            }
        }
        return false;
    }

    // S2: PQ + BFS
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int maximumMinimumPath2(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[1] - o1[1]);
        pq.offer(new int[]{0, grid[0][0]});
        boolean[][] visited =  new boolean[m][n];
        visited[0][0] = true;

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int i = top[0] / n, j = top[0] % n;
            if (i == m - 1 && j == n - 1) return top[1];

            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj]) {
                    pq.offer(new int[]{ii * n + jj, Math.min(grid[ii][jj], top[1])});
                    visited[ii][jj] = true;
                }
            }
        }
        return -1;
    }
}
