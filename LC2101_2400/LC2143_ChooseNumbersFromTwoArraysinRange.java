package LC2101_2400;

public class LC2143_ChooseNumbersFromTwoArraysinRange {
    /**
     * You are given two 0-indexed integer arrays nums1 and nums2 of length n.
     *
     * A range [l, r] (inclusive) where 0 <= l <= r < n is balanced if:
     *
     * For every i in the range [l, r], you pick either nums1[i] or nums2[i].
     * The sum of the numbers you pick from nums1 equals to the sum of the numbers you pick from nums2 (the sum is
     * considered to be 0 if you pick no numbers from an array).
     * Two balanced ranges from [l1, r1] and [l2, r2] are considered to be different if at least one of the following
     * is true:
     *
     * l1 != l2
     * r1 != r2
     * nums1[i] is picked in the first range, and nums2[i] is picked in the second range or vice versa for at least one i.
     * Return the number of different ranges that are balanced. Since the answer may be very large, return it modulo
     * 10^9 + 7.
     *
     * Input: nums1 = [1,2,5], nums2 = [2,6,3]
     * Output: 3
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 100
     * 0 <= nums1[i], nums2[i] <= 100
     * @param nums1
     * @param nums2
     * @return
     */
    int[] nums1, nums2;
    Integer[][] dp;
    int n;
    long M = (long)(1e9 + 7);
    public int countSubranges(int[] nums1, int[] nums2) {
        n = nums1.length;
        dp = new Integer[n][10001];
        this.nums1 = nums1;
        this.nums2 = nums2;
        long res = 0;

        for (int i = 0; i < n; i++) {
            res = (res + helper(i + 1, nums1[i]) + helper(i + 1, -nums2[i])) % M;
        }
        return (int) res;
    }

    private int helper(int idx, int sum) {
        if (idx == n) return sum == 0 ? 1 : 0;
        int key = sum + 5000;
        if (key < 0 || key > 10000) return 0;
        if (dp[idx][key] != null) return (Integer) dp[idx][key];
        int val1 = helper(idx + 1, sum + nums1[idx]);
        int val2 = helper(idx + 1, sum - nums2[idx]);
        dp[idx][key] = ((sum == 0 ? 1 : 0) + val1 + val2) % (int) M;
        return dp[idx][key];
    }
}
