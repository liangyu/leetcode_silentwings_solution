package LC1801_2100;
import java.util.*;
public class LC1962_RemoveStonestoMinimizetheTotal {
    /**
     * You are given a 0-indexed integer array piles, where piles[i] represents the number of stones in the ith pile,
     * and an integer k. You should apply the following operation exactly k times:
     *
     * Choose any piles[i] and remove floor(piles[i] / 2) stones from it.
     * Notice that you can apply the operation on the same pile more than once.
     *
     * Return the minimum possible total number of stones remaining after applying the k operations.
     *
     * floor(x) is the greatest integer that is smaller than or equal to x (i.e., rounds x down).
     *
     * Input: piles = [5,4,9], k = 2
     * Output: 12
     *
     * Constraints:
     *
     * 1 <= piles.length <= 10^5
     * 1 <= piles[i] <= 10^4
     * 1 <= k <= 10^5
     * @param piles
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int minStoneSum(int[] piles, int k) {
        // corner case
        if (piles == null || piles.length == 0 || k <= 0) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int p : piles) pq.offer(p);

        while (!pq.isEmpty()) {
            int cur = pq.poll();
            cur -= cur / 2;
            pq.offer(cur);
            k--;
        }

        int res = 0;
        while (!pq.isEmpty()) res += pq.poll();
        return res;
    }
}
