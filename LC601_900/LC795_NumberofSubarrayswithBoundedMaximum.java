package LC601_900;
import java.util.*;
public class LC795_NumberofSubarrayswithBoundedMaximum {
    /**
     * We are given an array nums of positive integers, and two positive integers left and right (left <= right).
     *
     * Return the number of (contiguous, non-empty) subarrays such that the value of the maximum array element in that
     * subarray is at least left and at most right.
     *
     * Input:
     * nums = [2, 1, 4, 3]
     * left = 2
     * right = 3
     * Output: 3
     *
     * Note:
     *
     * left, right, and nums[i] will be an integer in the range [0, 10^9].
     * The length of nums will be in the range of [1, 50000].
     * @param nums
     * @param left
     * @param right
     * @return
     */
    // time = O(n), space = O(1)
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        // corner case
        if (nums == null || nums.length == 0 ) return 0;

        return helper(nums, right) - helper(nums, left - 1);
    }

    private int helper(int[] nums, int limit) {
        int res = 0, cur = 0;
        for (int n : nums) {
            cur = n <= limit ? cur + 1 : 0;
            res += cur;
        }
        return res;
    }
}
