package LC1501_1800;
import java.util.*;
public class LC1730_ShortestPathtoGetFood {
    /**
     * You are starving and you want to eat food as quickly as possible. You want to find the shortest path to arrive
     * at any food cell.
     *
     * You are given an m x n character matrix, grid, of these different types of cells:
     *
     * '*' is your location. There is exactly one '*' cell.
     * '#' is a food cell. There may be multiple food cells.
     * 'O' is free space, and you can travel through these cells.
     * 'X' is an obstacle, and you cannot travel through these cells.
     * You can travel to any adjacent cell north, east, south, or west of your current location if there is not an
     * obstacle.
     *
     * Return the length of the shortest path for you to reach any food cell. If there is no path for you to reach food,
     * return -1.
     *
     * Input: grid = [["X","X","X","X","X","X"],["X","*","O","O","O","X"],["X","O","O","#","O","X"],["X","X","X","X","X","X"]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * grid[row][col] is '*', 'X', 'O', or '#'.
     * The grid contains exactly one '*'.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int getFood(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '*') {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    break;
                }
            }
        }

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                if (grid[x][y] == '#') return step;

                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (grid[i][j] == 'X') continue;
                    if (visited[i][j]) continue;
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
            step++;
        }
        return -1;
    }
}
