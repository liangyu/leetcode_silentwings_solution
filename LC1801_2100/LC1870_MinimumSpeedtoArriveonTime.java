package LC1801_2100;
import java.util.*;
public class LC1870_MinimumSpeedtoArriveonTime {
    /**
     * You are given a floating-point number hour, representing the amount of time you have to reach the office.
     * To commute to the office, you must take n trains in sequential order. You are also given an integer array dist
     * of length n, where dist[i] describes the distance (in kilometers) of the ith train ride.
     *
     * Each train can only depart at an integer hour, so you may need to wait in between each train ride.
     *
     * For example, if the 1st train ride takes 1.5 hours, you must wait for an additional 0.5 hours before you can
     * depart on the 2nd train ride at the 2 hour mark.
     * Return the minimum positive integer speed (in kilometers per hour) that all the trains must travel at for you
     * to reach the office on time, or -1 if it is impossible to be on time.
     *
     * Tests are generated such that the answer will not exceed 10^7 and hour will have at most two digits after the
     * decimal point.
     *
     * Input: dist = [1,3,2], hour = 6
     * Output: 1
     *
     * Constraints:
     *
     * n == dist.length
     * 1 <= n <= 10^5
     * 1 <= dist[i] <= 10^5
     * 1 <= hour <= 10^9
     * There will be at most two digits after the decimal point in hour.
     * @param dist
     * @param hour
     * @return
     */
    // time = O(n), space = O(1)
    public int minSpeedOnTime(int[] dist, double hour) {
        // corner case
        if (dist == null || dist.length == 0) return 0;
        if (hour < dist.length - 1) return -1;

        int n = dist.length, sum = 0;
        for (int i = 0; i < n; i++) sum += dist[i];
        int left = Math.max(1, sum / ((int) hour + 1));
        int right = (int)1e7;

        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (helper(mid, dist) <= hour) right = mid;
            else left = mid;
        }
        return helper(left, dist) <= hour ? left : right;
    }

    private double helper(int speed, int[] dist) {
        double time = 0;
        int n = dist.length;
        for (int i = 0; i < n - 1; i++) {
            time += (int)(dist[i] / speed);
            if (dist[i] % speed > 0) time++;
        }
        time += (double)dist[n - 1] / speed;
        return time;
    }
}
