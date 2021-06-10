package LC601_900;
import java.util.*;
public class LC746_MinCostClimbingStairs {
    /**
     * You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost,
     * you can either climb one or two steps.
     *
     * You can either start from the step with index 0, or the step with index 1.
     *
     * Return the minimum cost to reach the top of the floor.
     *
     * Input: cost = [10,15,20]
     * Output: 15
     *
     * Constraints:
     *
     * 2 <= cost.length <= 1000
     * 0 <= cost[i] <= 999
     * @param cost
     * @return
     */
    // time = O(n), space = O(1)
    public int minCostClimbingStairs(int[] cost) {
        // corner case
        if (cost == null || cost.length == 0) return 0;

        int old = 0, now = 0;
        for (int i = 2; i <= cost.length; i++) {
            int temp = now;
            now = Math.min(old + cost[i - 2], now + cost[i - 1]);
            old = temp;

        }
        return now;
    }
}
