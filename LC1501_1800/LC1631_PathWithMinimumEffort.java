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
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int minimumEffortPath(int[][] heights) {
        // corner case
        if (heights == null || heights.length == 0) return 0;

        // [0, INT_MAX] => 32 二分搜索效率非常高，因此随便估计就可以
        int left = 0, right = 1000000;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (bfs(heights, mid)) {
                right = mid - 1; // 满足的话可以继续往更小的方向去找
            } else left = mid + 1; // 不满足的话，说明试探的差值太小，往更高的方向去找
        }
        return left; // 最后一定可以找到，收敛于left
    }

    private boolean bfs(int[][] heights, int h) {
        int m = heights.length, n = heights[0].length;

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(0, 0));
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int i = p.x, j = p.y;
            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                // 判断条件：不能出界；不能走回头路；高度差不能大于当前试探的阈值h
                if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj] && Math.abs(heights[ii][jj] - heights[i][j]) <= h) {
                    queue.offer(new Pair(ii, jj));
                    visited[ii][jj] = true; // 记得要为了查重，把当前访问过的点标记true
                }
            }
        }
        return visited[m - 1][n - 1] == true; // 看最后有没有能够reach到右下角的终点
    }

    private class Pair {
        private int x;
        private int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // S2: Greedy + Union Find
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int minimumEffortPath2(int[][] heights) {
        // corner case
        if (heights == null || heights.length == 0) return 0;

        int m = heights.length, n = heights[0].length;
        List<Cell> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 每个点只要负责把其向右和向下的两条边抽出来即可
                if (j != n - 1) { // 到达最右列的时候只要负责抽出下方的边即可
                    edges.add(new Cell(Math.abs(heights[i][j] - heights[i][j + 1]), i * n + j, i * n + j + 1));
                }
                if (i != m - 1) { // 到达最下行的时候只要负责抽出最右边的边即可
                    edges.add(new Cell(Math.abs(heights[i][j] - heights[i + 1][j]), i * n + j, (i + 1) * n + j));
                }
            }
        }
        // 贪心策略，对高度差排序，优先从高度差最小的edge出发去寻找看能否连上终点
        Collections.sort(edges, ((o1, o2) -> o1.diff - o2.diff));

        UnionFind uf = new UnionFind(m * n);

        for (Cell edge : edges) {
            if (!uf.find(edge.i, edge.j)) uf.union(edge.i, edge.j); // 如果没有公共祖先，则2个邻居连线起来
            // 如果从起点到终点已经连接起来了，则一定是最优解，因为我们是从高度差最小的edge出发来寻找的
            if (uf.find(0, (m - 1) * n + (n - 1))) return edge.diff; // 如果从起点到终点已经连接起来了，则一定是最优解，因为我们是从高度差最小的edge出发来寻找的
        }
        return 0; // 如果只有一个点的情况，即形成不了边的时候，上面的代码都进不了，那么起点即为终点，返回0！！！
    }

    private class UnionFind {
        private int[] parent;
        private int[] size;
        public UnionFind(int n) {
            this.parent = new int[n];
            this.size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int getRoot(int v) {
            int cur = v;
            while (parent[cur] != cur) {
                parent[cur] = parent[parent[cur]];
                cur = parent[cur];
            }
            parent[v] = cur;
            return cur;
        }

        private boolean find(int p, int q) {
            return getRoot(p) == getRoot(q);
        }

        private void union(int p, int q) {
            int pRoot = getRoot(p);
            int qRoot = getRoot(q);

            if (size[pRoot] < size[qRoot]) {
                parent[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
            } else {
                parent[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
            }
        }
    }

    private class Cell {
        private int diff;
        private int i;
        private int j;

        public Cell(int diff, int i, int j) {
            this.diff = diff;
            this.i = i;
            this.j = j;
        }
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
