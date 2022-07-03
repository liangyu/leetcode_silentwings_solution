package LC2101_2400;
import java.util.*;
public class LC2279_MaximumBagsWithFullCapacityofRocks {
    /**
     * You have n bags numbered from 0 to n - 1. You are given two 0-indexed integer arrays capacity and rocks. The
     * ith bag can hold a maximum of capacity[i] rocks and currently contains rocks[i] rocks. You are also given an
     * integer additionalRocks, the number of additional rocks you can place in any of the bags.
     *
     * Return the maximum number of bags that could have full capacity after placing the additional rocks in some bags.
     *
     * Input: capacity = [2,3,4,5], rocks = [1,2,4,4], additionalRocks = 2
     * Output: 3
     *
     * Constraints:
     *
     * n == capacity.length == rocks.length
     * 1 <= n <= 5 * 10^4
     * 1 <= capacity[i] <= 10^9
     * 0 <= rocks[i] <= capacity[i]
     * 1 <= additionalRocks <= 10^9
     * @param capacity
     * @param rocks
     * @param additionalRocks
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {
        int n = capacity.length;
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) diff[i] = capacity[i] - rocks[i];

        Arrays.sort(diff);

        int count = 0;
        for (int x : diff) {
            if (additionalRocks >= x) {
                additionalRocks -= x;
                count++;
            } else break;
        }
        return count;
    }
}
