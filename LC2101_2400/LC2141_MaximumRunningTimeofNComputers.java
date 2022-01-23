package LC2101_2400;
import java.util.*;
public class LC2141_MaximumRunningTimeofNComputers {
    /**
     * You have n computers. You are given the integer n and a 0-indexed integer array batteries where the ith battery
     * can run a computer for batteries[i] minutes. You are interested in running all n computers simultaneously using
     * the given batteries.
     *
     * Initially, you can insert at most one battery into each computer. After that and at any integer time moment, you
     * can remove a battery from a computer and insert another battery any number of times. The inserted battery can be
     * a totally new battery or a battery from another computer. You may assume that the removing and inserting
     * processes take no time.
     *
     * Note that the batteries cannot be recharged.
     *
     * Return the maximum number of minutes you can run all the n computers simultaneously.
     *
     * Input: n = 2, batteries = [3,3,3]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= n <= batteries.length <= 10^5
     * 1 <= batteries[i] <= 10^9
     * @param n
     * @param batteries
     * @return
     */
    // S1: BS
    // time = O(nlogn), space = O(1)
    public long maxRunTime(int n, int[] batteries) {
        long left = 0, right = Long.MAX_VALUE / n;
        while (left < right) {
            long mid = right - (right - left) / 2;
            if (checkOK(mid, n, batteries)) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private boolean checkOK(long t, int n, int[] batteries) {
        long sum = 0;
        for (int x : batteries) {
            sum += Math.min(x, t);
            if (sum >= t * n) return true;
        }
        return false;
    }

    // S2: BS
    // time = O(nlogn), space = O(1)
    public long maxRunTime2(int n, int[] batteries) {
        int m = batteries.length;
        long sum = 0;
        for (int x : batteries) sum += x;

        Arrays.sort(batteries);

        long left = 0, right = sum / n;
        while (left < right) {
            long mid = right - (right - left) / 2;
            if (helper(batteries, n, mid)) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private boolean helper(int[] nums, int n, long t) {
        long sum = 0;
        int count = 0;
        for (int x : nums) {
            sum += x;
            if (sum >= t) {
                count++;
                sum -= t;
            }
        }
        return count >= n;
    }
}
/**
 * 遇事不决用二分
 * 因为电池可以任意分配给各个电脑和各个时段，所以策略很简单，把所有的电池容量加起来，查看能否支撑T*N即可。
 * 唯一需要注意的就是，任意一个电池都不能贡献超过时间T（因为我们只会让电脑运行时间T）。
 * 所以我们在算电池总容量的时候，取T为上限。
 */

