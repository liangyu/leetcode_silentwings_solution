package LC1801_2100;

public class LC1955_CountNumberofSpecialSubsequences {
    /**
     * A sequence is special if it consists of a positive number of 0s, followed by a positive number of 1s, then a
     * positive number of 2s.
     *
     * For example, [0,1,2] and [0,0,1,1,1,2] are special.
     * In contrast, [2,1,0], [1], and [0,1,2,0] are not special.
     * Given an array nums (consisting of only integers 0, 1, and 2), return the number of different subsequences that
     * are special. Since the answer may be very large, return it modulo 109 + 7.
     *
     * A subsequence of an array is a sequence that can be derived from the array by deleting some or no elements
     * without changing the order of the remaining elements. Two subsequences are different if the set of indices
     * chosen are different.
     *
     * Input: nums = [0,1,2,2]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 2
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int countSpecialSubsequences(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        long M = (long)(1e9 + 7);
        long[] dp = new long[3];
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) dp[0] = dp[0] * 2 % M;
            else if (nums[i] == 1) dp[1] = (dp[0] - 1 + dp[1] * 2) % M;
            else dp[2] = (dp[1] + 2 * dp[2]) % M;
        }
        return (int)dp[2];
    }
}
/**
 * x x x | x x x x x | x x x  => x -> ? -> ?
 * 0
 * 01
 * 012
 * [x x x x x 0] x x x
 * 末尾是0，不会接到现有的012上，只能延长若干个纯0序列
 * dp[0] = 1;
 * {0}
 * {   0}
 * {0, 0}
 * {} X
 * for i = 0 ~ n - 1
 * if (nums[i] == 0)
 *      dp[0] = dp[0] * 2;
 * else if (nums[i] == 1) {
 *     dp[1] = dp[0] - 1 + dp[1] * 2; // 去掉空集的纯0序列 -> reliable
 * } else if (nums[i] == 2) {
 *     dp[2] = dp[1] + dp[2] * 2;
 * }
 * return dp[2];
 */
