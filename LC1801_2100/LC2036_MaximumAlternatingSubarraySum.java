package LC1801_2100;

public class LC2036_MaximumAlternatingSubarraySum {
    /**
     * A subarray of a 0-indexed integer array is a contiguous non-empty sequence of elements within an array.
     *
     * The alternating subarray sum of a subarray that ranges from index i to j (inclusive, 0 <= i <= j < nums.length)
     * is nums[i] - nums[i+1] + nums[i+2] - ... +/- nums[j].
     *
     * Given a 0-indexed integer array nums, return the maximum alternating subarray sum of any subarray of nums.
     *
     * Input: nums = [3,-1,1,2]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -105 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public long maximumAlternatingSubarraySum(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        long res = Long.MIN_VALUE, odd = 0, even = 0;
        for (int i = 0; i < n; i++) { // start with even index
            if (i % 2 == 0) even += nums[i];
            else even = Math.max(0, even - nums[i]); // if sum < 0, then discard all previous ones, start with 0 again
            res = Math.max(even, res);
        }

        for (int i = 1; i < n; i++) { // start with odd index
            if (i % 2 == 1) odd += nums[i];
            else odd = Math.max(odd - nums[i], 0);
            res = Math.max(odd, res);
        }
        return res;
    }
}
