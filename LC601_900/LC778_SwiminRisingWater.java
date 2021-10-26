package LC601_900;
import java.util.*;
public class LC778_SwiminRisingWater {
    /**
     * You are given an n x n integer matrix grid where each value grid[i][j] represents the elevation at that point
     * (i, j).
     *
     * The rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another
     * 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can
     * swim infinite distances in zero time. Of course, you must stay within the boundaries of the grid during your swim.
     *
     * Return the least time until you can reach the bottom right square (n - 1, n - 1) if you start at the top left
     * square (0, 0).
     *
     * Input: grid = [[0,2],[1,3]]
     * Output: 3
     *
     * Constraints:
     *
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 50
     * 0 <= grid[i][j] < n^2
     * Each value grid[i][j] is unique.
     * @param grid
     * @return
     */
    // time = O(n^2 * logn), space = O(n^2)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {h, x, y}
        pq.offer(new int[]{grid[0][0], 0, 0});
        boolean[][] visited = new boolean[n][n];
        int res = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int h = cur[0], x = cur[1], y = cur[2];
            res = Math.max(res, h);

            if (visited[x][y]) continue;
            visited[x][y] = true;

            if (x == n - 1 && y == n - 1) return res;

            for (int[] dir : directions) {
                int xx = x + dir[0];
                int yy = y + dir[1];
                if (xx < 0 || xx >= n || yy < 0 || yy >= n) continue;
                if (visited[xx][yy]) continue;
                pq.offer(new int[]{grid[xx][yy], xx, yy});
            }
        }
        return -1;
    }
}
/**
 * ref: LC407
 * 从起点开始，人随着海平面的高度上升。我们将起点周围的格子作为“堤岸”放入一个优先队列里，PQ里面的元素按照高度从低到高。
 * 显然，当海平面高度cur涨至PQ的最小元素高度时，就会从那里“决堤而入”，这时候那个决堤口周围的新格子就可以作为新“堤岸”放入PQ。
 * 如果下一个回合PQ里面的最小元素也小于等于cur时，说明那个格子也会被“淹没”，我们继续将那个格子周围的格子作为“堤岸”加入队列。
 * 如果下一个回合PQ里面的最小元素大于cur时，那么就需要让整个海平面cur继续上涨，直至到达PQ里面的最小元素高度，从而引发又一次“决堤泛滥”的过程。
 * 整个海平面上涨的过程持续到我们能淹没右下角的格子为止。
 * [0]
 * [1 24]
 * [24 2 23] => [2 23 24]
 */
