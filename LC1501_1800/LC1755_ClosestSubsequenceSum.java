package LC1501_1800;
import java.util.*;
public class LC1755_ClosestSubsequenceSum {
    /**
     * You are given an integer array nums and an integer goal.
     *
     * You want to choose a subsequence of nums such that the sum of its elements is the closest possible to goal.
     * That is, if the sum of the subsequence's elements is sum, then you want to minimize the absolute difference
     * abs(sum - goal).
     *
     * Return the minimum possible value of abs(sum - goal).
     *
     * Note that a subsequence of an array is an array formed by removing some elements (possibly all or none) of the
     * original array.
     *
     * Input: nums = [5,-7,3,5], goal = 6
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 40
     * 10^7 <= nums[i] <= 10^7
     * -10^9 <= goal <= 10^9
     * @param nums
     * @param goal
     * @return
     */
    // time = O(2^(n/2) + nlogn), space = O(n)
    public int minAbsDifference(int[] nums, int goal) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        HashSet<Integer> first = new HashSet<>();
        HashSet<Integer> second = new HashSet<>();

        helper(nums, 0, n / 2, 0, first);
        helper(nums, n / 2, n, 0, second);

        TreeSet<Integer> treeSet = new TreeSet<>(first);

        int res = Integer.MAX_VALUE;
        for (int t : second) {
            int diff = goal - t;
            if (treeSet.contains(diff)) return 0;
            Integer lk = treeSet.lower(diff);
            Integer hk = treeSet.higher(diff);
            if (lk != null) res = Math.min(res, Math.abs(t + lk - goal));
            if (hk != null) res = Math.min(res, Math.abs(t + hk - goal));
        }
        return res;
    }

    private void helper(int[] nums, int cur, int end, int sum, HashSet<Integer> set) {
        if (cur == end) {
            set.add(sum);
            return;
        }

        helper(nums, cur + 1, end, sum + nums[cur], set); // take
        helper(nums, cur + 1, end, sum, set); // not take
    }
}
/**
 * pos = -insertionPoint - 1 => insertionPoint = -pos - 1 = -1 * (pos + 1)
 */