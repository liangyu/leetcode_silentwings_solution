package LC001_300;
import java.util.*;
public class LC198_HouseRobber {
    /**
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money
     * stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems
     * connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
     *
     * Given an integer array nums representing the amount of money of each house, return the maximum amount of money
     * you can rob tonight without alerting the police.
     *
     * Input: nums = [1,2,3,1]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 400
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public int rob(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int n = nums.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[n];
    }

    // S1.2: rolling matrix
    // time = O(n), space = O(1)
    public int rob2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int n = nums.length;
        int old = 0, now = nums[0];

        for (int i = 2; i <= n; i++) {
            int dp = Math.max(now, old + nums[i - 1]);
            old = now;
            now = dp;
        }
        return now;
    }

    // S2: dp
    // time = O(n), space = O(1)
    public int rob3(int[] nums) {
        int preNo = 0, preYes = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = preNo;
            preNo = Math.max(preNo, preYes);
            preYes = temp + nums[i];
        }
        return Math.max(preNo, preYes);
    }

    // S3: dp
    // time = O(n), space = O(1)
    public int rob4(int[] nums) {
        int n = nums.length;
        int rob = 0, norob = 0;
        for (int i = 0; i < n; i++) {
            int rob_temp = rob, norob_temp = norob;
            norob = Math.max(rob_temp, norob_temp);
            rob = norob_temp + nums[i];
        }
        return Math.max(rob, norob);
    }
}
/**
 * 这是“双状态”DP最典型的一道题。
 * 它的特点是：每一轮的状态只取决于上一轮的状态；并且每一轮的状态只有两种，通常就是“取”或“不取”。
 * 结合本题来说，是否打劫第k间房子（的收益），完全取决于是否打劫第k-1间房子（的收益）。
 * 令rob[k]表示打劫第k间的最大收益，norob[k]表示不打劫第k间的最大收益。
 * 我们可以分析出相邻两轮状态之间的递推关系：
 * rob[k] = norob[k-1];  // 想要打劫第k间房子，必须基于第k-1间房子没打劫。
 * norob[k] = max(rob[k-1],norob[k-1])+nums[k]; // 不打劫第k间房子的最大收益，显然对应了rob[k-1],norob[k-1]之间较大的值。
 * 对于初始状态，我们可以直接考虑第0间房子是否打劫及其收益。这样状态转移方程可以从第1间房子开始适用。
 * choose 3,  not choose 4 =>
 * not choose 3,  choose 4 =>
 *                not choose 4 =>
 * dp[not choose 4] => max(dp[choose 3], dp[not choose 3]) + 0
 * dp[choose 4] => dp[not choose 3]
 * 每一轮只有2种状态，选或者不选，决定了下一轮的状态，选或者不选
 * dp[not choose 4]: maximum amount of money you can rob when you do not choose 4
 * dp[choose 4]: maximum amount of money you can rob when you choose 4
 * 边界条件单独处理下
 */

