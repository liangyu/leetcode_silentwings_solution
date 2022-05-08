package LC2101_2400;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LC2263_MakeArrayNondecreasingorNonincreasing {
    /**
     * You are given a 0-indexed integer array nums. In one operation, you can:
     *
     * Choose an index i in the range 0 <= i < nums.length
     * Set nums[i] to nums[i] + 1 or nums[i] - 1
     * Return the minimum number of operations to make nums non-decreasing or non-increasing.
     *
     * Input: nums = [3,2,4,5,0]
     * Output: 4
     *
     * Input: nums = [2,2,3,4]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int convertArray(int[] nums) {
        int n = nums.length;
        int cost1 = helper(nums);
        for (int i = 0; i < n; i++) nums[i] = -nums[i];
        int cost2 = helper(nums);
        return Math.min(cost1, cost2);
    }

    private int helper(int[] nums) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int cost = 0;

        for (int x : nums) {
            if (!pq.isEmpty() && x < pq.peek()) {
                cost += pq.poll() - x;
                pq.offer(x);
            }
            pq.offer(x);
        }
        return cost;
    }
}
