package LC1801_2100;
import java.util.*;
public class LC2031_CountSubarraysWithMoreOnesThanZeros {
    /**
     * You are given a binary array nums containing only the integers 0 and 1. Return the number of subarrays in nums
     * that have more 1's than 0's. Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * A subarray is a contiguous sequence of elements within an array.
     *
     * Input: nums = [0,1,1,0,1]
     * Output: 9
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 1
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int subarraysWithMoreZerosThanOnes(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, sum = 0;
        long res = 0;
        long M = (long)(1e9 + 7);
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            sum += nums[i] == 0 ? -1 : 1;
            Integer pre = map.get(sum);
            if (pre == null) {
                if (sum > 0) dp[i] = i + 1;
                else if (sum == 0 && nums[i] > 0) dp[i] = i;
            } else {
                dp[i] = dp[pre];
                if (nums[i] > 0) dp[i] += i - pre - 1;
            }
            res += dp[i];
            map.put(sum, i);
        }
        return (int)(res % M);
    }
}
