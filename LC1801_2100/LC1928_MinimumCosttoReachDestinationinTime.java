package LC1801_2100;
import java.util.*;
public class LC1928_MinimumCosttoReachDestinationinTime {
    /**
     * There is a country of n cities numbered from 0 to n - 1 where all the cities are connected by bi-directional
     * roads. The roads are represented as a 2D integer array edges where edges[i] = [xi, yi, timei] denotes a road
     * between cities xi and yi that takes timei minutes to travel. There may be multiple roads of differing travel
     * times connecting the same two cities, but no road connects a city to itself.
     *
     * Each time you pass through a city, you must pay a passing fee. This is represented as a 0-indexed integer array
     * passingFees of length n where passingFees[j] is the amount of dollars you must pay when you pass through city j.
     *
     * In the beginning, you are at city 0 and want to reach city n - 1 in maxTime minutes or less. The cost of your
     * journey is the summation of passing fees for each city that you passed through at some moment of your journey
     * (including the source and destination cities).
     *
     * Given maxTime, edges, and passingFees, return the minimum cost to complete your journey, or -1 if you cannot
     * complete it within maxTime minutes.
     *
     * Input: maxTime = 30, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
     * Output: 11
     *
     * Constraints:
     *
     * 1 <= maxTime <= 1000
     * n == passingFees.length
     * 2 <= n <= 1000
     * n - 1 <= edges.length <= 1000
     * 0 <= xi, yi <= n - 1
     * 1 <= timei <= 1000
     * 1 <= passingFees[j] <= 1000
     * The graph may contain multiple edges between two nodes.
     * The graph does not contain self loops.
     * @param maxTime
     * @param edges
     * @param passingFees
     * @return
     */
    // S1
    // time = O(V + ElogE) = O(nlogn), space = O(V + E) = O(n)
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        HashMap<Integer, List<int[]>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.putIfAbsent(edge[1], new ArrayList<>());
            map.get(edge[0]).add(new int[]{edge[1], edge[2]}); // [endpoint, time]
            map.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }

        int[] minTime = new int[n];
        Arrays.fill(minTime, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]); // [node, cost, time]
        pq.offer(new int[]{0, passingFees[0], 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (cur[2] >= minTime[cur[0]]) continue;
            minTime[cur[0]] = cur[2];

            if (cur[0] == n - 1) return cur[1];

            if (map.containsKey(cur[0])) {
                for (int[] next : map.get(cur[0])) {
                    int time = cur[2] + next[1];
                    int cost = cur[1] + passingFees[next[0]];

                    if (time > maxTime || time > minTime[next[0]]) continue;
                    pq.offer(new int[]{next[0], cost, time});
                }
            }
        }
        return -1;
    }

    // S2: BFS
    // time =
    public int minCost2(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        // dp[c][t]: the min cost to reach c at time t
        int[] earliestTime = new int[n + 1];

        int[][] dp = new int[n][maxTime + 1];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);

        HashMap<Integer, List<int[]>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.putIfAbsent(edge[1], new ArrayList<>());
            map.get(edge[0]).add(new int[]{edge[1], edge[2]});
            map.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        dp[0][0] = passingFees[0];

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int city = cur[0], time = cur[1], fee = dp[city][time];
            if (map.containsKey(city)) {
                for (int[] key : map.get(city)) {
                    int next = key[0], deltaT = key[1];
                    int newTime = time + deltaT;
                    int newFee = fee + passingFees[next];
                    if (newTime > maxTime) continue;
                    if (newTime > earliestTime[next] && newFee > dp[next][earliestTime[next]]) continue;
                    if (newFee < dp[next][newTime]) {
                        dp[next][newTime] = newFee;
                        queue.offer(new int[]{next, newTime});
                        earliestTime[next] = Math.min(earliestTime[next], newTime);
                    }
                }
            }
        }

        int res = Integer.MAX_VALUE / 2;
        for (int t = 0; t <= maxTime; t++) {
            res = Math.min(res, dp[n - 1][t]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }
}
/**
 * S
 * C: time 10, fee 20
 *    time 20, fee 10
 * D
 * 并没有直接的方案去判断谁优谁劣 -> 遍历图的时候，对于每个城市要记录它的费用和时间
 * 从起点状态到终点状态，都要记录三元量：city, time, fee
 * {city, fee}
 * {city, time1, fee1} -> {D, ?, ?}
 * {city, time2, fee2} -> {D, ?, ?}
 * => dp[city][time] = fee
 * 最短路径问题bfs => 最常规的bfs
 * 跟猫和老鼠差不多
 * {pos, cat/mouse, step}
 * 初始状态: dp[0][0] = passingfees[0]
 * dp[1][10] = 1
 * dp[3][1] = 20
 * {0, 0} => {1, 10} =>
 *        => {3, 1} =>
 * 这里dp相当于记忆化,不需要把所有dp的city, time都算出来
 * for t = 0 : maxTime
 *  for c = 0 : n- 1
 *      dp[t][c] = min{dp[t-delta][prevC] + passingFees[c]}
 * => TLE!!! 本质上并不是所有的t和c有值
 * bfs/dfs只会访问到那些合理的状态，能够转移到的状态 => time = O(10^6)
 * dp[c][t] = 10
 * {c, t} = 5 => ...
 * min{dp[n-1][t]}  t = 0, 1, 2, ...maxTime
 * multi-state bfs
 */
