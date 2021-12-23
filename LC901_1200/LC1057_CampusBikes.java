package LC901_1200;
import java.util.*;
public class LC1057_CampusBikes {
    /**
     * On a campus represented on the X-Y plane, there are n workers and m bikes, with n <= m.
     *
     * You are given an array workers of length n where workers[i] = [xi, yi] is the position of the ith worker. You are
     * also given an array bikes of length m where bikes[j] = [xj, yj] is the position of the jth bike. All the given
     * positions are unique.
     *
     * Assign a bike to each worker. Among the available bikes and workers, we choose the (workeri, bikej) pair with the
     * shortest Manhattan distance between each other and assign the bike to that worker.
     *
     * If there are multiple (workeri, bikej) pairs with the same shortest Manhattan distance, we choose the pair with
     * the smallest worker index. If there are multiple ways to do that, we choose the pair with the smallest bike
     * index. Repeat this process until there are no available workers.
     *
     * Return an array answer of length n, where answer[i] is the index (0-indexed) of the bike that the ith worker is
     * assigned to.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
     * Output: [1,0]
     *
     * Constraints:
     *
     * n == workers.length
     * m == bikes.length
     * 1 <= n <= m <= 1000
     * workers[i].length == bikes[j].length == 2
     * 0 <= xi, yi < 1000
     * 0 <= xj, yj < 1000
     * All worker and bike locations are unique.
     * @param workers
     * @param bikes
     * @return
     */
    // time = O(m * n * log(n)), space = O(n)
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        int n = workers.length, m = bikes.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : (o1[1] != o2[1] ? o1[1] - o2[1] : o1[2] - o2[2]));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int dist = Math.abs(bikes[i][0] - workers[j][0]) + Math.abs(bikes[i][1] - workers[j][1]);
                pq.offer(new int[]{dist, i, j});
            }
        }

        HashSet<Integer> set = new HashSet<>();
        int[] res = new int[n];
        Arrays.fill(res, -1);
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int bike = cur[1], worker = cur[2];
            if (res[worker] != -1) continue;
            if (!set.add(bike)) continue;
            res[worker] = bike;
        }
        return res;
    }
}
