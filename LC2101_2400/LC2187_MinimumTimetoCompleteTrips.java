package LC2101_2400;

public class LC2187_MinimumTimetoCompleteTrips {
    /**
     * You are given an array time where time[i] denotes the time taken by the ith bus to complete one trip.
     *
     * Each bus can make multiple trips successively; that is, the next trip can start immediately after completing the
     * current trip. Also, each bus operates independently; that is, the trips of one bus do not influence the trips of
     * any other bus.
     *
     * You are also given an integer totalTrips, which denotes the number of trips all buses should make in total.
     * Return the minimum time required for all buses to complete at least totalTrips trips.
     *
     * Input: time = [1,2,3], totalTrips = 5
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= time.length <= 10^5
     * 1 <= time[i], totalTrips <= 10^7
     * @param time
     * @param totalTrips
     * @return
     */
    // time = O(nlogn), space = O(1)
    public long minimumTime(int[] time, int totalTrips) {
        long left = 1, right = (long) 1e14;
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (helper(time, mid, totalTrips)) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    private boolean helper(int[] time, long k, int t) {
        long count = 0;
        for (int x : time) count += k / x;
        return count >= t;
    }
}
