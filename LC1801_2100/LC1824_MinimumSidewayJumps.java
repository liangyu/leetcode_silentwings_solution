package LC1801_2100;
import java.util.*;
public class LC1824_MinimumSidewayJumps {
    /**
     * There is a 3 lane road of length n that consists of n + 1 points labeled from 0 to n. A frog starts at point 0
     * in the second lane and wants to jump to point n. However, there could be obstacles along the way.
     *
     * You are given an array obstacles of length n + 1 where each obstacles[i] (ranging from 0 to 3) describes an
     * obstacle on the lane obstacles[i] at point i. If obstacles[i] == 0, there are no obstacles at point i. There will
     * be at most one obstacle in the 3 lanes at each point.
     *
     * For example, if obstacles[2] == 1, then there is an obstacle on lane 1 at point 2.
     * The frog can only travel from point i to point i + 1 on the same lane if there is not an obstacle on the lane at
     * point i + 1. To avoid obstacles, the frog can also perform a side jump to jump to another lane (even if they are
     * not adjacent) at the same point if there is no obstacle on the new lane.
     *
     * For example, the frog can jump from lane 3 at point 3 to lane 1 at point 3.
     * Return the minimum number of side jumps the frog needs to reach any lane at point n starting from lane 2 at point
     * 0.
     *
     * Note: There will be no obstacles on points 0 and n.
     *
     * Input: obstacles = [0,1,2,3,0]
     * Output: 2
     *
     * Constraints:
     *
     * obstacles.length == n + 1
     * 1 <= n <= 5 * 10^5
     * 0 <= obstacles[i] <= 3
     * obstacles[0] == obstacles[n] == 0
     * @param obstacles
     * @return
     */
    // time = O(n), space = O(n)
    public int minSideJumps(int[] obstacles) {
        if (obstacles == null || obstacles.length == 0) return 0;

        int n = obstacles.length;
        int[][] dp = new int[n][4];
        dp[0][2] = 0;
        dp[0][1] = dp[0][3] = 1;

        for (int i = 1; i < n; i++) {
            int obs = obstacles[i];
            int minVal = Integer.MAX_VALUE;
            for (int j = 1; j <= 3; j++) {
                if (j == obs) { // 被堵住
                    dp[i][j] = Integer.MAX_VALUE;
                } else { // 没有被堵住，直接冲过来
                    dp[i][j] = dp[i - 1][j];
                }
                minVal = Math.min(minVal, dp[i][j]);
            }
            for (int j = 1; j <= 3; j++) { // 除去当前最小赛道外，其余两条都可以更新为从最小那条赛道横着跳一次过来，即minVal + 1
                if (j != obs && dp[i][j] != minVal) dp[i][j] = minVal + 1;
            }
        }
        int res = Integer.MAX_VALUE;
        for (int j = 1; j <= 3; j++) { // 最后一个位置是n - 1，遍历三条赛道找到n - 1位置的最小值
            res = Math.min(res, dp[n - 1][j]);
        }
        return res;
    }
}
/**
 * dp[i][j]: minimum number of side jumps to arrive at position i and track j
* if j == obs : dp[i][j] = Integer.MAX_VALUE
 * else:
 *      dp[i][j] = dp[i - 1][j] -> 冲成功
 *      dp[i][j] = min(dp[i][j]) + 1  -> 换道
 */