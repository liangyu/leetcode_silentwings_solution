package LC1201_1500;
import java.util.*;
public class LC1368_MinimumCosttoMakeatLeastOneValidPathinaGrid {
    /**
     * Given a m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are
     * currently in this cell. The sign of grid[i][j] can be:
     * 1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
     * 2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
     * 3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
     * 4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
     * Notice that there could be some invalid signs on the cells of the grid which points outside the grid.
     *
     * You will initially start at the upper left cell (0,0). A valid path in the grid is a path which starts from the
     * upper left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1) following the signs on the grid. The
     * valid path doesn't have to be the shortest.
     *
     * You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.
     *
     * Return the minimum cost to make the grid have at least one valid path.
     *
     * Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * @param grid
     * @return
     */
    // S1: Dijkstra
    // time = O(m * n * log(m * n)), space = O(m * n)
    private int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {cost, x, y}
        pq.offer(new int[]{0, 0, 0});
        boolean[][] visited = new boolean[m][n];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], x = cur[1], y = cur[2];
            if (visited[x][y]) continue;
            visited[x][y] = true;

            if (x == m - 1 && y == n - 1) return cost;

            for (int k = 0; k < 4; k++) {
                int i = x + directions[k][0];
                int j = y + directions[k][1];
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (visited[i][j]) continue;
                int addon = k + 1 == grid[x][y] ? 0 : 1;
                pq.offer(new int[]{cost + addon, i, j});
            }
        }
        return -1;
    }

    // S2: BFS
    // time = O(m * n), space = O(m * n)
    public int minCost2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        List<int[]> temp = new ArrayList<>();
        flow(0, 0, grid, temp, visited);
        Queue<int[]> queue = new LinkedList<>();
        for (int[] x : temp) queue.offer(x);

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                for (int k = 0; k < 4; k++) {
                    int a = x + directions[k][0];
                    int b = y + directions[k][1];
                    if (a < 0 || a >= m || b < 0 || b >= n) continue;
                    if (visited[a][b]) continue;

                    temp = new ArrayList<>(); // must clear the temp list before new round!
                    flow(a, b, grid, temp, visited);
                    for (int[] t : temp) {
                        if (t[0] == m - 1 && t[1] == n - 1) return step + 1;
                        queue.offer(t);
                    }
                }
            }
            step++;
        }
        return 0; // 一次就能从起点漂到终点
    }

    private void flow(int x, int y, int[][] grid, List<int[]> temp, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;
        if (x < 0 || x >= m || y < 0 || y >= n) return;
        if (visited[x][y]) return;

        temp.add(new int[]{x, y});
        visited[x][y] = true;

        int i = x + directions[grid[x][y] - 1][0];
        int j = y + directions[grid[x][y] - 1][1];
        flow(i, j, grid, temp, visited);
    }
}
/**
 * traditional bfs => time = O(m*n)
 * Dijkstra = bfs + pq
 * 有点贪心的方法 => ElogE = 4mn => 时间复杂度稍微高一点
 * 每个格子作为一个结点，边是4*m*n
 * 把它想象成一个图的模型，套模板
 * 边的权重不同 -> ->
 * cost = 1 => 边的权重是1
 * single source，起点终点固定
 * 非负边权，互不相同，最短距离，single source => Dijkstra
 * 队列里先弹出来的结点，按照这些结点到起点的距离 => 就是最短距离
 *
 * S2: bfs
 * Set0
 * Set1
 * Set2
 * Set3
 * bfs
 * set包含2部分：一部分是通过改变方向得到，另一部分是改变后顺流而下得到的新区域
 */
