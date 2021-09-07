package LC901_1200;

public class LC1043_PartitionArrayforMaximumSum {
    /**
     * Given an integer array arr, partition the array into (contiguous) subarrays of length at most k. After
     * partitioning, each subarray has their values changed to become the maximum value of that subarray.
     *
     * Return the largest sum of the given array after partitioning. Test cases are generated so that the answer fits
     * in a 32-bit integer.
     *
     * Input: arr = [1,15,7,9,2,5,10], k = 3
     * Output: 84
     *
     * Constraints:
     *
     * 1 <= arr.length <= 500
     * 0 <= arr[i] <= 10^9
     * 1 <= k <= arr.length
     * @param arr
     * @param k
     * @return
     */
    // time = O(n * k), space = O(n)
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int max = 0;
            // update dp[i]
            for (int j = i; j >= Math.max(i - k + 1, 1); j--) {
                max = Math.max(max, arr[j - 1]);
                dp[i] = Math.max(dp[i], dp[j - 1] + max * (i - j + 1));
            }
        }
        return dp[n];
    }
}
/**
 * dp[i]: the largest sum of the given array A[0:i] after partitioning
 * {x x x x} [j x i]
 * dp[j-1]
 * dp[i] = max{dp[j-1] + max*(i-j+1)} for j=i, i-1, ... i-k+1
 */
