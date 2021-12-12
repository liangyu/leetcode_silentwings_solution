package LC2100_2400;

public class LC2104_SumofSubarrayRanges {
    /**
     * You are given an integer array nums. The range of a subarray of nums is the difference between the largest and
     * smallest element in the subarray.
     *
     * Return the sum of all subarray ranges of nums.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *
     * Input: nums = [1,2,3]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(n^2), space = O(1)
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        long res = 0;
        for (int i = 0; i < n; i++) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int j = i; j < n; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                res += max - min;
            }
        }
        return res;
    }
}
