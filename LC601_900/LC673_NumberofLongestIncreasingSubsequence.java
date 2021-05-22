package LC601_900;
import java.util.*;
public class LC673_NumberofLongestIncreasingSubsequence {
    /**
     * Given an unsorted array of integers, find the number of longest increasing subsequence.
     *
     * Example 1:
     * Input: [1,3,5,4,7]
     * Output: 2
     * Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
     *
     * Example 2:
     * Input: [2,2,2,2,2]
     * Output: 5
     * Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length
     * is 1, so output 5.
     *
     * Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be fit in 32-bit signed
     * int.
     *
     * @param nums
     * @return
     */
    // time = O(n^2), space = O(n)
    public int findNumberOfLIS(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int len = nums.length;
        int[] dp = new int[len];
        int[] counter = new int[len];
        Arrays.fill(dp, 1);
        Arrays.fill(counter, 1);
        int max = 1;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                        counter[i] = counter[j];
                    } else if (dp[i] == dp[j] + 1) {
                        counter[i] += counter[j];
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }
        int res = 0;
        for (int i = 0; i < len; i++) {
            if (dp[i] == max) res += counter[i];
        }
        return res;
    }
}
