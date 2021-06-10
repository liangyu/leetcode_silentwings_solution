package LC601_900;
import java.util.*;
public class LC743_NetworkDelayTime {
    /**
     * You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as
     * directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time
     * it takes for a signal to travel from source to target.
     *
     * We will send a signal from a given node k. Return the time it takes for all the n nodes to receive the signal.
     * If it is impossible for all the n nodes to receive the signal, return -1.
     *
     * Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= k <= n <= 100
     * 1 <= times.length <= 6000
     * times[i].length == 3
     * 1 <= ui, vi <= n
     * ui != vi
     * 0 <= wi <= 100
     * All the pairs (ui, vi) are unique. (i.e., no multiple edges.)
     * @param times
     * @param n
     * @param k
     * @return
     */
    // S1: BFS + PQ
    // time = O(n + ElogE), space = O(n + E)
    public int networkDelayTime(int[][] times, int n, int k) {
        HashMap<Integer, List<int[]>> map = new HashMap<>();
        for (int[] t : times) { // O(E)
            map.putIfAbsent(t[0], new ArrayList<>());
            map.get(t[0]).add(new int[]{t[1], t[2]}); // t[1] -> node, t[2] -> weight
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        pq.offer(new int[]{0, k}); // 起点是k，距离起点的位置则是0, 把dist放前面，这样好按照距离在pq里进行排序
        int[] visited = new int[n + 1]; // node is 1-based
        int res = 0;

        while (!pq.isEmpty()) { // O(ElogE)
            int[] top = pq.poll(); // cur[0] -> dist, cur[1] -> node
            int d = top[0], cur = top[1];
            if (visited[cur] == 1) continue;
            visited[cur] = 1;
            res = Math.max(d, res); // dist[cur[1]] = cur[0]

            if (map.containsKey(cur)) {
                for (int[] next : map.get(cur)) {
                    int node = next[0], weight = next[1];
                    pq.offer(new int[]{d + weight, node});
                }
            }
        }
        for (int i = 1; i <= n; i++) { // O(n)
            if (visited[i] == 0) return -1;
        }
        return res;
    }

    // S2:
    // time = O(n^2), space = O(n + E)
    public int networkDelayTime2(int[][] times, int n, int k) {
        HashMap<Integer, List<int[]>> map = new HashMap<>();
        for (int[] t : times) {
            map.putIfAbsent(t[0], new ArrayList<>());
            map.get(t[0]).add(new int[]{t[1], t[2]}); // t[1] -> node, t[2] -> weight
        }

        int[] determined = new int[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        for (int i = 1; i <= n; i++) {
            int minVal = Integer.MAX_VALUE;
            int minNode = -1;
            for (int j = 1; j <= n; j++) {
                if (determined[j] == 0 && dist[j] < minVal) {
                    minVal = dist[j];
                    minNode = j;
                }
            }
            if (minNode == -1) break;
            determined[minNode] = 1;

            if (map.containsKey(minNode)) {
                for (int[] next : map.get(minNode)) {
                    dist[next[0]] = Math.min(dist[next[0]], dist[minNode] + next[1]);
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dist[i]);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // S3:Floyd
    // time = O(n^3), space = O(n^2)
    public int networkDelayTime3(int[][] times, int n, int k) {
        int[][] dp = new int[n + 1][n + 1]; // dp[i][j]: the shortest dist between i and j
        for (int[] d : dp) Arrays.fill(d, Integer.MAX_VALUE / 2);

        // init
        for (int[] t : times) dp[t[0]][t[1]] = t[2];
        for (int i = 1; i <= n; i++) dp[i][i] = 0;

        for (int m = 1; m <= n; m++) { // 求任意两点之间的最短距离
            // update all dp[i][j]
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][m] + dp[m][j]);
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dp[k][i]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }
}
/**
 * single source + non-negative weight
 * Dijkstra = BFS + PQ 模板题 => O(ElogE)
 * 问离起点最远的点是多少，求最短边的和
 * node 1  2  3  4
 * dist 2  5  4  3
 */
