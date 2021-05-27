package LC301_600;
import java.util.*;
public class LC317_ShortestDistancefromAllBuildings {
    /**
     * You are given an m x n grid grid of values 0, 1, or 2, where:
     *
     * each 0 marks an empty land that you can pass by freely,
     * each 1 marks a building that you cannot pass through, and
     * each 2 marks an obstacle that you cannot pass through.
     * You want to build a house on an empty land that reaches all buildings in the shortest total travel distance.
     * You can only move up, down, left, and right.
     *
     * Return the shortest travel distance for such a house. If it is not possible to build such a house according to
     * the above rules, return -1.
     *
     * The total travel distance is the sum of the distances between the houses of the friends and the meeting point.
     *
     * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
     *
     * Input: grid = [[1,0,2,0,1],[0,0,0,0,0],[0,0,1,0,0]]
     * Output: 7
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * grid[i][j] is either 0, 1, or 2.
     * There will be at least one building in the grid.
     * @param grid
     * @return
     */
    // time = O(m * n * k), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int shortestDistance(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        int[][] sumCost = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(i * n + j);
                    bfs(grid, queue, sumCost);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) min = Math.min(min, sumCost[i][j]);
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private void bfs(int[][] grid, Queue<Integer> queue, int[][] sumCost) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        int minLen = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / n, j = cur % n;
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj] && grid[ii][jj] == 0) {
                        queue.offer(ii * n + jj);
                        visited[ii][jj] = true;
                        sumCost[ii][jj] += minLen;
                    }
                }
            }
            minLen++;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && grid[i][j] == 0) {
                    grid[i][j] = 2;
                }
            }
        }
    }
}
