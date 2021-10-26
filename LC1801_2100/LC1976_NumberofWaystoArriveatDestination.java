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
    private long[] dist;
    private long[] memo;
    private HashMap<Integer, List<int[]>> map;
    public int countPaths(int n, int[][] roads) {
        // corner case
        if (roads == null || roads.length == 0) return n == 1 ? 1 : 0;

        map = new HashMap<>();
        for (int[] r : roads) {
            map.putIfAbsent(r[0], new ArrayList<>());
            map.putIfAbsent(r[1], new ArrayList<>());
            map.get(r[0]).add(new int[]{r[1], r[2]});
            map.get(r[1]).add(new int[]{r[0], r[2]});
        }

        dist = new long[n];
        Arrays.fill(dist, -1);

        PriorityQueue<Pair> pq = new PriorityQueue<>((o1, o2) -> (int) (o1.dist - o2.dist));
        pq.offer(new Pair(0, 0));// [dist, node]

        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            long d = p.dist;
            int cur = p.id;
            if (dist[cur] != -1) continue;
            dist[cur] = d;

            if (map.containsKey(cur)) {
                for (int[] next : map.get(cur)) {
                    int node = next[0], weight = next[1];
                    if (dist[node] != -1) continue;
                    pq.offer(new Pair(d + weight, node)); // pq中pair的d必须用long,因为会越界！！！
                }
            }
        }

        memo = new long[n];
        Arrays.fill(memo, -1);
        return (int)dfs(n - 1, dist[n - 1]);
    }

    private long dfs(int cur, long d) {
        // base case
        if (d != dist[cur]) return 0;
        if (cur == 0) return 1;

        if (memo[cur] != -1) return memo[cur];

        long count = 0;
        if (map.containsKey(cur)) {
            for (int[] next : map.get(cur)) {
                int node = next[0], weight = next[1];
                count += dfs(node, d - weight);
                count %= M;
            }
        }
        memo[cur] = count;
        return count;
    }

    private class Pair {
        private long dist;
        private int id;
        public Pair(long dist, int id) {
            this.dist = dist;
            this.id = id;
        }
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
