package LC1801_2100;
import java.util.*;
public class LC1879_MinimumXORSumofTwoArrays {
    /**
     * You are given two integer arrays nums1 and nums2 of length n.
     *
     * The XOR sum of the two integer arrays is (nums1[0] XOR nums2[0]) + (nums1[1] XOR nums2[1]) + ... + (nums1[n - 1]
     * XOR nums2[n - 1]) (0-indexed).
     *
     * For example, the XOR sum of [1,2,3] and [3,2,1] is equal to (1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4.
     * Rearrange the elements of nums2 such that the resulting XOR sum is minimized.
     *
     * Return the XOR sum after the rearrangement.
     *
     * Input: nums1 = [1,2], nums2 = [2,3]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums1.length
     * n == nums2.length
     * 1 <= n <= 14
     * 0 <= nums1[i], nums2[i] <= 10^7
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(n * 2^n), space = O(n)
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        return dfs(dp, nums1, nums2, 0, 0);
    }

    private int dfs(int[] dp, int[] nums1, int[] nums2, int cur, int mask) {
        int n = nums1.length;
        // base case
        if (cur == n) return 0;
        if (dp[mask] != Integer.MAX_VALUE) return dp[mask];

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) {
                dp[mask] = Math.min(dp[mask], (nums1[cur] ^ nums2[i]) + dfs(dp, nums1, nums2, cur + 1, mask + (1 << i)));
            }
        }
        return dp[mask];
    }
}
/**
 *
 */
