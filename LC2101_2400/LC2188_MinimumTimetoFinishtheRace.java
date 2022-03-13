package LC2101_2400;
import java.util.*;
public class LC2188_MinimumTimetoFinishtheRace {
    /**
     * You are given a 0-indexed 2D integer array tires where tires[i] = [fi, ri] indicates that the ith tire can finish
     * its xth successive lap in fi * ri(x-1) seconds.
     *
     * For example, if fi = 3 and ri = 2, then the tire would finish its 1st lap in 3 seconds, its 2nd lap in 3 * 2 = 6
     * seconds, its 3rd lap in 3 * 22 = 12 seconds, etc.
     * You are also given an integer changeTime and an integer numLaps.
     *
     * The race consists of numLaps laps and you may start the race with any tire. You have an unlimited supply of each
     * tire and after every lap, you may change to any given tire (including the current tire type) if you wait
     * changeTime seconds.
     *
     * Return the minimum time to finish the race.
     *
     * Input: tires = [[2,3],[3,4]], changeTime = 5, numLaps = 4
     * Output: 21
     *
     * Constraints:
     *
     * 1 <= tires.length <= 10^5
     * tires[i].length == 2
     * 1 <= fi, changeTime <= 10^5
     * 2 <= ri <= 10^5
     * 1 <= numLaps <= 1000
     * @param tires
     * @param changeTime
     * @param numLaps
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n)
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        Arrays.sort(tires, (o1, o2) -> o1[1] != o2[1] ? o1[1] - o2[1] : o1[0] - o2[0]);

        List<int[]> newTires = new ArrayList<>();
        for (int i = 0; i < tires.length; i++) {
            if (newTires.size() > 0 && tires[i][0] >= newTires.get(newTires.size() - 1)[0]) continue;
            newTires.add(tires[i]);
        }

        double[] dp = new double[numLaps + 1];
        Arrays.fill(dp, Double.MAX_VALUE);
        dp[0] = 0;

        double[] minTime = new double[Math.min(20, numLaps) + 1];
        Arrays.fill(minTime, Double.MAX_VALUE);
        for (int x = 1; x < minTime.length; x++) {
            for (int i = 0; i < newTires.size(); i++) {
                minTime[x] = Math.min(minTime[x], time(newTires.get(i), x));
            }
        }

        for (int i = 1; i <= numLaps; i++) {
            for (int j = 1; j <= Math.min(i, 20); j++) {
                dp[i] = Math.min(dp[i], dp[i - j] + (j == i ? 0 : changeTime) + minTime[j]);
            }
        }
        return (int) dp[numLaps];
    }

    private double time(int[] tire, int x) {
        double f = tire[0], r = tire[1];
        return f * (Math.pow(r, x) - 1) / (r - 1);
    }

    // S1.2: DP (use Integer.MAX_VALUE)
    // time = O(n^2), space = O(n)
    public int minimumFinishTime12(int[][] tires, int changeTime, int numLaps) {
        Arrays.sort(tires, (o1, o2) -> o1[1] != o2[1] ? o1[1] - o2[1] : o1[0] - o2[0]);
        List<int[]> newTires = new ArrayList<>();
        for (int i = 0; i < tires.length; i++) {
            if (newTires.size() > 0 && tires[i][0] >= newTires.get(newTires.size() - 1)[0]) continue;
            newTires.add(tires[i]);
        }

        int[] dp = new int[numLaps + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        int[] minTime = new int[Math.min(numLaps, 20) + 1];
        Arrays.fill(minTime, Integer.MAX_VALUE / 2);


        int m = minTime.length, n = newTires.size();
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                minTime[i] = Math.min(minTime[i], helper(newTires.get(j), i));
            }
        }

        for (int i = 1; i <= numLaps; i++) {
            for (int j = 1; j <= Math.min(20, i); j++) {
                // 这里有加减，所以初始要设为Integer.MAX_VALUE / 2 !!!
                dp[i] = Math.min(dp[i], dp[i - j] + (j == i ? 0 : changeTime) + minTime[j]);
            }
        }
        return dp[numLaps];
    }

    private int helper(int[] tire, int x) {
        int f = tire[0], r = tire[1];
        return (int)(f * (1 - Math.pow(r, x)) / (1 - r));
    }

    // S2: DP
    // time = O(n^2), space = O(n)
    public int minimumFinishTime2(int[][] tires, int changeTime, int numLaps) {
        int[] minTimes = new int[18];
        Arrays.fill(minTimes, Integer.MAX_VALUE / 2);
        for (int[] x : tires) {
            long time = x[0];
            int i = 1, sum = 0;
            while (time <= changeTime + x[0]) {
                sum += time;
                minTimes[i] = Math.min(minTimes[i], sum);
                time *= x[1];
                i++;
            }
        }

        int[] dp = new int[numLaps + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = -changeTime;
        for (int i = 1; i <= numLaps; i++) {
            for (int j = 1; j <= Math.min(17, i); j++) {
                dp[i] = Math.min(dp[i], dp[i - j] + minTimes[j]);
            }
            dp[i] += changeTime;
        }
        return dp[numLaps];
    }
}
/**
 * dp[i]: 跑i圈的最小耗时
 * minTime[i]: 同一个轮胎跑i圈的最小耗时
 * 动态规划
 * dp[i]: the minimum time to run i laps
 * 考虑最后一次跑了多少圈，j圈 -> 跑完i-j圈越小越好
 * dp[i] = min{dp[i-j] + C + minTime[j]}   j = 1, 2, 3, ...,j
 * => O(n^2) = O(10^6)
 * 对每一个指定圈数，找一个花时间最少的轮胎
 * 1*2^(x-1) > 1e5  => x = 19
 * 即使对于最优秀的轮胎，也不会让它开满20圈
 * => j的范围是有上限的，上限 = 20
 * 优化：f,r => 如果某个轮胎的f参数和r参数都比另一个大，可以直接淘汰掉，取比较小的那些
 * 提前过滤下轮胎
 * r单调递增，f单调递减，不会轻易淘汰掉一个轮胎
 */
