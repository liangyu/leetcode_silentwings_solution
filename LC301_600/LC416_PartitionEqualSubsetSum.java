package LC301_600;
import java.util.*;
public class LC416_PartitionEqualSubsetSum {
    /**
     * Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two
     * subsets such that the sum of elements in both subsets is equal.
     *
     * Input: nums = [1,5,11,5]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // S1： DP
    // time = O(n * s), space = O(n * s)
    public boolean canPartition(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;

        boolean[] dp = new boolean[sum / 2 + 1];
        dp[0] = true;

        for (int x : nums) {
            boolean[] dp2 = dp.clone();
            for (int s = 0; s <= sum / 2; s++) {
                if (s - x >= 0 && dp2[s - x]) {
                    dp[s] = true;
                }
            }
        }
        return dp[sum / 2];
    }

    // S1.2： DP
    // time = O(n * s), space = O(s)
    public boolean canPartition2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;

        boolean[] dp = new boolean[sum / 2 + 1];
        dp[0] = true;

        for (int x : nums) {
            for (int s = sum / 2; s >= 0; s--) {  // 从大到小更新，就能避免上面由于小的先更新导致大的更新混乱的情况
                if (s - x >= 0 && dp[s - x]) {
                    dp[s] = true;
                }
            }
        }
        return dp[sum / 2];
    }

    // S2: DP
    // time = O(n * s), space = O(n * s)
    public boolean canPartition3(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int n = nums.length;
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int k = sum / 2;

        boolean[][] dp = new boolean[n + 1][k + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] |= dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    dp[i][j] |= dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][k];
    }

    // S3: Set + DP
    // time = O(n * s), space = O(n * s)
    public boolean canPartition4(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        int n = nums.length;
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int k = sum / 2;

        HashSet<Integer> dp = new HashSet<>();
        dp.add(0); // 加入dp的都是true

        for (int x : nums) {
            List<Integer> temp = new ArrayList<>();
            for (int s : dp) {
                if (s + x == sum / 2) return true;
                temp.add(s + x);
            }
            dp.addAll(temp);
        }
        return false;
    }
}
/**
 * 本身是个NP问题，本质上还是个搜索。
 * dfs是种比较显然的想法，代价就是时间复杂度比较高
 * 你每个数都要试一下，本质就是O(2^n) => TLE
 * 扫一下数据范围，莫名有点小
 * 本质上是个动态规划，01背包问题，要么选要么不选
 * 01背包问题：切入点和思考角度不一样
 * dfs:在高维上搜索解答
 * (1,0,0,1,...,1,0,0)  subset sum = sum / 2  => 大海捞针 2^n
 * s = subset sum
 * dp[s]: whether we can find a subset whose sum equals to s
 * 0 ~ 2*10^4
 * dp[s_small] => dp[s_large]
 * dp[10] = true => dp[20] = true
 * 1 4 6, 10
 *
 * 01背包模板：
 * for (int x : nums) {  // 遍历物品
 *     for (int s = 0; s <= sum / 2; s++) { // 遍历容量
 *         if (dp'[s - x]) {
 *             dp[s] = true;
 *         }
 *     }
 * }
 *
 * dp[100000] => 稀疏，可以改用set来做
 */