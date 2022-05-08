package LC1801_2100;
import java.util.*;
public class LC2044_CountNumberofMaximumBitwiseORSubsets {
    /**
     * Given an integer array nums, find the maximum possible bitwise OR of a subset of nums and return the number of
     * different non-empty subsets with the maximum bitwise OR.
     *
     * An array a is a subset of an array b if a can be obtained from b by deleting some (possibly zero) elements of b.
     * Two subsets are considered different if the indices of the elements chosen are different.
     *
     * The bitwise OR of an array a is equal to a[0] OR a[1] OR ... OR a[a.length - 1] (0-indexed).
     *
     * Input: nums = [3,1]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 16
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n * 2^17), space = O(2^17)
    public int countMaxOrSubsets(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, M = (1 << n);
        int[] dp = new int[M + 1]; // dp[val]: how many subsets whose bitwise or == val
        dp[0] = 1;

        for (int x : nums) {
            int[] dp_old = dp.clone();
//            Arrays.fill(dp, 0); // 不清零就直接继承 -> 根据无后效性，过去推将来，一定要每轮给当前的dp备份后清零！！！
            for (int val = 0; val < M; val++) {
//                dp[val] += dp_old[val];
                dp[val | x] += dp_old[val];
            }
        }

        for (int val = M; val >= 0; val--) {
            if (dp[val] != 0) return dp[val];
        }
        return 0;
    }

    // S2: bitmask
    // time = O(2^n), space = O(1）
    public int countMaxOrSubsets2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int max = 0, n = nums.length;
        for (int x : nums) max |= x;

        int count = 0;
        for (int state = 0; state < (1 << n); state++) {
            int val = 0;
            for (int i = 0; i < n; i++) {
                if (((state >> i) & 1) == 1) {
                    val |= nums[i];
                }
            }
            if (val == max) count++;
        }
        return count;
    }

    // S3: DFS (better solution)
    // time = O(2^n), space = O(n)
    private int res = 0;
    public int countMaxOrSubsets3(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int max = 0;
        for (int x : nums) max |= x;

        dfs(nums, 0, max, 0);
        return res;
    }

    private void dfs(int[] nums, int idx, int max, int cur) {
        // base case
        if (cur == max) res++;

        for (int i = idx; i < nums.length; i++) {
            dfs(nums, i + 1, max, cur | nums[i]);
        }
    }
}
/**
 * 只有16个数
 * 用17个比特位就能把nums代表了
 * 可以枚举所有结果
 * 把所有结果都遍历一遍，每加入一个数，看对结果有什么更新
 * dp[i][val]: how many subsets whose bitwise or == val using nums[0:i]  2^17
 * 最多只要做16次更新就可以了
 * 从过去求现在：
 * dp[i][val] = ...... dp[i-1][v1] + dp[i-2][v2] + ... + dp[i-1][vk]
 *                      v1 | x = val
 * find all vi s.t. vi | x = val
 * 求逆运算
 * dp[i][val] => dp[i][val] += dp[i-1][val]
 *               dp[i][val|x] += dp[i-1][val]
 * 这样考虑，从现在推将来，只要做2步就行
 * 枚举过去无法全部穷举的时候，可以考虑反方向来做
 */
