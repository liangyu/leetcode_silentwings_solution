package LC901_1200;
import java.util.*;
public class LC1162_AsFarfromLandasPossible {
    /**
     * Given an n x n grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water
     * cell such that its distance to the nearest land cell is maximized, and return the distance. If no land or water
     * exists in the grid, return -1.
     *
     * The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1)
     * is |x0 - x1| + |y0 - y1|.
     *
     * Input: grid = [[1,0,1],[0,0,0],[1,0,1]]
     * Output: 2
     *
     * Constraints:
     *
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 100
     * grid[i][j] is 0 or 1
     * @param grid
     * @return
     */
    // time = O(n^2), space = O(n^2)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        Queue<int[]> queue = new LinkedList<>();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    dist[i][j] = 0;
                }
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];

                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= n || j < 0 || j >= n) continue;
                    if (dist[i][j] > dist[x][y] + 1) {
                        dist[i][j] = dist[x][y] + 1;
                        queue.offer(new int[]{i, j});
                    }
                }
            }
        }

        int res = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) res = Math.max(res, dist[i][j]);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
/**
 * 多源最短路问题 -> 多源BFS
 */