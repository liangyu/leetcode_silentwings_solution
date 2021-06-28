package LC1801_2100;
import java.util.*;
public class LC1913_MaximumProductDifferenceBetweenTwoPairs {
    /**
     * The product difference between two pairs (a, b) and (c, d) is defined as (a * b) - (c * d).
     *
     * For example, the product difference between (5, 6) and (2, 7) is (5 * 6) - (2 * 7) = 16.
     * Given an integer array nums, choose four distinct indices w, x, y, and z such that the product difference
     * between pairs (nums[w], nums[x]) and (nums[y], nums[z]) is maximized.
     *
     * Return the maximum such product difference.
     *
     * Input: nums = [5,6,2,7,4]
     * Output: 34
     *
     * Constraints:
     *
     * 4 <= nums.length <= 10^4
     * 1 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(1)
    public int maxProductDifference(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        Arrays.sort(nums);
        return nums[n - 1] * nums[n - 2] - nums[1] * nums[0];
    }

    // S2: find max (最优解！)
    // time = O(n), space = O(1)
    public int maxProductDifference2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int max1 = 0, max2 = 0, min1 = 10001, min2 = 10001;
        for (int n : nums) {
            if (n >= max1) {
                max2 = max1;
                max1 = n;
            } else if (n > max2) max2 = n;
            if (n <= min1) {
                min2 = min1;
                min1 = n;
            } else if ( n < min2) min2 = n;
        }
        return max1 * max2 - min1 * min2;
    }
}
