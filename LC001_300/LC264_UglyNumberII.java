package LC001_300;
import java.util.*;
public class LC264_UglyNumberII {
    /**
     * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
     *
     * Given an integer n, return the nth ugly number.
     *
     * Input: n = 10
     * Output: 12
     *
     * Constraints:
     *
     * 1 <= n <= 1690
     * @param n
     * @return
     */
    // time = O(n), space = O(n)
    public int nthUglyNumber(int n) {
        int i = 0, j = 0, k = 0;
        int[] res = new int[n];
        res[0] = 1;

        for (int t = 1; t < n; t++) {
            res[t] = Math.min(res[i] * 2, Math.min(res[j] * 3, res[k] * 5));
            if (res[t] == res[i] * 2) i++;
            if (res[t] == res[j] * 3) j++;
            if (res[t] == res[k] * 5) k++;
        }
        return res[n - 1];
    }

    // S2: PQ
    // time = O(3n * log(3n)), space = O(n)
    public int nthUglyNumber2(int n) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        pq.offer((long)1);

        for (int t = 0; t < n; t++) {
            long cur = pq.poll();
            if (t == n - 1) return (int)cur;
            while (!pq.isEmpty() && pq.peek() == cur) pq.poll(); // 重复的数都要扔掉
            pq.offer(cur * 2);
            pq.offer(cur * 3);
            pq.offer(cur * 5);
        }
        return -1;
    }
}
/**
 * 类似bfs
 * [1]
 * [2,3,5]
 * [3,5,4,6,10]
 * [4,5,6,10,6,9,15] pq 最小数在上面
 * [5,6,6,9,10,15]
 *
 * BFS: queue
 *      PQ
 * 通过弹出来的最小丑数再以此为基础往外扩散 -> bfs
 * time complexity = O(3n * log(3n))
 * 弹出n个数(求前n小数 -> 弹1个进3个)
 *
 * new ugly number = old[i] * 2
 *                   old[j] * 3
 *                   old[k] * 5
 * 三个指针
 * dp[t] = min{dp[i] * 2, dp[j] * 3, dp[k] * 5}
 * update(i, j, k)  i, j, k 是指针
 */