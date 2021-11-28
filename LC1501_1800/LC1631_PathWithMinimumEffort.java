package LC1501_1800;
import java.util.*;
public class LC1631_PathWithMinimumEffort {
    /**
     * You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns,
     * where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0),
     * and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed).
     * You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.
     *
     * A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
     *
     * Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
     *
     * Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
     * Output: 2
     *
     * Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
     * Output: 0
     *
     * Constraints:
     *
     * rows == heights.length
     * columns == heights[i].length
     * 1 <= rows, columns <= 100
     * 1 <= heights[i][j] <= 10^6
     *
     * @param heights
     * @return
     */
    // S1: B.S. + BFS
    // time = O(m * n * 10^6), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int minimumEffortPath(int[][] heights) {
        int left = 0, right = (int) 1e6;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isOK(heights, mid)) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    private boolean isOK(int[][] heights, int t) {
        int m = heights.length, n = heights[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1];

            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (visited[i][j]) continue;
                if (Math.abs(heights[i][j] - heights[x][y]) > t) continue;
                queue.offer(new int[]{i, j});
                visited[i][j] = true;
            }
        }
        return visited[m - 1][n - 1];
    }

    // S2: Greedy + Union Find
    // time = O(m * n * log(m * n)), space = O(m * n)
    int[] parent;
    public int minimumEffortPath2(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        parent = new int[m * n];
        for (int i = 0; i < m * n; i++) parent[i] = i;

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 每个点只管右边和下边2条边即可
                if (j < n - 1) edges.add(new int[]{Math.abs(heights[i][j] - heights[i][j + 1]), i * n + j, i * n + j + 1});
                if (i < m - 1) edges.add(new int[]{Math.abs(heights[i][j] - heights[i + 1][j]), i * n + j, (i + 1) * n + j});
            }
        }

        Collections.sort(edges, (o1, o2) -> o1[0] - o2[0]);

        for (int[] edge : edges) {
            int a = edge[1], b = edge[2];
            if (findParent(a) != findParent(b)) union(a, b);
            if (findParent(0) == findParent((m - 1) * n + n - 1)) return edge[0];
        }
        return 0;
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
 * S1: B.S.
 * 可以来回走 => 不是DP，DFS也太耗时间
 * 正向不行 => 反向 => B.S. 二分搜索，猜一个答案，看从左上到右下能否连通，可以的话就往小的方向试，直至收敛
 * 只要落差 < A 就可以走到，O(m * n) BFS, 虚拟的把 > A的那些边删除
 *
 * S2: 贪心
 * 把所有的边拿出来从小到大排个序，找到落差最小的边，比如落差为0的边，看能把哪些边连起来，然后再把落差倒数第二小的边连起来
 * 如果这些边能把左上角到右下角连通就OK了 => 优先选落差比较小的边，看能union哪些边，再看倒数第二小的边，看从左上角到右下角能否union起来
 */
