package LC2100_2400;
import java.util.*;
public class LC2136_EarliestPossibleDayofFullBloom {
    /**
     * You have n flower seeds. Every seed must be planted first before it can begin to grow, then bloom. Planting a
     * seed takes time and so does the growth of a seed. You are given two 0-indexed integer arrays plantTime and
     * growTime, of length n each:
     *
     * plantTime[i] is the number of full days it takes you to plant the ith seed. Every day, you can work on planting
     * exactly one seed. You do not have to work on planting the same seed on consecutive days, but the planting of a
     * seed is not complete until you have worked plantTime[i] days on planting it in total.
     * growTime[i] is the number of full days it takes the ith seed to grow after being completely planted. After the
     * last day of its growth, the flower blooms and stays bloomed forever.
     * From the beginning of day 0, you can plant the seeds in any order.
     *
     * Return the earliest possible day where all seeds are blooming.
     *
     * Input: plantTime = [1,4,3], growTime = [2,3,1]
     * Output: 9
     *
     * Input: plantTime = [1,2,3,2], growTime = [2,1,2,1]
     * Output: 9
     *
     * Constraints:
     *
     * n == plantTime.length == growTime.length
     * 1 <= n <= 10^5
     * 1 <= plantTime[i], growTime[i] <= 10^4
     * @param plantTime
     * @param growTime
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(n)
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int n = plantTime.length;
        int[][] time = new int[n][2];
        for (int i = 0; i < n; i++) {
            time[i] = new int[]{plantTime[i], growTime[i]};
        }
        Arrays.sort(time, (o1, o2) -> o2[1] - o1[1]);

        int days = 0, res = 0;
        for (int i = 0; i < n; i++) {
            days += time[i][0];
            res = Math.max(res, days + time[i][1]);
        }
        return res;
    }

    // S2: BS (二分猜值，逆向突破)
    // time = O(nlogn), space = O(n)
    public int earliestFullBloom2(int[] plantTime, int[] growTime) {
        int n = plantTime.length;
        int[][] time = new int[n][2];
        for (int i = 0; i < n; i++) {
            time[i] = new int[]{plantTime[i], - growTime[i]};
        }
        Arrays.sort(time, (o1, o2) -> o1[1] - o2[1]);

        int left = 1, right = Integer.MAX_VALUE / 2;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (checkOK(mid, time)) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    private boolean checkOK(int t, int[][] time) {
        int days = 0, n = time.length;
        for (int i = 0; i < n; i++) {
            days += time[i][0];
            if (days > t + time[i][1]) return false;
        }
        return true;
    }
}
/**
 * can we plant all seeds by their own deadline? Each seed requires plantTime[i]
 * 不管统一开花日是哪天，生长期越长，意味着plantTime的deadline就越靠前
 * deadline越靠前，就越要今早搞 => 永远做deadline最近的
 * 按照种植完成的deadline从早到晚排序，这就是我们的最优策略。
 * 有了种植的顺序，我们依次遍历每个花，就有了各自的种植完成时刻。
 * 然后比较每朵花“种植完成时刻+growTime”，最大值就是最终的统一开花日期。
 */