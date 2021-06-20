package LC601_900;
import java.util.*;
public class LC857_MinimumCosttoHireKWorkers {
    /**
     * There are n workers.  The i-th worker has a quality[i] and a minimum wage expectation wage[i].
     *
     * Now we want to hire exactly k workers to form a paid group.  When hiring a group of k workers, we must pay them
     * according to the following rules:
     *
     * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid
     * group.
     * Every worker in the paid group must be paid at least their minimum wage expectation.
     * Return the least amount of money needed to form a paid group satisfying the above conditions.
     *
     * Input: quality = [10,20,5], wage = [70,50,30], k = 2
     * Output: 105.00000
     *
     * Note:
     *
     * 1 <= k <= n <= 10000, where n = quality.length = wage.length
     * 1 <= quality[i] <= 10000
     * 1 <= wage[i] <= 10000
     * Answers within 10-5 of the correct answer will be considered correct.
     * @param quality
     * @param wage
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;
        int[][] persons = new int[n][2];
        for (int i = 0; i < n; i++) {
            persons[i] = new int[]{quality[i], wage[i]};
        }
        Arrays.sort(persons, ((o1, o2) -> Double.compare(o1[1] * 1.0 / o1[0], o2[1] * 1.0 / o2[0])));

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int quality_sum = 0;
        double res = 1e20;

        for (int i = 0; i < n; i++) {
            double unitWage = persons[i][1] * 1.0 / persons[i][0];
            quality_sum += persons[i][0];
            if (pq.size() > k - 1) {
                quality_sum -= pq.poll();
            }
            if (pq.size() == k - 1) res = Math.min(res, unitWage * quality_sum);
            pq.offer(persons[i][0]);
        }
        return res;
    }
}
/**
 * 2个属性：sort + pq
 * wageEarned[i] / quality[i] = unitWage -> 固定值
 * wageEarned[i] >= wage[i] => wageEarned[i] / quality[i] >= wage[i] / quality[i]
 * => unitWage >= wage[i] / quality[i]
 *
 * unitWage must be the maximum of unitWageExp of K workers
 * C(n, k), n 种选择标杆，找那些容易遍历的量，One-pass 最特殊的人 O(n)
 * unitWageExp_1, unitWageExp_2, ..., unitWageExp_t, ... unitWageExp_max
 * wageSum = unitWage * quality_sum => 找 k-1个总工作量最小的 => maxHeap to always have the k smallest qualities
 *
 * pq.size() > k => pq.poll()
 * wageSum = unitWage * quality_sum
 */
