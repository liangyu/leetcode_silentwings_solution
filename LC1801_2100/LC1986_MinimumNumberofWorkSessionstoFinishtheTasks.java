package LC1801_2100;
import java.util.*;
public class LC1986_MinimumNumberofWorkSessionstoFinishtheTasks {
    /**
     * There are n tasks assigned to you. The task times are represented as an integer array tasks of length n, where
     * the ith task takes tasks[i] hours to finish. A work session is when you work for at most sessionTime consecutive
     * hours and then take a break.
     *
     * You should finish the given tasks in a way that satisfies the following conditions:
     *
     * If you start a task in a work session, you must complete it in the same work session.
     * You can start a new task immediately after finishing the previous one.
     * You may complete the tasks in any order.
     * Given tasks and sessionTime, return the minimum number of work sessions needed to finish all the tasks following
     * the conditions above.
     *
     * The tests are generated such that sessionTime is greater than or equal to the maximum element in tasks[i].
     *
     * Input: tasks = [1,2,3], sessionTime = 3
     * Output: 2
     *
     * Constraints:
     *
     * n == tasks.length
     * 1 <= n <= 14
     * 1 <= tasks[i] <= 10
     * max(tasks[i]) <= sessionTime <= 15
     * @param tasks
     * @param sessionTime
     * @return
     */
    // time = O(3^n), space = O(2^n)
    public int minSessions(int[] tasks, int sessionTime) {
        // corner case
        if (tasks == null || tasks.length == 0 || sessionTime <= 0) return 0;

        int n = tasks.length;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);

        for (int state = 0; state < (1 << n); state++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if (((state >> i) & 1) == 1) sum += tasks[i];
            }
            if (sum <= sessionTime) dp[state] = 1;
        }

        for (int state = 0; state < (1 << n); state++) {
            for (int subset = state; subset > 0; subset = (subset - 1) & state) {
                dp[state] = Math.min(dp[state], dp[subset] + dp[state - subset]);
            }
        }
        return dp[(1 << n) - 1];
    }
}
/**
 * ref: LC1723
 * NP hard问题
 * 大海捞针
 * 如果有解，数据规模一定不大
 * n <= 14 => O(3^n) 可以接受 => 遍历子集
 * bitmask 用一个二进制数的每个bit代表这个任务是否被选取了
 * state = 00100110
 * dp[state]: minimum number of work sessions needed to finish the task of state
 * state = (A + B + C) + D  可以转化成这2部分
 * state = min{dp[subset] + dp[state - subset]}
 */
