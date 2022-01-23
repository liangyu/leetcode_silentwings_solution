package LC1201_1500;
import java.util.*;
public class LC1383_MaximumPerformanceofaTeam {
    /**
     * You are given two integers n and k and two integer arrays speed and efficiency both of length n. There are n
     * engineers numbered from 1 to n. speed[i] and efficiency[i] represent the speed and efficiency of the ith engineer
     * respectively.
     *
     * Choose at most k different engineers out of the n engineers to form a team with the maximum performance.
     *
     * The performance of a team is the sum of their engineers' speeds multiplied by the minimum efficiency among their
     * engineers.
     *
     * Return the maximum performance of this team. Since the answer can be a huge number, return it modulo 10^9 + 7.
     *
     * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
     * Output: 60
     *
     * Constraints:
     *
     * 1 <= <= k <= n <= 10^5
     * speed.length == n
     * efficiency.length == n
     * 1 <= speed[i] <= 10^5
     * 1 <= efficiency[i] <= 10^8
     * @param n
     * @param speed
     * @param efficiency
     * @param k
     * @return
     */
    // time = O(n * (logn + logk)), space = O(n + k)
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        // corner case
        if (speed == null || speed.length == 0 || efficiency == null || efficiency.length == 0) return 0;

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(new int[]{efficiency[i], speed[i]}); // O(n)
        Collections.sort(list, (o1, o2) -> o2[0] - o1[0]); // O(nlogn)

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long res = 0, speedSum = 0, M = (long)(1e9 + 7);

        for (int i = 0; i < n; i++) { // O(n)
            speedSum += list.get(i)[1];
            pq.offer(list.get(i)[1]); // O(logk)
            if (pq.size() > k) speedSum -= pq.poll();
            res = Math.max(res, list.get(i)[0] * speedSum);
        }
        return (int)(res % M);
    }
}
/**
 * 要使团队得分最大，既要团队的速度和尽量大，又要最低效率的人的效率尽量大。
 * 如果一个人是团队中效率最低的, 我们在所有效率比他高的人里面，取speed最大的k个人
 */
