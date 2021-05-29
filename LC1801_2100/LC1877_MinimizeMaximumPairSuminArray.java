package LC1801_2100;
import java.util.*;
public class LC1877_MinimizeMaximumPairSuminArray {
    /**
     * The pair sum of a pair (a,b) is equal to a + b. The maximum pair sum is the largest pair sum in a list of pairs.
     *
     * For example, if we have pairs (1,5), (2,3), and (4,4), the maximum pair sum would be
     * max(1+5, 2+3, 4+4) = max(6, 5, 8) = 8.
     * Given an array nums of even length n, pair up the elements of nums into n / 2 pairs such that:
     *
     * Each element of nums is in exactly one pair, and
     * The maximum pair sum is minimized.
     * Return the minimized maximum pair sum after optimally pairing up the elements.
     *
     * Input: nums = [3,5,2,3]
     * Output: 7
     *
     * Constraints:
     *
     * n == nums.length
     * 2 <= n <= 10^5
     * n is even.
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int minPairSum(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);

        int n = nums.length, res = 0;
        int left = 0, right = n - 1;
        while (left < right) res = Math.max(res, nums[left++] + nums[right--]);
        return res;
    }
}
