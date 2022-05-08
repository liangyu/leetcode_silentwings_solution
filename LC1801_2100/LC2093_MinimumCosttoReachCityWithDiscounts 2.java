package LC1801_2100;
import java.util.*;
public class LC2093_MinimumCosttoReachCityWithDiscounts {
    /**
     * A series of highways connect n cities numbered from 0 to n - 1. You are given a 2D integer array highways where
     * highways[i] = [city1i, city2i, tolli] indicates that there is a highway that connects city1i and city2i, allowing
     * a car to go from city1i to city2i and vice versa for a cost of tolli.
     *
     * You are also given an integer discounts which represents the number of discounts you have. You can use a discount
     * to travel across the ith highway for a cost of tolli / 2 (integer division). Each discount may only be used once,
     * and you can only use at most one discount per highway.
     *
     * Return the minimum total cost to go from city 0 to city n - 1, or -1 if it is not possible to go from city 0 to
     * city n - 1.
     *
     * Input: n = 5, highways = [[0,1,4],[2,1,3],[1,4,11],[3,2,3],[3,4,2]], discounts = 1
     * Output: 9
     *
     * Constraints:
     *
     * 2 <= n <= 1000
     * 1 <= highways.length <= 1000
     * highways[i].length == 3
     * 0 <= city1i, city2i <= n - 1
     * city1i != city2i
     * 0 <= tolli <= 10^5
     * 0 <= discounts <= 500
     * There are no duplicate highways.
     * @param n
     * @param highways
     * @param discounts
     * @return
     */
    // time = O(ElogE) = O(n^2 * logn), space = O(n^2)
    public int minimumCost(int n, int[][] highways, int discounts) {
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : highways) {
            int a = x[0], b = x[1], c = x[2];
            graph[a].add(new int[]{b, c});
            graph[b].add(new int[]{a, c});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        pq.offer(new int[]{0, 0, 0}); // {cost, node, discount}
        int[][] visited = new int[n][discounts + 1];
        for (int i = 0; i < n; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);
        visited[0][0] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], city = cur[1], dis = cur[2];

            if (city == n - 1) return cost;

            for (int[] x : graph[city]) {
                int next = x[0], weight = x[1];
                if (cost + weight < visited[next][dis]) {
                    pq.offer(new int[]{cost + weight, next, dis});
                    visited[next][dis] = cost + weight;
                }
                if (dis < discounts && cost + weight / 2 < visited[next][dis + 1]) {
                    pq.offer(new int[]{cost + weight / 2, next, dis + 1});
                    visited[next][dis + 1] = cost + weight / 2;
                }
            }
        }
        return -1;
    }
}
