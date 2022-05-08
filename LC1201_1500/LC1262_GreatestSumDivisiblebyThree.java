package LC1201_1500;
import java.util.*;
public class LC1262_GreatestSumDivisiblebyThree {
    /**
     * Given an array nums of integers, we need to find the maximum possible sum of elements of the array such that it
     * is divisible by three.
     *
     * Input: nums = [3,6,5,1,8]
     * Output: 18
     *
     * Constraints:
     *
     * 1 <= nums.length <= 4 * 10^4
     * 1 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // S1: dp
    // time = O(n), space = O(1)
    public int maxSumDivThree(int[] nums) {
        int n = nums.length;
        int[] dp = new int[3];

        // init
        dp[0] = 0;
        dp[1] = Integer.MIN_VALUE;
        dp[2] = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int k = nums[i] % 3;
            int[] dp_temp = dp.clone();
            for (int j = 0; j < 3; j++) {
                dp[j] = Math.max(dp_temp[j], dp_temp[(j - k + 3) % 3] + nums[i]);
            }
        }
        return dp[0];
    }

    // S2: 2D dp + rolling matrix
    // time = O(n), space = O(1)
    public int maxSumDivThree2(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[2][3];

        // init
        for (int i = 0; i <= 1; i++) Arrays.fill(dp[i], Integer.MIN_VALUE);
        dp[0][0] = 0;


        for (int i = 1; i <= n; i++) {
            int k = nums[i - 1] % 3;
            for (int j = 0; j < 3; j++) {
                dp[i & 1][j] = Math.max(dp[i - 1 & 1][j], dp[i - 1 & 1][(j - k + 3) % 3] + nums[i - 1]);
            }
        }
        return dp[n & 1][0] == Integer.MIN_VALUE / 2 ? 0 : dp[n & 1][0];
    }
}
/**
 * 数学解：
 * 把nums分成三组
 * 我们将所有的数字分为三类，有a个被3整除的，有b个被3除余1的，有c个被3除余2的。
 * 显然所有的被3整除的数都可以用。
 * 剩下两类的数字我们分别取多少呢？
 * 我们可以枚举一下配对的可能：前者的数目m可以是b,b-1,b-2，后者的数目n可以是c,c-1,c-2.
 * 配对成功的要求是(1*m+2*n)%3==0.
 * 如果配对成功，我们自然取的是被3除余1的最大的m个数、被3除余2的最大的n个数。
 * 枚举，3x3 = 9种可能
 * if total%3 == 0 return all
 * if total%3 == 1
 *                 delete 1  %3==1
 *                 delete 2  %3==2
 *                 delete 1  %3==2, 2 %3 ==1
 * if total%3 == 2
 *                 delete 2 %3==1
 *                 delete 1 %3==2
 *                 delete 2 %3==2  1  %3 ==1
 * S2: dp
 * dp[i][j]: the maximum possible sum (%3==j) in the first i elements
 * x x x x x i
 * pick nums[i]
 *      if nums[i] % 3 == 0,
 *          dp[i][0] = dp[i-1][0] + nums[i]
 *          dp[i][1] = dp[i-1][1] + nums[i]
 *          dp[i][2] = dp[i-1][2] + nums[i]
 *      if nums[i] % 3 == 1,
 *          dp[i][0] = dp[i-1][2] + nums[i]
 *          dp[i][1] = dp[i-1][0] + nums[i]
 *          dp[i][2] = dp[i-1][1] + nums[i]
 *      if nums[i] % 3 == 2,
 *          dp[i][0] = dp[i-1][1] + nums[i]
 *          dp[i][1] = dp[i-1][2] + nums[i]
 *          dp[i][2] = dp[i-1][0] + nums[i]
 * do not pick nums[i]
 *      dp[i][j] = dp[i-1][j]
 */
