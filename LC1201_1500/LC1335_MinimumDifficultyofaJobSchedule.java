package LC1201_1500;
import java.util.*;
public class LC1335_MinimumDifficultyofaJobSchedule {
    /**
     * You want to schedule a list of jobs in d days. Jobs are dependent (i.e To work on the ith job, you have to finish
     * all the jobs j where 0 <= j < i).
     *
     * You have to finish at least one task every day. The difficulty of a job schedule is the sum of difficulties of
     * each day of the d days. The difficulty of a day is the maximum difficulty of a job done on that day.
     *
     * You are given an integer array jobDifficulty and an integer d. The difficulty of the ith job is jobDifficulty[i].
     *
     * Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
     *
     * Input: jobDifficulty = [6,5,4,3,2,1], d = 2
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= jobDifficulty.length <= 300
     * 0 <= jobDifficulty[i] <= 1000
     * 1 <= d <= 10
     * @param jobDifficulty
     * @param d
     * @return
     */
    // S1: dp
    // time = O(n^2 * d), space = O(n * d)
    public int minDifficulty(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        int[] nums = new int[n + 1];
        for (int i = 0; i < n; i++) nums[i + 1] = jobDifficulty[i];
        int[][] dp = new int[n + 1][d + 1];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);

        // init
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= Math.min(d, i); k++) {
                int max = nums[i];
                for (int j = i; j >= k; j--) { // 从后往前遍历就能够顺便求出max[i:j]
                    max = Math.max(max, nums[j]);
                    dp[i][k] = Math.min(dp[i][k], dp[j - 1][k - 1] + max); // j - 1 >= k - 1 => j >= k
                }
            }
        }
        return dp[n][d] == Integer.MAX_VALUE / 2 ? -1 : dp[n][d];
    }

    // S1.2: use the original array
    // time = O(n^2 * d), space = O(n * d)
    public int minDifficulty2(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        int[][] dp = new int[n + 1][d + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= Math.min(i, d); k++) {
                int max = jobDifficulty[i - 1];
                for (int j = i; j >= k; j--) {
                    max = Math.max(max, jobDifficulty[j - 1]);
                    dp[i][k] = Math.min(dp[i][k], dp[j - 1][k - 1] + max);
                }
            }
        }
        return dp[n][d] == Integer.MAX_VALUE / 2 ? -1 : dp[n][d];
    }
}
/**
 * Given an array, minimize "the sum of each maximum of subarray"
 * schedule a list of jobs in d days -> 第一类区间型dp
 * dp[i][k]: given an array s[0:i], minimize "the sum of each maximum of k subarray"
 * xxxxxxxx i
 * 看最后一份，起始点在哪里，找一个最优的j
 * {xxxxxx} [j xxxx i]
 * dp[j-1][k-1] + max(j:i)
 */