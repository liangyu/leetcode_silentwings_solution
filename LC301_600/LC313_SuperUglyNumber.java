package LC301_600;

import java.util.PriorityQueue;

public class LC313_SuperUglyNumber {
    /**
     * A super ugly number is a positive integer whose prime factors are in the array primes.
     *
     * Given an integer n and an array of integers primes, return the nth super ugly number.
     *
     * The nth super ugly number is guaranteed to fit in a 32-bit signed integer.
     *
     * Input: n = 12, primes = [2,7,13,19]
     * Output: 32
     *
     * Constraints:
     *
     * 1 <= n <= 10^6
     * 1 <= primes.length <= 100
     * 2 <= primes[i] <= 1000
     * primes[i] is guaranteed to be a prime number.
     * All the values of primes are unique and sorted in ascending order.
     * @param n
     * @param primes
     * @return
     */
    // time = O(nlogk), space = O(n + k)
    public int nthSuperUglyNumber(int n, int[] primes) {
        // corner case
        if (primes == null || primes.length == 0) return -1;

        int k = primes.length;
        int[] res = new int[n];
        res[0] = 1;
        int[] p = new int[k];

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for (int i = 0; i < k; i++) {
            pq.offer(new int[]{res[p[i]] * primes[i], i});
        }
        for (int t = 1; t < n; t++) {
            int cur = pq.peek()[0];
            res[t] = cur;

            while (!pq.isEmpty() && cur == pq.peek()[0]) {
                int i = pq.poll()[1];
                p[i]++;
                pq.offer(new int[]{res[p[i]] * primes[i], i});
            }
        }
        return res[n - 1];
    }
}
/**
 * BFS()
 * [2, 7, 13, 19]
 * [7, 13, 19, 4, 14, 26, 38] => O(nklog(nk))
 *
 * ref: LC246
 * dp_new = min{dp[i] * 2, dp[j] * 3, dp[k] * 5, ... } -> 永远在k个候选人中选一个最小的，维护k个指针
 * 每次只有k个元素找最小值 => O(nlogk)
 */
