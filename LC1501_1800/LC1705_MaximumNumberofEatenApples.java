package LC1501_1800;
import java.util.*;
public class LC1705_MaximumNumberofEatenApples {
    /**
     * There is a special kind of apple tree that grows apples every day for n days. On the ith day, the tree grows
     * apples[i] apples that will rot after days[i] days, that is on day i + days[i] the apples will be rotten and
     * cannot be eaten. On some days, the apple tree does not grow any apples, which are denoted by apples[i] == 0 and
     * days[i] == 0.
     *
     * You decided to eat at most one apple a day (to keep the doctors away). Note that you can keep eating after the
     * first n days.
     *
     * Given two integer arrays days and apples of length n, return the maximum number of apples you can eat.
     *
     * Input: apples = [1,2,3,5,2], days = [3,2,1,4,2]
     * Output: 7
     *
     * Constraints:
     *
     * apples.length == n
     * days.length == n
     * 1 <= n <= 2 * 10^4
     * 0 <= apples[i], days[i] <= 2 * 10^4
     * days[i] = 0 if and only if apples[i] = 0.
     * @param apples
     * @param days
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int eatenApples(int[] apples, int[] days) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int n = apples.length, res = 0, i = 0;
        while (i < n || !pq.isEmpty()) {
            while (!pq.isEmpty() && pq.peek()[0] == i) pq.poll();
            if (i < n && apples[i] > 0) {
                pq.offer(new int[]{i + days[i], apples[i]});
            }
            if (!pq.isEmpty()) {
                res++;
                pq.peek()[1]--;
                if (pq.peek()[1] == 0) pq.poll();
            }
            i++;
        }
        return res;
    }
}
/**
 * n = 2e^4 + 2e^4 = 4e^4
 */
