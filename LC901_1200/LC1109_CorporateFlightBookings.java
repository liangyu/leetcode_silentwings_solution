package LC901_1200;
import java.util.*;
public class LC1109_CorporateFlightBookings {
    /**
     * There are n flights that are labeled from 1 to n.
     *
     * You are given an array of flight bookings bookings, where bookings[i] = [firsti, lasti, seatsi] represents a
     * booking for flights firsti through lasti (inclusive) with seatsi seats reserved for each flight in the range.
     *
     * Return an array answer of length n, where answer[i] is the total number of seats reserved for flight i.
     *
     * Input: bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
     * Output: [10,55,45,25,25]
     *
     * Constraints:
     *
     * 1 <= n <= 2 * 10^4
     * 1 <= bookings.length <= 2 * 10^4
     * bookings[i].length == 3
     * 1 <= firsti <= lasti <= n
     * 1 <= seatsi <= 10^4
     * @param bookings
     * @param n
     * @return
     */
    // time = O(n), space = O(n)
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // corner case
        if (bookings == null || bookings.length == 0 || bookings[0] == null || bookings[0].length == 0) {
            return new int[0];
        }

        int[] diff = new int[n + 2];
        for (int[] b : bookings) {
            diff[b[0]] += b[2];
            diff[b[1] + 1] -= b[2];
        }

        int cur = 0;
        int[] res = new int[n];
        for (int i = 1; i <= n; i++) {
            cur += diff[i];
            res[i - 1] = cur;
        }
        return res;
    }
}
/**
 * ref: LC370
 * [1, 10000]+2
 * [2, 10003]+3
 * 差分数组 => 并不会把这些区间上的每个元素都作处理，而是只处理区间的端点
 * 每次只处理增量
 * diff[i] = flight[i] - flight[i-1]
 * 差分数组是稀疏的，大部分都是0，储存的时候并不需要把中间为0的储存下来，只要存2个端点
 */
