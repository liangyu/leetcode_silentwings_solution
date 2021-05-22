package LC001_300;
import java.util.*;
public class LC152_MaximumProductSubarray {
    /**
     * Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product,
     * and return the product.
     *
     * It is guaranteed that the answer will fit in a 32-bit integer.
     *
     * A subarray is a contiguous subsequence of the array.
     *
     * Input: nums = [2,3,-2,4]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * -10 <= nums[i] <= 10
     * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int maxProduct(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int res = nums[0];
        int max = nums[0];
        int min = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int temp = max;
            max = Math.max(Math.max(max * nums[i], min * nums[i]), nums[i]);
            min = Math.min(Math.min(temp * nums[i], min * nums[i]), nums[i]);
            res = Math.max(res, max);
        }
        return res;
    }
}
