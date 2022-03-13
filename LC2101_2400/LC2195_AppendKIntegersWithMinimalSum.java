package LC2101_2400;
import java.util.*;
public class LC2195_AppendKIntegersWithMinimalSum {
    /**
     * You are given an integer array nums and an integer k. Append k unique positive integers that do not appear in
     * nums to nums such that the resulting total sum is minimum.
     *
     * Return the sum of the k integers appended to nums.
     *
     * Input: nums = [1,4,25,10,25], k = 2
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i], k <= 10^9
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public long minimalKSum(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long sum = 0, prev = 1;
        for (int i = 0; i < n; i++) {
            if (nums[i] > prev) {
                long diff = nums[i] - prev;
                if (diff >= k) {
                    sum += (prev + prev + k - 1) * k / 2;
                    k = 0;
                } else {
                    sum += (prev + nums[i] - 1) * diff / 2;
                    k -= diff;
                }
            }
            prev = nums[i] + 1;
            if (k == 0) break;
        }
        if (k > 0) sum += (prev + prev + k - 1) * k / 2;
        return sum;
    }
}
