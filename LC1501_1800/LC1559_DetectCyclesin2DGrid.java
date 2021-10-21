package LC1501_1800;
import java.util.*;
public class LC1559_DetectCyclesin2DGrid {
    /**
     * Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the
     * same value in grid.
     *
     * A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell,
     * you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right),
     * if it has the same value of the current cell.
     *
     * Also, you cannot move to the cell that you visited in your last move. For example, the cycle
     * (1, 1) -> (1, 2) -> (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.
     *
     * Return true if any cycle of the same value exists in grid, otherwise, return false.
     *
     * Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
     * Output: true
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 500
     * grid consists only of lowercase English letters.
     * @param grid
     * @return
     */
    // S1: BFS
    // time = O(m * n * (m + n)), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public boolean containsCycle(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) continue;
                Queue<int[]> queue = new LinkedList<>(); // {x, y, dir to {x, y}}
                queue.offer(new int[]{i, j, -1}); // 起点没方向
                visited[i][j] = true;

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    int x = cur[0], y = cur[1], d = cur[2];
                    for (int k = 0; k < 4; k++) {
                        if (d != -1 && Math.abs(d - k) == 2) continue; // d, k 相反方向
                        int a = x + directions[k][0];
                        int b = y + directions[k][1];
                        if (a < 0 || a >= m || b < 0 || b >= n) continue;
                        if (grid[a][b] != grid[x][y]) continue;
                        if (visited[a][b]) return true;
                        queue.offer(new int[]{a, b, k});
                        visited[a][b] = true;
                    }
                }
            }
        }
        return false;
    }

    // S2: Union Find
    // time = O(m * n * log(m * n)), space = O(m * n)
    private int[] parent;
    public boolean containsCycle2(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        parent = new int[m * n];
        for (int i = 0; i < m * n; i++) parent[i] = i;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // compare upper side
                if (i > 0 && grid[i][j] == grid[i - 1][j]) {
                    int v1 = i * n + j, v2 = (i - 1) * n + j;
                    if (findParent(v1) == findParent(v2)) return true;
                    union(v1, v2);
                }
                // compare left side
                if (j > 0 && grid[i][j] == grid[i][j - 1]) {
                    int v1 = i * n + j, v2 = i * n + j - 1;
                    if (findParent(v1) == findParent(v2)) return true;
                    union(v1, v2);
                }
            }
        }
        return false;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * 从任意一点开始，对同一个value的所有像素做常规的遍历。如果遍历的过程中遇到了之前访问过的格子，那么就是有环。
 * 注意遍历的过程中不能走“回头路”，即从A遍历到B，那么从B开始的遍历就不能包括A。
 * 有向图判断有环 -> 用拓扑排序 eg.LC1462
 * S2: 使用并查集判断无向图中是否有环的方法非常简洁且直观：
 * 对于图中的任意一条边 (x, y), 我们将 x 和 y 对应的集合合并。如果 x 和 y 已经属于同一集合，那么说明 x 和 y 已经连通，
 * 在边 (x, y)的帮助下，图中会形成一个环。
 * 我们遍历数组grid 中的每一个位置，如果该位置与其"上方或左侧"的值相同，那么就有了一条边，并将这两个位置进行合并。
 * 这样的方法可以保证每一条边的两个节点只会被合并一次。
 */