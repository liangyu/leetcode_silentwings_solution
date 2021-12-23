package LC601_900;

import java.util.PriorityQueue;

public class LC774_MinimizeMaxDistancetoGasStation {
    /**
     * You are given an integer array stations that represents the positions of the gas stations on the x-axis. You are
     * also given an integer k.
     *
     * You should add k new gas stations. You can add the stations anywhere on the x-axis, and not necessarily on
     * an integer position.
     *
     * Let penalty() be the maximum distance between adjacent gas stations after adding the k new stations.
     *
     * Return the smallest possible value of penalty(). Answers within 10-6 of the actual answer will be accepted.
     *
     * Input: stations = [1,2,3,4,5,6,7,8,9,10], k = 9
     * Output: 0.50000
     *
     * Constraints:
     *
     * 10 <= stations.length <= 2000
     * 0 <= stations[i] <= 10^8
     * stations is sorted in a strictly increasing order.
     * 1 <= k <= 10^6
     * @param stations
     * @param k
     * @return
     */
    // S1: BS
    // time = O(nlogn), space = O(1)
    public double minmaxGasDist(int[] stations, int k) {
        int n = stations.length;
        double left = 0, right = stations[n - 1] - stations[0];

        while (left + 1e-6 < right) {
            double mid = left + (right - left) / 2;
            if (isOK(mid, stations, k)) right = mid; // 下压
            else left = mid;
        }
        return left;
    }

    private boolean isOK(double d, int[] stations, int k) {
        int n = stations.length, count = 0;
        for (int i = 1; i < n; i++) {
            double t = (stations[i] - stations[i - 1]) / d;
            count += Math.ceil(t) - 1; // 可以分成Math.ceil(t)份 => 需要插入Math.ceil(t) - 1块板子
        }
        return count <= k;
    }
}
/**
 * 正着想比较难，反着想比较容易
 * A ___ B _____|_____ C
 * A ___ B ___|____|___ C
 * A ___ B ___C____D____E
 * 1.5 -> 2 -> 1
 * 2.1 -> 3 -> 2
 * 2 -> 2 -> 1
 */