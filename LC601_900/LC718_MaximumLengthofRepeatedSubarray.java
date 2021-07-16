package LC601_900;

public class LC718_MaximumLengthofRepeatedSubarray {
    /**
     * Given two integer arrays nums1 and nums2, return the maximum length of a subarray that appears in both arrays.
     *
     * Input: nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 100
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp= new int[m + 1][n + 1];
        int res = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }
}
/**
 * longest common subarray
 * longest common subsequence (LCS): DP
 * dp[i][j]: the longest common subarray that ends at A[i] and ends at B[j]
 * xx[xxx i]
 * yyyyy[yyy j]
 * dp[i][j] => dp[i-1][j-1]+1 if A[i] == b[j] else 0
 *
 * for LCS:
 * dp[i][j] => dp[i-1][j-1]+1 if A[i] == b[j]
 * dp[i][j] => Math.max(dp[i-1][j], dp[i][j-1]) if A[i] != b[j]
 */