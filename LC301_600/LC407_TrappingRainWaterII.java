package LC301_600;
import java.util.*;
public class LC407_TrappingRainWaterII {
    /**
     * Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return
     * the volume of water it can trap after raining.
     *
     * Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
     * Output: 4
     *
     * Constraints:
     *
     * m == heightMap.length
     * n == heightMap[i].length
     * 1 <= m, n <= 200
     * 0 <= heightMap[i][j] <= 2 * 10^4
     * @param heightMap
     * @return
     */
    // time = O(m * n * log(m * n)), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int trapRainWater(int[][] heightMap) {
        // corner case
        if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) return 0;

        int m = heightMap.length, n = heightMap[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    pq.offer(new int[]{heightMap[i][j], i, j});
                    visited[i][j] = true;
                }
            }
        }

        int res = 0, cur = Integer.MIN_VALUE;
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int h = top[0];
            int i = top[1];
            int j = top[2];

            if (h > cur) cur = h; // 新一轮开始
            res += cur - h;

            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj]) {
                    pq.offer(new int[]{heightMap[ii][jj], ii, jj});
                    visited[ii][jj] = true;
                }
            }
        }
        return res;
    }
}
/**
 * 优化：优先队列可以只放边界，常规队列专门放小于海平面的格子，在内陆做常规BFS
 * 找一个每时每刻最矮的堤岸 => pq (minHeap)
 * bfs + pq
 */
