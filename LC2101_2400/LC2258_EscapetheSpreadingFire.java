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

    // S2: bfs x 2
    // time = O(m * n), space = O(m * n)
    public int maximumMinutes2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] person = bfs(grid, 0);
        int[][] fire = bfs(grid, 1);

        if (person[m - 1][n - 1] == Integer.MAX_VALUE) return -1;
        if (person[m - 1][n - 1] > fire[m - 1][n - 1]) return -1;
        if (fire[m - 1][n - 1] == Integer.MAX_VALUE) return (int) 1e9;

        int d = fire[m - 1][n - 1] - person[m - 1][n - 1];
        if (fire[m - 1][n - 2] == fire[m - 2][n - 1]) return d - 1;
        if (fire[m - 1][n - 2] < fire[m - 2][n - 1]) {
            if (person[m - 2][n - 1] == person[m - 1][n - 1] - 1) return d;
        } else {
            if (person[m - 1][n - 2] == person[m - 1][n - 1] - 1) return d;
        }
        return d - 1;
    }

    private int[][] bfs(int[][] grid, int type) {
        int m = grid.length, n = grid[0].length;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(res[i], Integer.MAX_VALUE);

        Queue<int[]> queue = new LinkedList<>();
        if (type == 0) {
            queue.offer(new int[]{0, 0});
            res[0][0] = 0;
        } else {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        queue.offer(new int[]{i, j});
                        res[i][j] = 0;
                    }
                }
            }
        }

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
                    if (grid[i][j] == 2) continue;
                    if (res[i][j] != Integer.MAX_VALUE) continue;
                    res[i][j] = step + 1;
                    if (i != m - 1 || j != n - 1) {
                        queue.offer(new int[]{i, j});
                    }
                }
            }
            step++;
        }
        return res;
    }

    // S3: bfs
    // time = O(m * n), space = O(m * n)
    public int maximumMinutes3(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] person = bfs(grid, 0);
        int[][] fire = bfs(grid, 1);

        if (person[m - 1][n - 1] == Integer.MAX_VALUE) return -1;
        if (person[m - 1][n - 1] > fire[m - 1][n - 1]) return -1;
        if (fire[m - 1][n - 1] == Integer.MAX_VALUE) return (int) 1e9;

        int d = fire[m - 1][n - 1] - person[m - 1][n - 1];

        if (checkOK(grid, fire, d)) return d;
        return d - 1;
    }

    private boolean checkOK(int[][] grid, int[][] fire, int d) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        int step = d;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (i < 0 || i >= m || j < 0 || j >= n) continue;
                    if (grid[i][j] == 2) continue;
                    if (visited[i][j]) continue;
                    if ((i != m - 1 || j != n - 1) && step + 1 >= fire[i][j]) continue;
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    if (i == m - 1 && j == n - 1 && step + 1 <= fire[i][j]) return true;
                }
            }
            step++;
        }
        return false;
    }

    // S4: B.S.
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int maximumMinutes4(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] person = bfs(grid, 0);
        int[][] fire = bfs(grid, 1);

        if (person[m - 1][n - 1] == Integer.MAX_VALUE) return -1;
        if (person[m - 1][n - 1] > fire[m - 1][n - 1]) return -1;
        if (fire[m - 1][n - 1] == Integer.MAX_VALUE) return (int) 1e9;

        int left = 0, right = m * n;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (checkOK(grid, fire, mid)) left = mid;
            else right = mid - 1;
        }
        return checkOK(grid, fire, left) ? left : -1;
    }

    // S5: reverse Dijkstra
    // time = O(ElogE) = O(m * n * log(m * n)), space = O(m * n)
    public int maximumMinutes5(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] person = bfs(grid, 0);
        int[][] fire = bfs(grid, 1);

        if (person[m - 1][n - 1] == Integer.MAX_VALUE) return -1;
        if (person[m - 1][n - 1] > fire[m - 1][n - 1]) return -1;
        if (fire[m - 1][n - 1] == Integer.MAX_VALUE) return (int) 1e9;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        pq.offer(new int[]{fire[m - 1][n - 1], m - 1, n - 1});
        boolean[][] visited = new boolean[m][n];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            // the latest date to arrive at (i, j) so that we can arrive at the destination the same as the fire
            int t = cur[0], x = cur[1], y = cur[2];
            if (visited[x][y]) continue;
            visited[x][y] = true;

            if (x == 0 && y == 0) return t;

            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (grid[i][j] == 2) continue;
                if (visited[i][j]) continue;
                pq.offer(new int[]{Math.min(t - 1, fire[i][j] - 1), i, j});
            }
        }
        return -1;
    }
}
/**
 * 除了终点，人都要比火早到
 * bfs跑不掉
 * 可以以人作为起点，做bfs，人到达每个格子最早的时间
 * person[i][j]: the earliest time for the people to arrive at (i,j)
 * fire[i][j]: the earliest time for the person to arrive at (i,j)
 * person[i][j] < fire[i][j] except destination
 * person[m - 1][n - 1] = Integer.MAX_VALUE => return -1
 * person[m - 1][n - 1] > fire[m - 1][n - 1] => return -1
 * fire[m - 1][n - 1] == Integer.MAX_VALUE => 10^9
 * D = fire[m - 1][n - 1] - person[m - 1][n - 1]
 * stay D - 1 => ok
 * D ？
 * 我们需要考虑再多停留一天，也就是D是否是可行的答案
 *   F
 *   O
 * P O O O D
 * P可以走4步到达终点D，F可以走5步到达终点D。
 * 人与火会在非终点的位置相遇，这就不合条件。
 *         P
 *         O
 * F O O O D
 * 人可以停留两天再出发（即4-2）。因为人与火的最优路径只在终点才相遇
 * 只有人和火从2个不同的方向到达终点，才是可以人和火同时到达终点。
 * 中间所有路径都不可能相交，不会出现人在某个位置上被火阻挡
 * 1. fire[m-1][n-2] == fire[m-2][n-1] => return D-1
 * 2. fire[m-1][n-2] < fire[m-2][n-1]
 *      if (person[m-2][n-1] == person[m-1][n-1] - 1) => return D
 *      else return D-1
 * 3. fire[m-1][n-2] > fire[m-2][n-1]
 *      if (person[m-1][n-2] == person[m-1][n-1] - 1) => return D
 *      else return D-1
 *
 * S4: Dijkstra
 * 我们可以求得一个矩阵，ret[i][j]表示人最晚什么时候到达(i,j)，才能保证能在fire[m-1][n-1]时刻到达右下角。
 * Dijkstra的传播过程要遵守两个规则：随着BFS的过程，ret[i][j]必须逐步是变小；其次任何位置上，ret[i][j]必须小于fire[i][j].
 * 这也解释了为什么得用Dijkstra和PQ，而不是传统的BFS和队列，这是因为相邻两点之间的时间差不一定是1.
 *
 * Summary:
 * 1. 2 bfs
 * 2. 2 bfs + 1 bfs (check)
 * 3. 2 bfs + binary search using check
 * 4. 1 bfs + reverse Dijkstra
 */