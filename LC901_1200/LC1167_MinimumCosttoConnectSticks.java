package LC901_1200;
import java.util.*;
public class LC1167_MinimumCosttoConnectSticks {
    /**
     * You have some number of sticks with positive integer lengths. These lengths are given as an array sticks, where
     * sticks[i] is the length of the ith stick.
     *
     * You can connect any two sticks of lengths x and y into one stick by paying a cost of x + y. You must connect all
     * the sticks until there is only one stick remaining.
     *
     * Return the minimum cost of connecting all the given sticks into one stick in this way.
     *
     * Input: sticks = [2,4,3]
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= sticks.length <= 10^4
     * 1 <= sticks[i] <= 10^4
     * @param sticks
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int connectSticks(int[] sticks) {
        // corner case
        if (sticks == null || sticks.length < 2) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int stick : sticks) pq.offer(stick);

        int res = 0;
        while (pq.size() > 1) {
            int sum = pq.poll() + pq.poll();
            res += sum;
            pq.offer(sum);
        }
        return res;
    }
}
