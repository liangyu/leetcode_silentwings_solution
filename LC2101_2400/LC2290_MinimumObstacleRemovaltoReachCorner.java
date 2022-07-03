package LC2101_2400;
import java.util.*;
public class LC2290_MinimumObstacleRemovaltoReachCorner {
    /**
     * You are given a 0-indexed 2D integer array grid of size m x n. Each cell has one of two values:
     *
     * 0 represents an empty cell,
     * 1 represents an obstacle that may be removed.
     * You can move up, down, left, or right from and to an empty cell.
     *
     * Return the minimum number of obstacles to remove so you can move from the upper left corner (0, 0) to the lower
     * right corner (m - 1, n - 1).
     *
     * Input: grid = [[0,1,1],[1,1,0],[1,1,0]]
     * Output: 2
     *
     * Input: grid = [[0,1,0,0,0],[0,1,0,1,0],[0,0,0,1,0]]
     * Output: 0
     *
     *
     Constraints:

     m == grid.length
     n == grid[i].length
     1 <= m, n <= 10^5
     2 <= m * n <= 10^5
     grid[i][j] is either 0 or 1.
     grid[0][0] == grid[m - 1][n - 1] == 0
     * @param grid
     * @return
     */
    // S1: Dijkstra
    // time = O(m * n * log(m * n)), space = O(m * n)
    int m, n;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int minimumObstacles(int[][] grid) {
        m = grid.length;
        n = grid[0].length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        pq.offer(new int[]{0, 0, 0});
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);
        visited[0][0] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int x = cur[0], y = cur[1], cnt = cur[2];
            if (x == m - 1 && y == n - 1) return cnt;

            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                int c = cnt;
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (grid[i][j] == 1) c++;
                if (visited[i][j] <= c) continue;
                pq.offer(new int[]{i, j, c});
                visited[i][j] = c;
            }
        }
        return -1;
    }

    // S2: bfs
    // time = O(m * n), space = O(m * n)
    public int minimumObstacles2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;

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
                    if (visited[i][j]) continue;
                    if (grid[i][j] == 1) {
                        queue.offer(new int[]{i, j});
                        visited[i][j] = true;
                    } else {
                        for (int[] next : travelAir(grid, i, j, visited)) {
                            int ii = next[0], jj = next[1];
                            if (ii == m - 1 && jj == n - 1) return step;
                            queue.offer(new int[]{ii, jj});
                            visited[ii][jj] = true;
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private List<int[]> travelAir(int[][] grid, int x0, int y0, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;
        List<int[]> res = new ArrayList<>();
        if (x0 == m - 1 && y0 == n - 1) {
            res.add(new int[]{x0, y0});
            return res;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x0, y0});
        visited[x0][y0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1];
            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (visited[i][j]) continue;
                visited[i][j] = true;
                if (i == m - 1 && j == n - 1) res.add(new int[]{i, j});
                if (grid[i][j] == 1) {
                    res.add(new int[]{i, j});
                } else queue.offer(new int[]{i, j});
            }
        }
        return res;
    }
}
/**
 * 本题的本质就是从起点到终点，采用层级BFS，最少需要穿越几个回合的障碍。而障碍与障碍之间的空气，可以忽略不计。
 * 也就是说，某个障碍与空气相邻的话，下一个回合可以通过空气到达其他的障碍。
 * 在实现过程中，除了常规的层级BFS之外，我们还需要有一个travelAir的函数。travelAir以某个空格子为起点，遍历所有能“隔空”访问的障碍物。
 * 这些障碍物需要加入下一回合BFS的队列中去。
 */