package LC1501_1800;
import java.util.*;
public class LC1568_MinimumNumberofDaystoDisconnectIsland {
    /**
     * Given a 2D grid consisting of 1s (land) and 0s (water).  An island is a maximal 4-directionally (horizontal or
     * vertical) connected group of 1s.
     *
     * The grid is said to be connected if we have exactly one island, otherwise is said disconnected.
     *
     * In one day, we are allowed to change any single land cell (1) into a water cell (0).
     *
     * Return the minimum number of days to disconnect the grid.
     *
     * Input: grid = [[0,1,1,0],[0,1,1,0],[0,0,0,0]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= grid.length, grid[i].length <= 30
     * grid[i][j] is 0 or 1.
     * @param grid
     * @return
     */
    // time = O(m^2 * n^2), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int minDays(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int count = island(grid);
        if (count != 1) return 0; // [0,0,0,0,0]应该返回0，同理count > 1也是返回0 => 判断条件应该为count != 1

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                grid[i][j] = 0;
                count = island(grid);
                if (count != 1) return 1; // 翻转一个岛，判断条件同上，要么变为全0，要么count > 1
                grid[i][j] = 1;
            }
        }
        return 2;
    }

    private int island(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                if (visited[i][j]) continue;
                Queue<int[]> queue = new LinkedList<>();
                queue.offer(new int[]{i, j});
                visited[i][j] = true;

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    int x = cur[0], y = cur[1];

                    for (int[] dir : directions) {
                        int a = x + dir[0];
                        int b = y + dir[1];
                        if (a < 0 || a >= m || b < 0 || b >= n) continue;
                        if (visited[a][b]) continue;
                        if (grid[a][b] == 0) continue;
                        queue.offer(new int[]{a, b});
                        visited[a][b] = true;
                    }
                }
                count++;
                if (count > 1) return count;
            }
        }
        return count;
    }
}
/**
 *    x
 *  x x x
 *    x
 * 最多也就翻转4次，答案的上限就是4
 * 但并不是所有点都不是被4个相邻的点包围着
 * 肯定有些点在边缘的，比如可能只有3面有陆地 -> 这样最多翻转3次
 * 不管什么样的几何图形，肯定会有corner点，最多只有2面邻接大陆，把两面邻接大陆的点翻转
 * 所以答案只有3种可能：0，1，2
 * 翻一块陆地：暴力枚举， 30*30 => O(900) => bfs (how many islands, O(900)) => O(810000) ~ 10^6
 * 0,1都不行的话，那就最多翻2次即可 => 排除法
 */
