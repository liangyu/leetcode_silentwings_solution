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
    // S1: B.S.
    // time = O(nlogk), space = O(n) k: 二分的上下界之差
    public int minSpeedOnTime(int[] dist, double hour) {
        // corner case
        if (dist == null || dist.length == 0) return 0;
        int n = dist.length;
        if (hour <= n - 1) return -1;
        int low = 1, high = (int)1e7;
        while (low < high) {
            int mid = low + (high - low) / 2;
            double time = helper(dist, mid);
            if (time > hour) low = mid + 1; // mid本身不符合要求，下界要+1
            else high = mid; // mid本身是一个解，但不是最优解，可以再小一点
        }
        return low;
    }

    private double helper(int[] dist, int speed) {
        double time = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            time += (dist[i] - 1) / speed + 1; // 向上取整的模板！！！
        }
        time += dist[dist.length - 1] * 1.0 / speed;
        return time;
    }
}
/**
 * 猜大猜小 -> B.S. 整型搜索，效率非常高
 * 浮点数相等的比较是没有意义的，大小比较是没有问题的，不涉及精度问题。
 */