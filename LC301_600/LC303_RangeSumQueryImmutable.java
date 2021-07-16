package LC301_600;

public class LC303_RangeSumQueryImmutable {
    /**
     * Given an integer array nums, handle multiple queries of the following type:
     *
     * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
     * Implement the NumArray class:
     *
     * NumArray(int[] nums) Initializes the object with the integer array nums.
     * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right
     * inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
     *
     * Input
     * ["NumArray", "sumRange", "sumRange", "sumRange"]
     * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
     * Output
     * [null, 1, -1, -3]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -10^5 <= nums[i] <= 10^5
     * 0 <= left <= right < nums.length
     * At most 10^4 calls will be made to sumRange.
     * @param nums
     */
    // time = O(n), space = O(n)
    private int[] presum;
    public LC303_RangeSumQueryImmutable(int[] nums) {
        int n = nums.length;
        presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];
    }

    public int sumRange(int left, int right) {
        return presum[right + 1] - presum[left];
    }
}
