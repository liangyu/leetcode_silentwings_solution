package LC1801_2100;
import java.util.*;
public class LC2090_KRadiusSubarrayAverages {
    /**
     * You are given a 0-indexed array nums of n integers, and an integer k.
     *
     * The k-radius average for a subarray of nums centered at some index i with the radius k is the average of all
     * elements in nums between the indices i - k and i + k (inclusive). If there are less than k elements before or
     * after the index i, then the k-radius average is -1.
     *
     * Build and return an array avgs of length n where avgs[i] is the k-radius average for the subarray centered at
     * index i.
     *
     * The average of x elements is the sum of the x elements divided by x, using integer division. The integer division
     * truncates toward zero, which means losing its fractional part.
     *
     * For example, the average of four elements 2, 3, 1, and 5 is (2 + 3 + 1 + 5) / 4 = 11 / 4 = 3.75, which truncates
     * to 3.
     *
     * Input: nums = [7,4,3,9,1,8,5,2,6], k = 3
     * Output: [-1,-1,-1,5,4,4,-1,-1,-1]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * 0 <= nums[i], k <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        int[] res = new int[n];
        Arrays.fill(res, -1);
        int divisor = 2 * k + 1;
        for (int i = k + 1; i + k <= n; i++) {
            long sum = presum[i + k] - presum[i - k - 1];
            res[i - 1] = (int)(sum / divisor);
        }
        return res;
    }
}
