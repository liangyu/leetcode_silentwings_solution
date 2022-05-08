package LC2101_2400;
import java.util.*;
public class LC2247_MaximumCostofTripWithKHighways {
    /**
     * A series of highways connect n cities numbered from 0 to n - 1. You are given a 2D integer array highways where
     * highways[i] = [city1i, city2i, tolli] indicates that there is a highway that connects city1i and city2i,
     * allowing a car to go from city1i to city2i and vice versa for a cost of tolli.
     *
     * You are also given an integer k. You are going on a trip that crosses exactly k highways. You may start at any
     * city, but you may only visit each city at most once during your trip.
     *
     * Return the maximum cost of your trip. If there is no trip that meets the requirements, return -1.
     *
     * Input: n = 5, highways = [[0,1,4],[2,1,3],[1,4,11],[3,2,3],[3,4,2]], k = 3
     * Output: 17
     *
     * Constraints:
     *
     * 2 <= n <= 15
     * 1 <= highways.length <= 50
     * highways[i].length == 3
     * 0 <= city1i, city2i <= n - 1
     * city1i != city2i
     * 0 <= tolli <= 100
     * 1 <= k <= 50
     * There are no duplicate highways.
     * @param n
     * @param highways
     * @param k
     * @return
     */
    // time = O(n), space = O(2^n * n)
    public int maximumCost(int n, int[][] highways, int k) {
        if (k > n - 1) return -1;
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : highways) {
            int a = x[0], b = x[1], c = x[2];
            graph[a].add(new int[]{b, c});
            graph[b].add(new int[]{a, c});
        }

        int[][] dp = new int[1 << n][n];
        for (int i = 0; i < (1 << n); i++) Arrays.fill(dp[i], Integer.MIN_VALUE);


        int res = -1;
        k++;
        for (int j = 0; j < n; j++) {
            int state = (1 << k) - 1;
            while (state < (1 << n)) {
                if (((state >> j) & 1) == 1) {
                    res = Math.max(res, dfs(graph, dp, state - (1 << j), j));
                }

                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r;
            }
        }
        return res;
    }

    private int dfs(List<int[]>[] graph, int[][] dp, int state, int cur) {
        // base case
        if (state == 0) return 0;

        if (dp[state][cur] == Integer.MIN_VALUE) {
            for (int[] x : graph[cur]) {
                int next = x[0], cost = x[1];
                if (((state >> next) & 1) == 1) {
                    dp[state][cur] = Math.max(dp[state][cur], dfs(graph, dp, state - (1 << next), next) + cost);
                }
            }
        }
        return dp[state][cur];
    }
}
