package LC1801_2100;
import java.util.*;
public class LC1984_MinimumDifferenceBetweenHighestandLowestofKScores {
    /**
     * You are given a 0-indexed integer array nums, where nums[i] represents the score of the ith student. You are
     * also given an integer k.
     *
     * Pick the scores of any k students from the array so that the difference between the highest and the lowest of
     * the k scores is minimized.
     *
     * Return the minimum possible difference.
     *
     * Input: nums = [90], k = 1
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 1000
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int minimumDifference(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return 0;

        Arrays.sort(nums);

        int n = nums.length, res = Integer.MAX_VALUE;
        for (int i = 0; i <= n - k; i++) {
            res = Math.min(res, nums[i + k - 1] - nums[i]);
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
