package LC301_600;

public class LC494_TargetSum {
    /**
     * You are given an integer array nums and an integer target.
     *
     * You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums
     * and then concatenate all the integers.
     *
     * For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the
     * expression "+2-1".
     * Return the number of different expressions that you can build, which evaluates to target.
     *
     * Input: nums = [1,1,1,1,1], target = 3
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 20
     * 0 <= nums[i] <= 1000
     * 0 <= sum(nums[i]) <= 1000
     * -1000 <= target <= 1000
     * @param nums
     * @param target
     * @return
     */
    // time = O(n * k), space = O(n * k)  k: sum of the array
    public int findTargetSumWays(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, sum = 0;
        for (int x : nums) sum += x;
        if (target > sum || target < -sum) return 0;

        int[][] dp = new int[n + 1][2 * sum + 1];
        dp[0][sum] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = -sum; j <= sum; j++) {
                if (j - nums[i - 1] >= -sum) dp[i][j + sum] += dp[i - 1][j + sum - nums[i - 1]];
                if (j + nums[i - 1] <= sum) dp[i][j + sum] += dp[i - 1][j + sum + nums[i - 1]];
            }
        }
        return dp[n][target + sum];
    }
}
/**
 * dp[x]  x < 1000
 * 状态数组  dp[i][x] 考虑到第i个的时候
 * dp[i][x] = dp[i-1][...]
 * s s+1 / s-1
 * n  n     n
 * dp[i][s+1] += dp[i-1][s]
 * dp[i][s-1] += dp[i-1][s]
 * why use dp[sum]? 避免重复
 * 1 1 1 1  => 2^4 = 16
 *            4, -4 => 9
 *            N, -N = 2N
 * dp[i][5] : dp[i][s+nums[i]]
 *            dp[i][s'+nums[i']]
 *
 * dp状态表示：
 * 集合：前i个数总和为j的所有方案的集合
 * 属性：数量
 * 状态计算：
 * ai取正：f(i-1,j-ai)
 * ai取负：f(i-1,j+ai)
 * f(0,0) = 1
 */
