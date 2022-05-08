package LC1801_2100;
import java.util.*;
public class LC1976_NumberofWaystoArriveatDestination {
    /**
     * You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between
     * some intersections. The inputs are generated such that you can reach any intersection from any other intersection
     * and that there is at most one road between any two intersections.
     *
     * You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a
     * road between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you
     * can travel from intersection 0 to intersection n - 1 in the shortest amount of time.
     *
     * Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer
     * may be large, return it modulo 10^9 + 7.
     *
     * Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= n <= 200
     * n - 1 <= roads.length <= n * (n - 1) / 2
     * roads[i].length == 3
     * 0 <= ui, vi <= n - 1
     * 1 <= timei <= 10^9
     * ui != vi
     * There is at most one road connecting any two intersections.
     * You can reach any intersection from any other intersection.
     * @param n
     * @param roads
     * @return
     */
    // time = O(n^3), space = O(n^2)
    private long M = (long)(1e9 + 7);
    private long[] dist; // dist[i]: the shortest dist from 0 to i
    private List<long[]>[] graph;
    private long[] memo;
    public int countPaths(int n, int[][] roads) {
        dist = new long[n];
        graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] road : roads) {
            int u = road[0], v = road[1], len = road[2];
            graph[u].add(new long[]{v, len});
            graph[v].add(new long[]{u, len});
        }

        Arrays.fill(dist, -1); // 不能设置为0，0作为dist是有意义的

        PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[0], o2[0])); // {dist, city}

        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int c = (int) cur[1];

            if (dist[c] != -1) continue;
            dist[c] = d;

            for (long[] x : graph[c]) {
                int next = (int) x[0];
                long len = x[1];
                if (dist[next] != -1) continue;
                pq.offer(new long[]{d + len, next});
            }
        }

        // dist[i] 都已经被赋值了
        memo = new long[n];
        Arrays.fill(memo, -1);
        return (int) dfs(n - 1, dist[n - 1]);
    }

    private long dfs(int cur, long d) {
        // base case
        if (d != dist[cur]) return 0;
        if (cur == 0) return 1;
        if (memo[cur] != -1) return memo[cur];

        long total = 0;
        for (long[] x : graph[cur]) {
            int next = (int) x[0];
            long len = x[1];
            total += dfs(next, d - len);
            total %= M;
        }
        memo[cur] = total;
        return total;
    }
}
/**
 * 最短路径长度是多少 -> Dijkstra的裸题
 * 这里问多少条
 * 大框架，求最短路径
 * Dijkstra: single source, non-negative weight
 * 最短距离知道了，只考虑总长度为7的那些路径
 * 倒着看
 * T- ti = T[Ai]
 * Dijkstra + dfs + memo
 * 找倒数第二站，看相邻点，递归思想
 */
