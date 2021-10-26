package LC901_1200;
import java.util.*;
public class LC1066_CampusBikesII {
    /**
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D
     * coordinate on this grid.
     *
     * We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their
     * assigned bike is minimized.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.
     *
     * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
     * Output: 6
     *
     * Constraints:
     *
     * N == workers.length
     * M == bikes.length
     * 1 <= N <= M <= 10
     * workers[i].length == 2
     * bikes[i].length == 2
     * 0 <= workers[i][0], workers[i][1], bikes[i][0], bikes[i][1] < 1000
     * All the workers and the bikes locations are unique.
     * @param workers
     * @param bikes
     * @return
     */
    // time = O(m * n!), space = O(m * n)
    public int assignBikes(int[][] workers, int[][] bikes) {
        // corner case
        if (workers == null || workers.length == 0 || bikes == null || bikes.length == 0) return 0;

        int m = workers.length, n = bikes.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {cost, state}
        pq.offer(new int[]{0, 0});
        boolean[] visited = new boolean[n];
        int[][] dist = new int[m][n];

        for (int i = 0; i < m; i++) { // O(m)
            for (int j = 0; j < n; j++) {
                int x1 = workers[i][0];
                int y1 = workers[i][1];
                int x2 = bikes[j][0];
                int y2 = bikes[j][1];
                dist[i][j] = Math.abs(x1 - x2) + Math.abs(y1 - y2);
            }
        }

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], state = cur[1];
            if (visited[state]) continue;
            visited[state] = true;

            int i = Integer.bitCount(cur[1]);
            if (i == m) return cost;

            for (int j = 0; j < n; j++) { // O(n)
                if (((state >> j) & 1) == 1) continue;
                int newState = state + (1 << j);
                if (visited[newState]) continue;
                pq.offer(new int[]{cost + dist[i][j], newState});
            }
        }
        return -1;
    }
}
/**
 * 带权二分图的最优匹配问题 -> KM算法(匈牙利算法的进阶版)
 * Dijkstra本质: bfs + pq 每次在队列里弹出一个元素，首元素相邻结点到首元素的权重是不一样的，不能无脑放在队列后面，需要自动排序
 * 标准Dijkstra的题目
 * 如何转化？ 想要结点的图表示的是状态 -> n bit 的二进制数
 * state: 01100 表示说我们已经处理完了前2个工人，分别配对了1号和2号自行车
 * k is the number of 1 bits in state
 * the minimum cost of the first k workers assigned with the 1-bit bikes
 * 我们不关心具体这两个工人是怎么占据这2辆自行车，我们并不关心，只求最少代价来占据
 * 把state作为node，node 之间边的转化
 * initial state: 00000
 * dp[10110]：dp[00110] + dist[2][0]
 * the minimum cost of the first 3 workers assigned with 0/2/3-th bike
 *
 * dp[01110]: dp[00110] + dist[2][1]
 * dp[00111]: dp[00110] + dist[2][4]
 * node, edge
 * destination: a state that contains m 1-bits
 * Dijkstra算的是任何一个状态到起点的一个距离，如果某个状态在优先队列里是第一次弹出来，那这个状态对应的权重就是优先距离 -> 贪心思想
 *
 * [00110]
 * [00110, 01110, 00111]  看这三个结点所对应的cost，谁最小就放到前面去
 * cost[10110] 因为你不可能通过其他状态再跳转到这个点上，因为它的cost是最小的，所有权重都是正的，都不如从起点直接走到它cost最小
 * 非常典型的Dijkstra模板题
 */
