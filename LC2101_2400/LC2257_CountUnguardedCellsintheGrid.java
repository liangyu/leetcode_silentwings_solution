package LC2101_2400;
import java.util.*;
public class LC2257_CountUnguardedCellsintheGrid {
    /**
     * You are given two integers m and n representing a 0-indexed m x n grid. You are also given two 2D integer arrays
     * guards and walls where guards[i] = [rowi, coli] and walls[j] = [rowj, colj] represent the positions of the ith
     * guard and jth wall respectively.
     *
     * A guard can see every cell in the four cardinal directions (north, east, south, or west) starting from their
     * position unless obstructed by a wall or another guard. A cell is guarded if there is at least one guard that can
     * see it.
     *
     * Return the number of unoccupied cells that are not guarded.
     *
     * Input: m = 4, n = 6, guards = [[0,0],[1,1],[2,3]], walls = [[0,1],[2,2],[1,4]]
     * Output: 7
     *
     * Input: m = 3, n = 3, guards = [[1,1]], walls = [[0,1],[1,0],[2,1],[1,2]]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= m, n <= 10^5
     * 2 <= m * n <= 10^5
     * 1 <= guards.length, walls.length <= 5 * 10^4
     * 2 <= guards.length + walls.length <= m * n
     * guards[i].length == walls[j].length == 2
     * 0 <= rowi, rowj < m
     * 0 <= coli, colj < n
     * All the positions in guards and walls are unique.
     * @param m
     * @param n
     * @param guards
     * @param walls
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        int[][] grid = new int[m][n];
        for (int[] wall : walls) {
            int x = wall[0], y = wall[1];
            grid[x][y] = -1;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[m][n][4];
        for (int[] guard : guards) {
            int x = guard[0], y = guard[1];
            queue.offer(new int[]{x, y, 0});
            queue.offer(new int[]{x, y, 1});
            queue.offer(new int[]{x, y, 2});
            queue.offer(new int[]{x, y, 3});
            visited[x][y][0] = true;
            visited[x][y][1] = true;
            visited[x][y][2] = true;
            visited[x][y][3] = true;
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1], k = cur[2];
            grid[x][y] = 1;

            int i = x + directions[k][0];
            int j = y + directions[k][1];
            if (i < 0 || i >= m || j < 0 || j >= n) continue;
            if (visited[i][j][k]) continue;
            if (grid[i][j] == -1) continue;
            queue.offer(new int[]{i, j, k});
            visited[i][j][k] = true;
        }

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) count++;
            }
        }
        return count;
    }
}
