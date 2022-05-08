package LC301_600;

public class LC396_RotateFunction {
    /**
     * You are given an integer array nums of length n.
     *
     * Assume arrk to be an array obtained by rotating nums by k positions clock-wise. We define the rotation function
     * F on nums as follow:
     *
     * F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1].
     * Return the maximum value of F(0), F(1), ..., F(n-1).
     *
     * The test cases are generated so that the answer fits in a 32-bit integer.
     *
     * Input: nums = [4,3,2,6]
     * Output: 26
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * -100 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int maxRotateFunction(int[] nums) {
        int n = nums.length, sum = 0, total = 0;
        for (int x : nums) sum += x;
        for (int i = 0; i < n; i++) {
            total += i * nums[i];
        }

        int res = total;
        for (int i = 1; i < n; i++) {
            total += sum - n * nums[n - i];
            res = Math.max(res, total);
        }
        return res;
    }
}
