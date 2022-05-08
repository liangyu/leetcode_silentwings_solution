package LC601_900;
import java.util.*;
public class LC787_CheapestFlightsWithinKStops {
    /**
     * There are n cities connected by some number of flights. You are given an array flights where flights[i] =
     * [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
     *
     * You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops.
     * If there is no such route, return -1.
     *
     * Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
     * Output: 200
     *
     * Constraints:
     *
     * 1 <= n <= 100
     * 0 <= flights.length <= (n * (n - 1) / 2)
     * flights[i].length == 3
     * 0 <= fromi, toi < n
     * fromi != toi
     * 1 <= pricei <= 10^4
     * There will not be any multiple flights between two cities.
     * 0 <= src, dst, k < n
     * src != dst
     * @param n
     * @param flights
     * @param src
     * @param dst
     * @param k
     * @return
     */
    // S1: dp
    // time = O(m * k), space = O(n * k)
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[][] dp = new int[k + 2][n];
        for (int i = 0; i < k + 2; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        dp[0][src] = 0;

        for (int i = 1; i <= k + 1; i++) {
            for (int[] flight : flights) {
                int b = flight[0], c = flight[1], cost = flight[2];
                dp[i][c] = Math.min(dp[i][c], dp[i - 1][b] + cost);
            }
        }
        int res = Integer.MAX_VALUE / 2;
        for (int i = 0; i <= k + 1; i++) res = Math.min(res, dp[i][dst]);
        return res >= Integer.MAX_VALUE / 2 ? -1 : res;
    }

    // S2: dp
    // time = O(k * m), space = O(n * k)
    public int findCheapestPrice2(int n, int[][] flights, int src, int dst, int k) {
        int[][] dp = new int[k + 2][n];
        for (int i = 0; i < k + 2; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        dp[0][src] = 0;

        for (int i = 1; i <= k + 1; i++) {
            for (int[] flight : flights) {
                int b = flight[0], c = flight[1], cost = flight[2];
                dp[i][c] = Math.min(dp[i][c], dp[i - 1][b] + cost);
                // dp[k][c]: the minimum cost to arrive in city c by taking (up to) k flights
                dp[i][c] = Math.min(dp[i][c], dp[i - 1][c]);
            }
        }
        return dp[k + 1][dst] == Integer.MAX_VALUE / 2 ? -1 : dp[k + 1][dst];
    }

    // S3: Dijkstra
    // time = O(ElogE) = O(n^2 * logn), space = O(n^2)
    public int findCheapestPrice3(int n, int[][] flights, int src, int dst, int k) {
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] flight : flights) {
            graph[flight[0]].add(new int[]{flight[1], flight[2]});
        }

        int[][] visited = new int[n][k + 2];
        for (int i = 0; i < n; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {cost, step, city}
        pq.offer(new int[]{0, 0, src});
        visited[src][0] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], step = cur[1], city = cur[2];
            if (city == dst) return cost;
            if (step == k + 1) continue;

            for (int[] next : graph[city]) {
                if (cost + next[1] < visited[next[0]][step + 1]) {
                    pq.offer(new int[]{cost + next[1], step + 1, next[0]});
                    visited[next[0]][step + 1] = cost + next[1];
                }
            }
        }
        return -1;
    }
}
/**
 * Floyd是有问题的
 * dp[dst][k] = min{dp[midway][k-1] + cost[midway][dst]}
 * dp[k][c]: the minimum cost to arrive in city c by taking k flights
 * dp[k][c] = min{dp[k-1][b] + cost[b][c]} for b = ......
 * ans = min{dp[k+1][dst]} k = 0,1,2,...,k+1
 * S2: Dijkstra
 *        -> F
 * A -> B -> C -> D
 * ----------------> E -> D
 * F, C, E
 * 较早弹出的对应的一定是最短路径
 * midway: 3 stops, 20 cost => (F,G,H)
 * midway: 2 stops, 30 cost => (F,G,H)
 * A -> midway -> D
 * 任何在队列中的都是合法的
 * 比较朴素的贪心 + bfs
 * 3. Floyd
 * O(n^3) e[i][j]
 * for (int k = 0 ~ n)
 *      for (int i = 0 ~ n)
 *          for (int j = 0 ~ n)
 *              if (e[i][j] > e[i][k] + e[k][j] && t[i][k] + t[k][j] <= k + 1) ??? xxxx 不对的！！！
 *                  e[i][j] = min(e[i][j], e[i][k] + e[k][j]); // 不能超过k次
 * 对于非终点的任何路径而言，e[i][j]不一定意味着路径最短即是最优的，也有可能转机次数限制，也有可能在后续计算destination时也会有帮助
 * 尽管路径最短，但是可能转机次数更多
 * Floyd这道题是不能做的
 * 结点是个2维量 (x,3) -> (y,4)
 * DP:
 * dp[i][b] = min{dp[i - 1][a] + price[a][b]}  where there is a flight a -> b
 */