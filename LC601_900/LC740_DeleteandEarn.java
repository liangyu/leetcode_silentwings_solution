package LC601_900;
import java.util.*;
public class LC740_DeleteandEarn {
    /**
     * You are given an integer array nums. You want to maximize the number of points you get by performing the
     * following operation any number of times:
     *
     * Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must delete every element equal to
     * nums[i] - 1 and every element equal to nums[i] + 1.
     * Return the maximum number of points you can earn by applying the above operation some number of times.
     *
     * Input: nums = [3,4,2]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // S1
    // time = O(nlogn), space = O(m)
    public int deleteAndEarn(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);
        int n = nums.length, m = nums[n - 1];
        int[] gain = new int[m + 1];
        for (int x : nums) gain[x] += x;

        int p = 0; // p[i]: maximum gain by earning i
        int q = 0; // q[i]: maximum gain by not earning i

        for (int i = 1; i <= m; i++) {
            int p2 = p, q2 = q;
            p = q2 + gain[i];
            q = Math.max(p2, q2);
        }
        return Math.max(p, q);
    }

    // S2
    // time = O(nlogn), space = O(m)
    public int deleteAndEarn2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);
        int n = nums.length, m = nums[n - 1];
        int[] gain = new int[m + 1];
        for (int x : nums) gain[x] += x;

        int[] dp = new int[m + 1]; // dp[i]: maximum gain by ending with i
        dp[1] = gain[1];
        for (int i = 2; i <= m; i++) { // 数字从小到大遍历
            dp[i] = Math.max(dp[i - 2] + gain[i], dp[i - 1]);
        }
        return dp[m];
    }
}
/**
 * [2,2,{3,3,3},4]
 * 1 2 3 4 5 6 7 8 9 ...
 * p[3]: maximum gain by earning 3
 * q[3]: maximum gain by not earning 3
 * p[3] = q[2] + gain[3];
 * q[3] = Math.max(q[2], p[2]);
 * dp[i] = Math.max(dp[i - 2] + gain[i], dp[i - 1])
 */