package LC2101_2400;
import java.util.*;
public class LC2297_JumpGameIX {
    /**
     * You are given a 0-indexed integer array nums of length n. You are initially standing at index 0. You can jump
     * from index i to index j where i < j if:
     *
     * nums[i] <= nums[j] and nums[k] < nums[i] for all indexes k in the range i < k < j, or
     * nums[i] > nums[j] and nums[k] >= nums[i] for all indexes k in the range i < k < j.
     * You are also given an integer array costs of length n where costs[i] denotes the cost of jumping to index i.
     *
     * Return the minimum cost to jump to the index n - 1.
     *
     * Input: nums = [3,2,4,4,1], costs = [3,7,6,4,2]
     * Output: 8
     *
     * Input: nums = [0,1,2], costs = [1,1,1]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums.length == costs.length
     * 1 <= n <= 10^5
     * 0 <= nums[i], costs[i] <= 10^5
     * @param nums
     * @param costs
     * @return
     */
    // time = O(n), space = O(n)
    public long minCost(int[] nums, int[] costs) {
        int n = nums.length;
        Stack<Integer> stk1 = new Stack<>();
        Stack<Integer> stk2 = new Stack<>();
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            while (!stk1.isEmpty() && nums[stk1.peek()] <= nums[i]) {
                if (dp[stk1.peek()] != Long.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[stk1.peek()] + costs[i]);
                }
                stk1.pop();
            }
            stk1.push(i);

            while (!stk2.isEmpty() && nums[stk2.peek()] > nums[i]) {
                if (dp[stk2.peek()] != Long.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[stk2.peek()] + costs[i]);
                }
                stk2.pop();
            }
            stk2.push(i);
        }
        return dp[n - 1];
    }
}
