package LC1801_2100;
import java.util.*;
public class LC1883_MinimumSkipstoArriveatMeetingOnTime {
    /**
     * You are given an integer hoursBefore, the number of hours you have to travel to your meeting. To arrive at your
     * meeting, you have to travel through n roads. The road lengths are given as an integer array dist of length n,
     * where dist[i] describes the length of the ith road in kilometers. In addition, you are given an integer speed,
     * which is the speed (in km/h) you will travel at.
     *
     * After you travel road i, you must rest and wait for the next integer hour before you can begin traveling on the
     * next road. Note that you do not have to rest after traveling the last road because you are already at the meeting.
     *
     * For example, if traveling a road takes 1.4 hours, you must wait until the 2 hour mark before traveling the next
     * road. If traveling a road takes exactly 2 hours, you do not need to wait.
     * However, you are allowed to skip some rests to be able to arrive on time, meaning you do not need to wait for the
     * next integer hour. Note that this means you may finish traveling future roads at different hour marks.
     *
     * For example, suppose traveling the first road takes 1.4 hours and traveling the second road takes 0.6 hours.
     * Skipping the rest after the first road will mean you finish traveling the second road right at the 2 hour mark,
     * letting you start traveling the third road immediately.
     * Return the minimum number of skips required to arrive at the meeting on time, or -1 if it is impossible.
     *
     * Input: dist = [1,3,2], speed = 4, hoursBefore = 2
     * Output: 1
     *
     * Constraints:
     *
     * n == dist.length
     * 1 <= n <= 1000
     * 1 <= dist[i] <= 10^5
     * 1 <= speed <= 10^6
     * 1 <= hoursBefore <= 10^7
     * @param dist
     * @param speed
     * @param hoursBefore
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        // corner case
        if (dist == null || dist.length == 0) return 0;

        int n = dist.length;
        double[][] dp = new double[n + 1][n + 1];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], 1e20);
        dp[0][0] = 0; // dp[0][1] -> 无意义

        // 纠正精度问题 -> 定义一个eps
        double eps = 1e-8;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.ceil(dp[i - 1][j] + dist[i - 1] * 1.0 / speed - eps);
                if (j >= 1) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + dist[i - 1] * 1.0 / speed, dp[i][j]);
                }
            }
        }

        for (int j = 0; j <= n; j++) {
            dp[n][j] = dp[n - 1][j] + dist[n - 1] * 1.0 / speed;
            if (dp[n][j] <= hoursBefore) return j;
        }
        return -1;
    }
}
/**
 * skip k rests
 * time needed vs hoursbefore
 * while (low < high) {
 *     mid = low + (high - low) / 2;
 *     timeneeded = cumputeTime(dist, speed, mid);
 *     if (timeneeded > hoursbefore)
 *          high = mid - 1;
*      else
 *          low = mid;
 * }
 * return low;
 *
 * double computeTime(dist, speed, k) {
 *
 * }
 *
 * the minimum time needed to run n roads with k skipped rests  => dp
 * dp[i][j]: the minimum time needed to run i roads with j skipped rests，跑完后如果休息的话这段休息时间也算在内
 * 突破口：当前第i条路
 *
 * (1) skip the rest for the i-th road
 * dp[i-1][j-1] + dist[i] * 1.0/speed
 * (2) do not skip the rest for the i-th road
 * ceil(dp[i-1][j] + dist[i] * 1.0/speed)
 * => dp[i][j] = min(t1,t2);
 * return dp[n][k]  跑完n条路休息k次
 * i:1~n
 * j:0~k
 * 你每次给我一个k，我都要把dp[i][j]算一遍,重新构建起来，效率不高，有重复的地方 => B.S.这层壳不需要
 * i:1~n
 * j:0~n
 * 把二维数组都算满了
 * for (int j = 0; j <= n; j++) {
 *     if (dp[n][j] <= hoursBefore)
 *          return j;
 * }
 *
 * 最大挑战：精度！
 * 因为浮点的误差有可能正好跨越了整数这个槛
 * System.out.println(Math.ceil(8.0 + 1.0/3 + 1.0/3 + 1.0/3));
 * => 10.0 而不是9.0,为什么呢？
 * System.out.println(8.0 + 1.0/3 + 1.0/3 + 1.0/3);
 * => 9.000000000000002 => ceil = 10
 * 向上取整很可能会越过这个槛 => 减掉这个多余的部分
 * => System.out.println(Math.ceil(8.0 + 1.0/3 + 1.0/3 + 1.0/3 - 1e-8));
 * => 9.0
 */
